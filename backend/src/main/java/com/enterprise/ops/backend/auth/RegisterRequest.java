package com.enterprise.ops.backend.auth;

import com.enterprise.ops.backend.user.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
