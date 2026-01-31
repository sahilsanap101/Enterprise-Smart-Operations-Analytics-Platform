package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.analytics.dto.ProjectRiskResponse;
import com.enterprise.ops.backend.analytics.dto.ProjectWorkloadResponse;
import com.enterprise.ops.backend.assignment.Assignment;
import com.enterprise.ops.backend.assignment.AssignmentRepository;
import com.enterprise.ops.backend.project.Project;
import com.enterprise.ops.backend.project.ProjectRepository;

@Service
public class ProjectAnalyticsService {

    private final ProjectRepository projectRepository;
    private final AssignmentRepository assignmentRepository;

    public ProjectAnalyticsService(
            ProjectRepository projectRepository,
            AssignmentRepository assignmentRepository
    ) {
        this.projectRepository = projectRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public List<ProjectWorkloadResponse> getProjectWorkload() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapWorkload)
                .toList();
    }

    public List<ProjectRiskResponse> getProjectRisks() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapRisk)
                .toList();
    }

    private ProjectWorkloadResponse mapWorkload(Project project) {

        int totalAllocation = assignmentRepository
                .findByProject(project)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        String status =
                totalAllocation > 100 ? "OVER_ALLOCATED"
                : totalAllocation < 60 ? "UNDER_STAFFED"
                : "BALANCED";

        return new ProjectWorkloadResponse(
                project.getId(),
                project.getName(),
                totalAllocation,
                status
        );
    }

    private ProjectRiskResponse mapRisk(Project project) {

        int allocation = assignmentRepository
                .findByProject(project)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        if (allocation < 50) {
            return new ProjectRiskResponse(
                    project.getId(),
                    project.getName(),
                    "HIGH",
                    "Low staffing allocation"
            );
        }

        return new ProjectRiskResponse(
                project.getId(),
                project.getName(),
                "LOW",
                "Sufficient staffing"
        );
    }
}