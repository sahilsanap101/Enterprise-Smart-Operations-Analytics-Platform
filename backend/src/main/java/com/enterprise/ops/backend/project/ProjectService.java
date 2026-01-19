package com.enterprise.ops.backend.project;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.user.User;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        User manager
    ){
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(ProjectStatus.PLANNED);
        project.setManager(manager);

        return projectRepository.save(project);
     }

     public List<Project> getProjectsByManager(User manager){
        return projectRepository.findByManager(manager);
     }

     public List<Project> getAllProjects(){
        return projectRepository.findAll();
     }
}
