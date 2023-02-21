package com.example.EP1Springboot.model;

import lombok.Data;

@Data
public class MLogInRequest {
    //model สำหรับ login
    private String email;
    private String password;
}
