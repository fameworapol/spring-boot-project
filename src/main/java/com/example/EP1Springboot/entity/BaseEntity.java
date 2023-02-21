package com.example.EP1Springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

//ให้ทุกๆ table มี id
@Data
@MappedSuperclass //🍎เพื่อให้คลาสลูกที่สืบทอดไปใช้ จะมี id เสมอ
public abstract class BaseEntity {
    @Id //🦷กำหนดให้ id เป็น primary key
    // generated ID ที่ไม่ซ้ำกันด้วย UUID
    @GenericGenerator(name = "uuid2",strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY , generator = "uuid2")
    @Column(length = 36,nullable = false,updatable = false) //ยาวสูงสุด 36 ตัว,ห้ามเป็น null,ห้าม update
    private String id; //column id

}
/*package com.example.EP1Springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

//🍎class สำหรับจัดการ database (entity คือ 1 table)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user") //กำหนดให้ table ชื่อ m_user
public class user extends BaseEntity{ //สืบทอดมาจาก BaseEntity
    //🦷กำหนดว่าจะให้ table m_user มี column อะไรบ้าง?
    @Column(nullable = false,unique = true,length = 60) //ห้ามเป็นค่าว่าง,ไม่ซ้ำ,ยาวสุด 60 ตัวอักษร
    private String email;

    @Column(nullable = false,length = 120)
    private String password;

    @Column(nullable = false,length = 120)
    private String name;

    //🍎 ตั้งค่าเสร็จกดรันแอปแล้วจะ create table ใน database โดยอัติโนมัติ
}
*/
