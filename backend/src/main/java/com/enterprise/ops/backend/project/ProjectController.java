package com.enterprise.ops.backend.project;

import jakarta.validation.Valid;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    public ProjectController(
            ProjectService projectService,
            UserRepository userRepository
    ) {
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    // ADMIN or MANAGER can create projects
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Project> createProject(
            @Valid @RequestBody ProjectRequest request,
            Principal principal
    ) {
        User manager = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Project project = projectService.createProject(
                request.getName(),
                request.getDescription(),
                LocalDate.parse(request.getStartDate()),
                LocalDate.parse(request.getEndDate()),
                manager
        );

        return ResponseEntity.ok(project);
    }

    // ADMIN, MANAGER, EMPLOYEE can view all projects (FIXED)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // MANAGER can view only their projects
    @GetMapping("/my")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Project>> getMyProjects(Principal principal) {
        User manager = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return ResponseEntity.ok(
                projectService.getProjectsByManager(manager)
        );
    }
}