package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.analytics.dto.TicketTrendResponse;
import com.enterprise.ops.backend.ticket.TicketRepository;
import com.enterprise.ops.backend.ticket.TicketStatus;

@Service
public class TicketAnalyticsService {

    private final TicketRepository ticketRepository;

    public TicketAnalyticsService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // USED BY AnalyticsController (/analytics/tickets/trends)
    public List<TicketTrendResponse> getTicketTrends() {
        return List.of(
                new TicketTrendResponse(
                        TicketStatus.OPEN.name(),
                        ticketRepository.countByStatus(TicketStatus.OPEN)
                ),
                new TicketTrendResponse(
                        TicketStatus.IN_PROGRESS.name(),
                        ticketRepository.countByStatus(TicketStatus.IN_PROGRESS)
                ),
                new TicketTrendResponse(
                        TicketStatus.RESOLVED.name(),
                        ticketRepository.countByStatus(TicketStatus.RESOLVED)
                ),
                new TicketTrendResponse(
                        TicketStatus.CLOSED.name(),
                        ticketRepository.countByStatus(TicketStatus.CLOSED)
                )
        );
    }

    // USED BY TicketAnalyticsController (/analytics/tickets)
    public AnalyticsResponse getTicketAnalytics() {

        long open = ticketRepository.countByStatus(TicketStatus.OPEN);
        long inProgress = ticketRepository.countByStatus(TicketStatus.IN_PROGRESS);
        long resolved = ticketRepository.countByStatus(TicketStatus.RESOLVED);
        long closed = ticketRepository.countByStatus(TicketStatus.CLOSED);

        long total = open + inProgress + resolved + closed;

        return new AnalyticsResponse(
                total,
                open + inProgress,
                closed,
                0,      // SLA breaches (placeholder)
                0.0     // Avg resolution time (placeholder)
        );
    }
}