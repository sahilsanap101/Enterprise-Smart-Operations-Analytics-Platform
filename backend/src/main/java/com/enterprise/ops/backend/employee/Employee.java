package com.enterprise.ops.backend.employee;

import com.enterprise.ops.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "employees",
    indexes = {
        @Index(name = "idx_employee_user", columnList = "user_id"),
        @Index(name = "idx_employee_department", columnList = "department")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String designation;
    private String department;
    private boolean active;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}