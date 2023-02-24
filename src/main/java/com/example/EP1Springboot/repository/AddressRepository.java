package com.example.EP1Springboot.repository;

import com.example.EP1Springboot.entity.Address;
import com.example.EP1Springboot.entity.Social;
import com.example.EP1Springboot.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//🍎Repo สำหรับสำหรับ table m_user
public interface AddressRepository extends CrudRepository<Address,String> { //primary key คือ String id
    //เราสามารถสร้าง method เพิ่มจากที่มีใน CrudRepository ได้
    //🦷สร้าง method หา social จาก user
    List<Address> findByUser(User user); //return เป็น list ของ user
}