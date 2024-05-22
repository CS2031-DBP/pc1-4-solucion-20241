package org.demo.pc1_demo.project.application;

import org.demo.pc1_demo.project.domain.Project;
import org.demo.pc1_demo.project.domain.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // Crear un proyecto
    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody Project project) {
        projectService.createProject(project);
        return ResponseEntity.noContent().build();
    }

    // Añadir un empleado a un proyecto
    @PostMapping("/addEmployee")
    public ResponseEntity<Void> addEmployee(@RequestParam Long projectId, @RequestParam Long employeeId) {
        projectService.addEmployee(projectId, employeeId);
        return ResponseEntity.noContent().build();
    }

    // Obtener una lista de proyectos activos
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        return ResponseEntity.ok(projectService.getActiveProjects());
    }

    // Eliminar un proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
