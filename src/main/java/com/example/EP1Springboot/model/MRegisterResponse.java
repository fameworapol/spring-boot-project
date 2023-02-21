package com.example.EP1Springboot.model;

import lombok.Data;

@Data
public class MRegisterResponse {
    //🍎ใช้ mapping ในการส่งข้อมูลกลับไปหา user (แทนที่จะ response จาก API โดยตรง)
    //กำหนดให้ response ไปแค่ email กับ name ก็พอ
    private String name;
    private String email;
}
