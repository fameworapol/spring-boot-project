package com.example.EP1Springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

//🍎class สำหรับจัดการ database (entity คือ 1 table)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_social") //กำหนดให้ table ชื่อ m_social
public class Social extends BaseEntity{ //สืบทอดมาจาก BaseEntity จึงมี attribute id ของ BaseEntity
    //🦷กำหนดว่าจะให้ table m_social มี column อะไรบ้าง?
    @Column(length = 60) //ยาวสุด 60 ตัวอักษร
    private String facebook;
    @Column(length = 120)
    private String line;
    @Column(length = 120)
    private String instagram;
    @Column(length = 120)
    private String tiktok;
    //เราต้องการให้ table m_social link กับ m_user
    @OneToOne
    @JoinColumn(name="m_user_id",nullable = false) //👉จะมีคอลัมน์ m_user_id ทำหน้าที่เป็น foreign key
    private User user;
}

