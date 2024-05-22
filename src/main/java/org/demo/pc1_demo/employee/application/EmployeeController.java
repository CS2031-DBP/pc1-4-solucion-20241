package org.demo.pc1_demo.employee.application;

import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // (TEST) obtener la información del empleado
    @GetMapping("/me")
    public ResponseEntity<Employee> getEmployee() {
        return ResponseEntity.ok(employeeService.getEmployeeOwnInfo());
    }

}
