package com.enterprise.ops.backend.ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enterprise.ops.backend.user.User;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByCreatedBy(User user, Pageable pageable);

    Page<Ticket> findByAssignedTo(User user, Pageable pageable);

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.status = :status
    """)
    long countByStatus(@Param("status") TicketStatus status);
}
