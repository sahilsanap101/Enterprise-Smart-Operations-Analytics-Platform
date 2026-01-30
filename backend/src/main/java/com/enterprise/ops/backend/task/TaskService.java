package com.enterprise.ops.backend.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.employee.EmployeeRepository;
import com.enterprise.ops.backend.project.Project;
import com.enterprise.ops.backend.project.ProjectRepository;
import com.enterprise.ops.backend.user.Role;
import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    // MANAGER creates task
    public Task createTask(
            String title,
            String description,
            Long projectId,
            Long employeeId,
            String managerEmail
    ) {
        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only managers can create tasks");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setProject(project);
        task.setAssignedEmployee(employee);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    // EMPLOYEE views own tasks
    public List<Task> getMyTasks(String employeeEmail) {
        Employee employee = employeeRepository
                .findByUserEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return taskRepository.findByAssignedEmployee(employee);
    }

    // EMPLOYEE updates task status
    public Task updateStatus(
            Long taskId,
            TaskStatus newStatus,
            String employeeEmail
    ) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Employee employee = employeeRepository
                .findByUserEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!task.getAssignedEmployee().getId().equals(employee.getId())) {
            throw new RuntimeException("You are not assigned to this task");
        }

        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
}
