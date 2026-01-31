package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.project.Project;
import com.enterprise.ops.backend.project.ProjectRepository;
import com.enterprise.ops.backend.user.Role;
import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TicketHistoryRepository historyRepository;

    public TicketService(
            TicketRepository ticketRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository,
            TicketHistoryRepository historyRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    // CREATE: DELIVERY (Manager/Admin) | ESCALATION (Employee)
    public Ticket createTicket(TicketRequest request, String email) {

        User creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getType() == TicketType.ESCALATION &&
                creator.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Only employees can raise escalation tickets");
        }

        if (request.getType() == TicketType.DELIVERY &&
                creator.getRole() == Role.EMPLOYEE) {
            throw new RuntimeException("Employees cannot create delivery tickets");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setType(request.getType());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(creator);
        ticket.setProject(project);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setSlaDeadline(calculateSla(request.getPriority()));

        Ticket saved = ticketRepository.save(ticket);
        log(saved, "CREATED", null, "OPEN", creator);

        return saved;
    }

    // ASSIGN: ADMIN / MANAGER
    public Ticket assignTicket(Long ticketId, Long assigneeId, String actorEmail) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (actor.getRole() == Role.EMPLOYEE) {
            throw new RuntimeException("Employee cannot assign tickets");
        }

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found"));

        User old = ticket.getAssignedTo();
        ticket.setAssignedTo(assignee);
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        Ticket saved = ticketRepository.save(ticket);
        log(saved, "ASSIGNED",
                old == null ? null : old.getEmail(),
                assignee.getEmail(),
                actor);

        return saved;
    }

    // FULL LIFECYCLE
    public Ticket updateStatus(Long ticketId, TicketStatus newStatus, String email) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TicketStatus old = ticket.getStatus();

        switch (newStatus) {
            case IN_PROGRESS -> {
                if (user.getRole() != Role.EMPLOYEE)
                    throw new RuntimeException("Only employee can start work");
            }
            case RESOLVED -> {
                if (user.getRole() != Role.MANAGER)
                    throw new RuntimeException("Only manager can resolve");
            }
            case CLOSED -> {
                if (user.getRole() == Role.EMPLOYEE)
                    throw new RuntimeException("Employee cannot close ticket");
            }
            default -> {}
        }

        ticket.setStatus(newStatus);
        Ticket saved = ticketRepository.save(ticket);

        log(saved, "STATUS_CHANGED", old.name(), newStatus.name(), user);

        if (newStatus == TicketStatus.CLOSED) {
            log(saved, "CLOSED", null, "CLOSED", user);
        }

        return saved;
    }

    // ================= NEW METHODS ADDED =================

    public Page<Ticket> getMyTickets(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ticketRepository.findByCreatedBy(user, pageable);
    }

    public Page<Ticket> getAssignedTickets(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ticketRepository.findByAssignedTo(user, pageable);
    }

    // ================= HELPER METHODS =================

    private LocalDateTime calculateSla(TicketPriority p) {
        return switch (p) {
            case CRITICAL -> LocalDateTime.now().plusHours(12);
            case HIGH -> LocalDateTime.now().plusHours(24);
            case MEDIUM -> LocalDateTime.now().plusHours(72);
            case LOW -> LocalDateTime.now().plusHours(120);
        };
    }

    private void log(
            Ticket ticket,
            String action,
            String oldVal,
            String newVal,
            User user
    ) {
        TicketHistory h = new TicketHistory();
        h.setTicket(ticket);
        h.setAction(action);
        h.setOldValue(oldVal);
        h.setNewValue(newVal);
        h.setPerformedBy(user);
        h.setPerformedAt(LocalDateTime.now());
        historyRepository.save(h);
    }
}