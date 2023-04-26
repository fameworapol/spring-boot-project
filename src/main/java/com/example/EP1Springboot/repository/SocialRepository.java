package com.example.EP1Springboot.repository;

import com.example.EP1Springboot.entity.Social;
import com.example.EP1Springboot.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//🍎Repo สำหรับสำหรับ table m_social
public interface SocialRepository extends CrudRepository<Social,String> { //primary key คือ String id
    //เราสามารถสร้าง method เพิ่มจากที่มีใน CrudRepository ได้
    //🦷สร้าง method social จาก user
    Optional<Social> findByUser(User user);
}