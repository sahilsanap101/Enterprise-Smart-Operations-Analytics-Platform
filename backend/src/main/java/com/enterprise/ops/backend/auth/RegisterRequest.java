package com.enterprise.ops.backend.auth;

/*
 * This class represents the data coming from the client
 * when a user wants to register.
 * 
 * DTO = Data Transfer Object
 * We use DTOs to avoid exposing our entity directly.
 */

public class RegisterRequest {
    
    private String email;

    private String password;

    private String role;

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}
