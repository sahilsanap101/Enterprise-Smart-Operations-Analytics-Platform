package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // ✅ EMPLOYEE raises ticket
    public Ticket createTicket(TicketRequest request, String employeeEmail) {

        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (employee.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Only employees can raise tickets");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(employee);
        ticket.setProject(project);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setSlaDeadline(calculateSla(request.getPriority()));

        Ticket savedTicket = ticketRepository.save(ticket);

        logHistory(savedTicket, "CREATED", null, "OPEN", employee);

        return savedTicket;
    }

    // ✅ MANAGER assigns ticket
    public Ticket assignTicket(Long ticketId, Long managerId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only managers can be assigned");
        }

        User oldAssignee = ticket.getAssignedTo();

        ticket.setAssignedTo(manager);
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        Ticket updatedTicket = ticketRepository.save(ticket);

        logHistory(
                updatedTicket,
                "ASSIGNED",
                oldAssignee == null ? null : oldAssignee.getEmail(),
                manager.getEmail(),
                manager
        );

        return updatedTicket;
    }

    // ✅ EMPLOYEE: view my tickets (Paginated + Sorting)
    public Page<Ticket> getTicketsByEmployee(
            String employeeEmail,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return ticketRepository.findByCreatedBy(employee, pageable);
    }

    // ✅ MANAGER: view assigned tickets (Paginated + Sorting)
    public Page<Ticket> getTicketsAssignedToManager(
            String managerEmail,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("Access denied");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        return ticketRepository.findByAssignedTo(manager, pageable);
    }

    // ✅ SLA calculation
    private LocalDateTime calculateSla(TicketPriority priority) {
        return switch (priority) {
            case CRITICAL -> LocalDateTime.now().plusHours(12);
            case HIGH -> LocalDateTime.now().plusHours(24);
            case MEDIUM -> LocalDateTime.now().plusHours(72);
            case LOW -> LocalDateTime.now().plusHours(120);
        };
    }

    // ✅ HISTORY LOGGER
    private void logHistory(
            Ticket ticket,
            String action,
            String oldValue,
            String newValue,
            User user
    ) {
        TicketHistory history = new TicketHistory();
        history.setTicket(ticket);
        history.setAction(action);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setPerformedBy(user);
        history.setPerformedAt(LocalDateTime.now());

        historyRepository.save(history);
    }
}
