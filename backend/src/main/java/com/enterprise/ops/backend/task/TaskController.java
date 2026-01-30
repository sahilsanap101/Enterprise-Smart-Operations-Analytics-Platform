package com.enterprise.ops.backend.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // MANAGER creates task
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Task> createTask(
            @RequestBody CreateTaskRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                taskService.createTask(
                        request.getTitle(),
                        request.getDescription(),
                        request.getProjectId(),
                        request.getEmployeeId(),
                        principal.getName()
                )
        );
    }

    // EMPLOYEE views own tasks
    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Task>> getMyTasks(Principal principal) {
        return ResponseEntity.ok(
                taskService.getMyTasks(principal.getName())
        );
    }

    // EMPLOYEE updates task status
    @PutMapping("/{taskId}/status")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status,
            Principal principal
    ) {
        return ResponseEntity.ok(
                taskService.updateStatus(taskId, status, principal.getName())
        );
    }
}

/* ---------- REQUEST DTO ---------- */

class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long projectId;

    @NotNull
    private Long employeeId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
}
