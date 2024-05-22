package org.demo.pc1_demo.task.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.project.domain.Project;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Data
@JsonIdentityInfo(scope = Task.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private ZonedDateTime deadline;

    @Column(nullable = false)
    private Status status = Status.TODO;

    // Las tareas tienen solo un responsable
    // Si se elimina una tarea, el responsable no se elimina
    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private Employee responsible;

    // Las tareas pertenecen a un solo proyecto
    // Si se elimina una tarea, el proyecto no se eliminan
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
