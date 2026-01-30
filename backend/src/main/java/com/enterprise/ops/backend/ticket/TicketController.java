package com.enterprise.ops.backend.ticket;

import com.enterprise.ops.backend.common.ApiResponse;
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

    // ✅ EMPLOYEE raises ticket
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<Ticket>> createTicket(
            @Valid @RequestBody TicketRequest request,
            Principal principal
    ) {
        Ticket ticket = ticketService.createTicket(
                request,
                principal.getName()
        );

        return ResponseEntity.ok(
                ApiResponse.success("Ticket created successfully", ticket)
        );
    }

    // ✅ ADMIN assigns ticket to manager
    @PostMapping("/{ticketId}/assign/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Ticket>> assignTicket(
            @PathVariable Long ticketId,
            @PathVariable Long managerId
    ) {
        Ticket ticket = ticketService.assignTicket(ticketId, managerId);

        return ResponseEntity.ok(
                ApiResponse.success("Ticket assigned successfully", ticket)
        );
    }

    // ✅ EMPLOYEE views own tickets
    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<?>> getMyTickets(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "My tickets fetched",
                        ticketService.getTicketsByEmployee(
                                principal.getName(),
                                page,
                                size,
                                sortBy,
                                direction
                        )
                )
        );
    }

    // ✅ MANAGER views assigned tickets
    @GetMapping("/assigned")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<?>> getAssignedTickets(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Assigned tickets fetched",
                        ticketService.getTicketsAssignedToManager(
                                principal.getName(),
                                page,
                                size,
                                sortBy,
                                direction
                        )
                )
        );
    }
}
