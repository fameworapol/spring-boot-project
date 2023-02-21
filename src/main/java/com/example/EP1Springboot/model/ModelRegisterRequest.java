package com.example.EP1Springboot.model;

import lombok.Data;

@Data
public class ModelRegisterRequest {
    //กำหนดให้การ register ต้องส่งตัวแปรด้านล่างนี้
    private String email;
    private String password;
    private String name;

}
