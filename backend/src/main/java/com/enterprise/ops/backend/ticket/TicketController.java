package com.enterprise.ops.backend.ticket;

import jakarta.validation.Valid;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // EMPLOYEE raises ticket
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Ticket> createTicket(
            @Valid @RequestBody TicketRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                ticketService.createTicket(request, principal.getName())
        );
    }

    // MANAGER assigns ticket
    @PostMapping("/{ticketId}/assign/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ticket> assignTicket(
            @PathVariable Long ticketId,
            @PathVariable Long managerId
    ) {
        return ResponseEntity.ok(
                ticketService.assignTicket(ticketId, managerId)
        );
    }
}
