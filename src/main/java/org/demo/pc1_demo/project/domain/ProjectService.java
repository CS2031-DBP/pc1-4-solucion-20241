package org.demo.pc1_demo.project.domain;

import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.EmployeeService;
import org.demo.pc1_demo.employee.domain.Role;
import org.demo.pc1_demo.employee.exceptions.EmployeeNotFoundException;
import org.demo.pc1_demo.employee.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.demo.pc1_demo.project.exceptions.ProjectNotFoundException;
import org.demo.pc1_demo.project.infrastructure.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    // Cualquier Empleado puede crear un proyecto
    public void createProject(Project project) {
        Employee employee = authorizationUtils.getCurrentEmployee();

        project.setLeader(employee);
        projectRepository.save(project);

        // El empleado que crea el proyecto se convierte en líder
        employee.getProjects().add(project);
        employee.setRole(Role.LEADER);
        employeeRepository.save(employee);
    }

    // Solo el líder del proyecto puede agregar empleados
    public void addEmployee(Long projectId, Long employeeId) {
        if (!authorizationUtils.isProjectLeader(projectId))
            throw new UnauthorizeOperationException("Employee has no permission to modify this resource");

        // Se obtiene el empleado y el proyecto
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project not found"));

        // Se agrega el empleado al proyecto y se guarda
        project.getEmployees().add(employee);
        projectRepository.save(project);

        // Se agrega el proyecto al empleado y se guarda
        employee.getProjects().add(project);
        employeeRepository.save(employee);
    }

    // Obtener una lista de proyectos activos (aún no finalizados)
    public List<Project> getActiveProjects() {
        ZonedDateTime now = ZonedDateTime.now();
        return projectRepository.findByEndDateGreaterThanEqual(now);
    }

    // Solo el líder del proyecto puede eliminar un proyecto
    public void deleteProject(Long id) {
        if (!authorizationUtils.isProjectLeader(id))
            throw new UnauthorizeOperationException("Employee has no permission to modify this resource");

        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not found"));

        projectRepository.delete(project);
    }
}
