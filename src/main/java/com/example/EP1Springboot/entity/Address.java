package com.example.EP1Springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

//🍎class สำหรับจัดการ database (entity คือ 1 table)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_address") //กำหนดให้ table ชื่อ m_address
public class Address extends BaseEntity{ //สืบทอดมาจาก BaseEntity จึงมี attribute id ของ BaseEntity
    //🦷กำหนดว่าจะให้ table m_address มี column อะไรบ้าง?
    @Column(length = 60) //ยาวสุด 60 ตัวอักษร
    private String line1;
    @Column(length = 120)
    private String line2;
    @Column(length = 120)
    private String zipcode;

    //Address link หา User แบบ many to one
    @ManyToOne
    @JoinColumn(name = "m_user_id",nullable = false) //กำหนดให้ m_user_id เป็น foreign key
    private User user;
}

