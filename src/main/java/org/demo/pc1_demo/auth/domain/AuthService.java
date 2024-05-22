package org.demo.pc1_demo.auth.domain;

import org.demo.pc1_demo.auth.dto.JwtAuthResponse;
import org.demo.pc1_demo.auth.dto.LoginReq;
import org.demo.pc1_demo.auth.dto.RegisterReq;
import org.demo.pc1_demo.auth.exceptions.UserAlreadyExistException;
import org.demo.pc1_demo.config.JwtService;
import org.demo.pc1_demo.employee.domain.Employee;
import org.demo.pc1_demo.employee.domain.Role;
import org.demo.pc1_demo.employee.infrastructure.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthService(EmployeeRepository employeeRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    public JwtAuthResponse login(LoginReq req) {
        Optional<Employee> employee;
        employee = employeeRepository.findByEmail(req.getEmail());

        if (employee.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), employee.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(employee.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterReq req){
        Optional<Employee> user = employeeRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        Employee employee = new Employee();
        employee.setFirstName(req.getFirstName());
        employee.setLastName(req.getLastName());
        employee.setEmail(req.getEmail());
        employee.setPassword(passwordEncoder.encode(req.getPassword()));
        employee.setDepartment(req.getDepartment());
        employee.setDateAdmission(ZonedDateTime.now());

        if (req.getIsLeader()) {
            employee.setRole(Role.LEADER);

            employeeRepository.save(employee);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(employee));
            return response;
        }
        else {
            employee.setRole(Role.EMPLOYEE);

            employeeRepository.save(employee);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(employee));
            return response;
        }

    }
}
