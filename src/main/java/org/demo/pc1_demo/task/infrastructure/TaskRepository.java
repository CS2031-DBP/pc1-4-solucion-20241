package org.demo.pc1_demo.task.infrastructure;

import org.demo.pc1_demo.task.domain.Status;
import org.demo.pc1_demo.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
}
