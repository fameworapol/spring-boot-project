package com.example.EP1Springboot.repository;

import com.example.EP1Springboot.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

import java.util.Optional;

//🍎Repo สำหรับสำหรับ table m_user
public interface UserRepository extends CrudRepository<User,String> { //primary key คือ String id
    //เราสามารถสร้าง method เพิ่มจากที่มีใน CrudRepository ได้
    //🦷สร้าง method หา user จาก email > findByEmail
    Optional<User> findByEmail(String email);
    //เช็คว่า email ซ้ำหรือไม่?
    boolean existsByEmail(String email); //จะ return เป็น true/false
}