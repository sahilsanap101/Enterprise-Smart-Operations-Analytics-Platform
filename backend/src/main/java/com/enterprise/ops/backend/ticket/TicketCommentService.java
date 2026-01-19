package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

@Service
public class TicketCommentService {

    private final TicketRepository ticketRepository;
    private final TicketCommentRepository commentRepository;
    private final UserRepository userRepository;

    public TicketCommentService(
            TicketRepository ticketRepository,
            TicketCommentRepository commentRepository,
            UserRepository userRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public TicketComment addComment(
            Long ticketId,
            String commentText,
            String userEmail
    ) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setCommentedBy(user);
        comment.setComment(commentText);
        comment.setCommentedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }
}
