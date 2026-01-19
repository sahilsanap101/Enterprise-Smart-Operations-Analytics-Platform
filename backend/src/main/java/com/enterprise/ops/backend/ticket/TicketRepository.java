package com.enterprise.ops.backend.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.ops.backend.user.User;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedBy(User user);

    List<Ticket> findByAssignedTo(User user);

    List<Ticket> findByStatus(TicketStatus status);
}
