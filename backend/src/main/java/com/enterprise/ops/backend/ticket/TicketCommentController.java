package com.enterprise.ops.backend.ticket;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets/{ticketId}/comments")
public class TicketCommentController {

    private final TicketCommentService commentService;
    private final TicketCommentRepository commentRepository;

    public TicketCommentController(
            TicketCommentService commentService,
            TicketCommentRepository commentRepository
    ) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    // EMPLOYEE / MANAGER / ADMIN can comment
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<TicketComment> addComment(
            @PathVariable Long ticketId,
            @Valid @RequestBody TicketCommentRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                commentService.addComment(
                        ticketId,
                        request.getComment(),
                        principal.getName()
                )
        );
    }

    // View comments
    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
    public ResponseEntity<List<TicketComment>> getComments(
            @PathVariable Long ticketId
    ) {
        return ResponseEntity.ok(
                commentRepository.findByTicketId(ticketId)
        );
    }
}
