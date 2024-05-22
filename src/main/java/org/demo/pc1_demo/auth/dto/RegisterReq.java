package org.demo.pc1_demo.auth.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String department;
    private Boolean isLeader=false;
}
