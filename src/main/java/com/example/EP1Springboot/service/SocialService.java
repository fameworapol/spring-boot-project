package com.example.EP1Springboot.service;

import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.EP1Springboot.entity.User;

import java.util.Objects;
import java.util.Optional;

//layer ที่ใช้ interaction กับ repository
@Service
public class UserService {
    //Inject userRepository เข้ามาใช้งาน
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder; //นำเข้าตัว encoder
    //constructor
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //method สำหรับการค้นหา email ใน database
    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword,String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword); //เช็คว่า password ที่ encode ไว้ตรงกับที่ส่งมารึเปล่า
    }

    //🍎method สำหรับ create User
    public User create(String email,String password,String name) throws UserException{
        User enity = new User();
        //🦷validate เช็คว่าข้อมูลไม่ใช่ค่าว่าง
        if (Objects.isNull(email)){
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(password)){
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(name)){
            throw UserException.createNameNull();
        }
        //🦷verify
        //เช็คว่า email ซ้ำหรือไม่?
        if (repository.existsByEmail(email)){
            throw UserException.emailAlreadyExist();
        }

        //🦷set ค่าที่รับมาลงไปที่ตัวแปรใน baseEntity
        enity.setEmail(email);
        enity.setName(name);
        enity.setPassword(passwordEncoder.encode(password)); //ตอนนำ password เก็บที่ database จะเข้ารหัสไว้่

        return repository.save(enity); //Save ข้อมูลจาก entity ลง database
    }

    /*
    update ทั้งก้อนเลย
    public User update(User user){
        return repository.save(user);
    }*/

    //Method สำหรับ update ค่าใน database
    //update เฉพาะ name
    public User updateName(String id,String name) throws BaseException { //รับ id,name
        Optional<User> opt = repository.findById(id); //ค้นหา user จาก id > รับค่าเป็น user
        if(opt.isEmpty()){
            throw UserException.notFound();
        }
        User user = opt.get(); //get ข้อมูล user
        user.setName(name); //กำหนดค่าใหม่ให้คอลัมน์ name
        return repository.save(user); //save ค่าลงใน database
    }

    public void deleteById(String id){
        repository.deleteById(id); //ลบข้อมูลตาม id
    }
}
