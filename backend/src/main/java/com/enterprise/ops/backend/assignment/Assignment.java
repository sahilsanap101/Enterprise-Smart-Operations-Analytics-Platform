package com.enterprise.ops.backend.assignment;

import java.time.LocalDate;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.project.Project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Assignment links Employee to Project with allocation %
 */
@Entity
@Table(
    name = "assignments",
    indexes = {
        @Index(name = "idx_assignment_employee", columnList = "employee_id"),
        @Index(name = "idx_assignment_project", columnList = "project_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private int allocationPercentage;

    @Column(nullable = false)
    private LocalDate assignedDate;
}
