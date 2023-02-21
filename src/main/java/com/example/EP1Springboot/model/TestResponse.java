package com.example.EP1Springboot.model;

import lombok.Data;

//🥶 ใช้ lombok generated getter,setter method ให้เรา
@Data // object ที่สร้างจาก class Test สามารถกำหนดค่าให้ Attribute ได้เลย
public class TestResponse {
    //กำหนด key ที่จะส่งเป็น json ออกไป
    private String name;
    private int age;
    private String studentid;

}
