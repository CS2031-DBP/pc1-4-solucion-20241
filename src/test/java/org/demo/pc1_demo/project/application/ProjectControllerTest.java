package org.demo.pc1_demo.project.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.Role;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.demo.pc1_demo.project.domain.Project;
import org.demo.pc1_demo.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    String token = "";

    // Crear un empleado autorizado con Mockito (Se prueba el endpoint "/auth/register")
    public void createAuthorizedEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@gmail.com");
        employee.setPassword("password");
        employee.setDepartment("IT");
        employee.setRole(Role.EMPLOYEE);
        employee.setDateAdmission(ZonedDateTime.now());

        var res = mockMvc.perform(post("/auth/register")
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseContent = res.getResponse().getContentAsString();
        token = responseContent.substring(10, responseContent.length() - 2);
    }

    // Creación de dos proyectos
    @BeforeEach
    public void setUp() throws Exception {
        Project project1 = new Project();
        project1.setName("Project 1");
        project1.setDescription("Description 1");
        project1.setStartDate(ZonedDateTime.parse("2024-04-10T05:15:30-05:00"));
        project1.setEndDate(ZonedDateTime.parse("2024-06-10T05:15:30-05:00"));
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Description 2");
        project2.setStartDate(ZonedDateTime.parse("2024-04-10T05:15:30-05:00"));
        project2.setEndDate(ZonedDateTime.parse("2024-06-10T05:15:30-05:00"));
        projectRepository.save(project2);
    }

    // Test con mockito para probar el endpoint "/projects/active"
    @Test
    public void getActiveProjectsTest() throws Exception{
        mockMvc.perform(get("/projects/active"))
                .andExpect(status().isOk());
    }

    // Test con mockito para probar el endpoint "/projects"
    @Test
    public void createProjectTest() throws Exception {
        // Crear un empleado autorizado
        createAuthorizedEmployee();

        Project project = new Project();
        project.setName("Project 3");
        project.setDescription("Description 3");
        project.setStartDate(ZonedDateTime.parse("2024-04-10T05:15:30-05:00"));
        project.setEndDate(ZonedDateTime.parse("2024-06-10T05:15:30-05:00"));

        // Probar el endpoint "/projects" con el token del empleado autorizado
        mockMvc.perform(post("/projects")
                        .content(objectMapper.writeValueAsString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
