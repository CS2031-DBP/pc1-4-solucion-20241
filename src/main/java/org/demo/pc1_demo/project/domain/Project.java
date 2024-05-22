package org.demo.pc1_demo.project.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.task.domain.Task;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(scope = Project.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private ZonedDateTime startDate;

    @Column(nullable = false)
    private ZonedDateTime endDate;

    // Un proyecto tiene un líder
    // Si se elimina un proyecto, no se elimina el líder
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Employee leader;

    // Un proyecto tiene uno o más empleados
    // Si se elimina un proyecto, no se eliminan los empleados
    @OneToMany(fetch = FetchType.LAZY)
    private List<Employee> employees;

    // Un proyecto tiene una o más tareas
    // Si se elimina un proyecto, se eliminan las tareas
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}
