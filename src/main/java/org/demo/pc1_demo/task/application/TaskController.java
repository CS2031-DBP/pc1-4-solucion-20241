package org.demo.pc1_demo.task.application;

import org.demo.pc1_demo.task.domain.Task;
import org.demo.pc1_demo.task.domain.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Controlador para crear una tarea
    @PostMapping("/add")
    public ResponseEntity<Void> createTask(@RequestBody Task task, @RequestParam Long projectId) {
        taskService.createTask(task, projectId);
        return ResponseEntity.noContent().build();
    }

    // Controlador para asignar una tarea a un empleado
    @PatchMapping("/assign")
    public ResponseEntity<Void> assignTask(@RequestParam Long taskId, @RequestParam Long employeeId) {
        taskService.assignTask(taskId, employeeId);
        return ResponseEntity.noContent().build();
    }

    // Controlador para modificar el estado de una tarea
    @PatchMapping("/status")
    public ResponseEntity<Void> changeTaskStatus(@RequestParam Long taskId, @RequestParam String status) {
        taskService.changeTaskStatus(taskId, status);
        return ResponseEntity.noContent().build();
    }

    // Filtrar tareas por estado
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTaskByStatus(@RequestParam String status) {
        return ResponseEntity.ok(taskService.getTaskByStatus(status));
    }
}
