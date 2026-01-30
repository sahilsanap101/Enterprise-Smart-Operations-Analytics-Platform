package com.enterprise.ops.backend.task;

import java.time.LocalDateTime;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.project.Project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "tasks",
    indexes = {
        @Index(name = "idx_task_employee", columnList = "employee_id"),
        @Index(name = "idx_task_project", columnList = "project_id"),
        @Index(name = "idx_task_status", columnList = "status")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    // Task belongs to a project
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Employee working on the task
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee assignedEmployee;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
