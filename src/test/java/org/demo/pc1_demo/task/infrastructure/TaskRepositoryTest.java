package org.demo.pc1_demo.task.infrastructure;

import org.demo.pc1_demo.AbstractContainerBaseTest;
import org.demo.pc1_demo.task.domain.Task;
import org.demo.pc1_demo.task.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(Status.TODO);
        task1.setDeadline(ZonedDateTime.now());
        entityManager.persist(task1);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(Status.TODO);
        task2.setDeadline(ZonedDateTime.now());
        entityManager.persist(task2);
    }

    // Testcontainer para probar el repositorio de Task con la función especial "findByStatus"
    @Test
    public void testFindByStatus() {
        List<Task> tasks = taskRepository.findByStatus(Status.TODO);
        assertEquals(2, tasks.size());
    }
}