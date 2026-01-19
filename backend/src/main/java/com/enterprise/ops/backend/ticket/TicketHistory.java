package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

import com.enterprise.ops.backend.user.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
@NoArgsConstructor
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    private String action; // STATUS_CHANGED, ASSIGNED, CLOSED

    private String oldValue;
    private String newValue;

    @ManyToOne
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;

    private LocalDateTime performedAt;
}
