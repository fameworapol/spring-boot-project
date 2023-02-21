package com.example.EP1Springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

//🍎class สำหรับจัดการ database (entity คือ 1 table)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user") //กำหนดให้ table ชื่อ m_user
public class User extends BaseEntity{ //สืบทอดมาจาก BaseEntity จึงมี attribute id ของ BaseEntity
    //🦷กำหนดว่าจะให้ table m_user มี column อะไรบ้าง?
    @Column(nullable = false,unique = true,length = 60) //ห้ามเป็นค่าว่าง,ไม่ซ้ำ,ยาวสุด 60 ตัวอักษร
    private String email;

    //กำหนดให้คอลัมน์ password ไป return กลับไปหา user
    @Column(nullable = false,length = 120)
    private String password;

    @Column(nullable = false,length = 120)
    private String name;

    //🍎 ตั้งค่าเสร็จกดรันแอปแล้วจะ create table ใน database โดยอัติโนมัติ
}

