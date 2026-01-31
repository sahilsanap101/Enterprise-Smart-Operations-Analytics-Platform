package com.enterprise.ops.backend.ticket;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enterprise.ops.backend.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // CREATE TICKET
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<Ticket>> create(
            @RequestBody TicketRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket created",
                        ticketService.createTicket(request, principal.getName())
                )
        );
    }

    // MY CREATED TICKETS
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<Page<Ticket>>> myTickets(
            Principal principal,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "My tickets",
                        ticketService.getMyTickets(principal.getName(), pageable)
                )
        );
    }

    // ASSIGNED TO ME
    @GetMapping("/assigned")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<Page<Ticket>>> assignedTickets(
            Principal principal,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Assigned tickets",
                        ticketService.getAssignedTickets(principal.getName(), pageable)
                )
        );
    }

    // ASSIGN TICKET
    @PostMapping("/{ticketId}/assign/{assigneeId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Ticket>> assign(
            @PathVariable Long ticketId,
            @PathVariable Long assigneeId,
            Principal principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket assigned",
                        ticketService.assignTicket(
                                ticketId,
                                assigneeId,
                                principal.getName()
                        )
                )
        );
    }

    // UPDATE STATUS
    @PutMapping("/{ticketId}/status")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<Ticket>> updateStatus(
            @PathVariable Long ticketId,
            @RequestParam TicketStatus status,
            Principal principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Status updated",
                        ticketService.updateStatus(
                                ticketId,
                                status,
                                principal.getName()
                        )
                )
        );
    }
}