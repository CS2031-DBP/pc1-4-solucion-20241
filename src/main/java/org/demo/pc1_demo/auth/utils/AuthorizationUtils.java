package org.demo.pc1_demo.auth.utils;

import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.EmployeeService;
import org.demo.pc1_demo.employee.domain.Role;
import org.demo.pc1_demo.employee.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.demo.pc1_demo.project.domain.Project;
import org.demo.pc1_demo.project.exceptions.ProjectNotFoundException;
import org.demo.pc1_demo.project.infrastructure.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Obtiene el empleado actual en base al JWT
    public Employee getCurrentEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() == "anonymousUser") {
            throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        if(user == null)
            throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");

        return employeeRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Employee not found"));
    }

    // Verifica si el empleado actual es el dueño del recurso
    public boolean isResourceOwner(Long id) {
        Employee employee = this.getCurrentEmployee();

        return employee.getId().equals(id);
    }

    // Verifica si el empleado actual es el líder del proyecto
    public boolean isProjectLeader(Long projectId) {
        Employee employee = this.getCurrentEmployee();

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project not found"));

        return project.getLeader().getId().equals(employee.getId());
    }

    // Verifica si el empleado actual es parte del proyecto
    public boolean isPartOfProject(Long projectId) {
        Employee employee = this.getCurrentEmployee();

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException("Project not found"));

        return project.getEmployees().contains(employee);
    }
}
