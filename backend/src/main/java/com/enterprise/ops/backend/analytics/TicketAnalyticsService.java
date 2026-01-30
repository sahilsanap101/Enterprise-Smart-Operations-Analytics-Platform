package com.enterprise.ops.backend.analytics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.ticket.*;

@Service
public class TicketAnalyticsService {

    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository historyRepository;

    public TicketAnalyticsService(
            TicketRepository ticketRepository,
            TicketHistoryRepository historyRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.historyRepository = historyRepository;
    }

    public AnalyticsResponse getTicketAnalytics() {

        List<Ticket> allTickets = ticketRepository.findAll();

        long total = allTickets.size();

        long open = allTickets.stream()
                .filter(t -> t.getStatus() != TicketStatus.CLOSED)
                .count();

        long closed = allTickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.CLOSED)
                .count();

        long slaBreached = allTickets.stream()
                .filter(this::isSlaBreached)
                .count();

        double avgResolutionHours = calculateAverageResolutionTime();

        return new AnalyticsResponse(
                total,
                open,
                closed,
                slaBreached,
                avgResolutionHours
        );
    }

    // 🔥 SLA breach logic
    private boolean isSlaBreached(Ticket ticket) {

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            return ticket.getSlaDeadline()
                    .isBefore(getTicketClosedTime(ticket));
        }

        return LocalDateTime.now().isAfter(ticket.getSlaDeadline());
    }

    // 🔥 Resolution time calculation
    private double calculateAverageResolutionTime() {

        List<TicketHistory> closedEvents =
                historyRepository.findAll()
                        .stream()
                        .filter(h -> "CLOSED".equals(h.getAction()))
                        .toList();

        if (closedEvents.isEmpty()) return 0;

        double totalHours = 0;

        for (TicketHistory history : closedEvents) {
            Ticket ticket = history.getTicket();

            long hours = Duration.between(
                    ticket.getCreatedAt(),
                    history.getPerformedAt()
            ).toHours();

            totalHours += hours;
        }

        return totalHours / closedEvents.size();
    }

    private LocalDateTime getTicketClosedTime(Ticket ticket) {
        return historyRepository.findByTicketId(ticket.getId())
                .stream()
                .filter(h -> "CLOSED".equals(h.getAction()))
                .findFirst()
                .map(TicketHistory::getPerformedAt)
                .orElse(LocalDateTime.now());
    }
}
