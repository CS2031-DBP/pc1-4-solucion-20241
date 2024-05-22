package org.demo.pc1_demo.employee.domain;

import org.demo.pc1_demo.employee.exceptions.EmployeeNotFoundException;
import org.demo.pc1_demo.employee.exceptions.UnauthorizeOperationException;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.demo.pc1_demo.auth.utils.AuthorizationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    public Employee getEmployeeOwnInfo() {
        return authorizationUtils.getCurrentEmployee();
    }


    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            Employee employee = employeeRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
            return (UserDetails) employee;
        };
    }
}
