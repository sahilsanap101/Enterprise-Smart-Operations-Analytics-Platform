package com.enterprise.ops.backend.employee;

import com.enterprise.ops.backend.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Employee entity represents an employee in the organization.
 * Each employee is linked to a User account.
 */
@Entity
@Table(name = "employees")
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

    /*
     * Whether employee is active in the organization
     */
    private boolean active;

    /*
     * Many employees can be linked to one user (optional design choice)
     * You can also make this OneToOne later.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
