package com.enterprise.ops.backend.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryRepository
        extends JpaRepository<TicketHistory, Long> {

    List<TicketHistory> findByTicketId(Long ticketId);
}