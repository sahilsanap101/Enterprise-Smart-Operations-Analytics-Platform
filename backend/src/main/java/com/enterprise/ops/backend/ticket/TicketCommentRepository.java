package com.enterprise.ops.backend.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCommentRepository
        extends JpaRepository<TicketComment, Long> {

    List<TicketComment> findByTicketId(Long ticketId);
}
