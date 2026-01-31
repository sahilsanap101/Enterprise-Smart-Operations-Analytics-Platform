package com.enterprise.ops.backend.admin;

import com.enterprise.ops.backend.user.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private Role role;

    // Employee details
    @NotBlank
    private String name;

    @NotBlank
    private String designation;

    @NotBlank
    private String department;
}