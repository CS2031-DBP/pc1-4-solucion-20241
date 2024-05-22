package org.demo.pc1_demo.task.domain;

import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.EmployeeService;
import org.demo.pc1_demo.employee.exceptions.EmployeeNotFoundException;
import org.demo.pc1_demo.employee.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.demo.pc1_demo.project.domain.Project;
import org.demo.pc1_demo.project.exceptions.ProjectNotFoundException;
import org.demo.pc1_demo.project.infrastructure.ProjectRepository;
import org.demo.pc1_demo.task.exceptions.TaskNotFoundException;
import org.demo.pc1_demo.task.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    // Solo los empleados que pertenecen al proyecto pueden crear tareas
    public void createTask(Task task, Long projectId) {
        if (!authorizationUtils.isPartOfProject(projectId))
            throw new UnauthorizeOperationException("Employee has no permission to modify this resource");

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project not found"));

        // Se asigna a la tarea el proyecto al que pertenece y se guarda
        task.setProject(project);
        taskRepository.save(task);

        // Se agrega la tarea al proyecto y se guarda
        project.getTasks().add(task);
        projectRepository.save(project);
    }

    // Solo los empleados que pertenecen al proyecto pueden asignar tareas
    public void assignTask(Long taskId, Long employeeId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task not found"));

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found"));

        if (!authorizationUtils.isPartOfProject(task.getProject().getId()))
            throw new UnauthorizeOperationException("Employee has no permission to modify this resource");

        // Se asigna la tarea al empleado y se guarda
        task.setResponsible(employee);
        taskRepository.save(task);

        // Se agrega la tarea al empleado y se guarda
        employee.getTasks().add(task);
        employeeRepository.save(employee);
    }

    // Solo los empleados que pertenecen al proyecto pueden cambiar el estado de las tareas
    public void changeTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task not found"));

        if (!authorizationUtils.isPartOfProject(task.getProject().getId()))
            throw new UnauthorizeOperationException("Employee has no permission to modify this resource");

        // Se cambia el estado de la tarea y se guarda
        switch (status) {
            case "TODO":
                task.setStatus(Status.TODO);
                break;
            case "DOING":
                task.setStatus(Status.DOING);
                break;
            case "DONE":
                task.setStatus(Status.DONE);
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }

        taskRepository.save(task);
    }

    // Filtrar todas las tareas por estado
    public List<Task> getTaskByStatus(String status) {
        return switch (status) {
            case "TODO" -> taskRepository.findByStatus(Status.TODO);
            case "DOING" -> taskRepository.findByStatus(Status.DOING);
            case "DONE" -> taskRepository.findByStatus(Status.DONE);
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }
}
