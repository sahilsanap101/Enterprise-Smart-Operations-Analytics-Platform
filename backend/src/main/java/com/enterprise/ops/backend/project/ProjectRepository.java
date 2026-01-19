package com.enterprise.ops.backend.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.ops.backend.user.User;

/*
 * Repository for Project entity
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByManager(User manager);
}
