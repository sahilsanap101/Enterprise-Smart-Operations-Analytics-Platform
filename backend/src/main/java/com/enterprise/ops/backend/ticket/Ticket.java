package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

import com.enterprise.ops.backend.project.Project;
import com.enterprise.ops.backend.user.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "tickets",
    indexes = {
        @Index(name = "idx_ticket_status", columnList = "status"),
        @Index(name = "idx_ticket_created_by", columnList = "created_by"),
        @Index(name = "idx_ticket_assigned_to", columnList = "assigned_to"),
        @Index(name = "idx_ticket_sla_deadline", columnList = "slaDeadline")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    // EMPLOYEE who raised the ticket
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // MANAGER assigned to handle ticket
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    // Related project
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // SLA deadline
    @Column(nullable = false)
    private LocalDateTime slaDeadline;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
