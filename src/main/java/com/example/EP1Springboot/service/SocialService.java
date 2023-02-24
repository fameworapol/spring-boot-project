package com.example.EP1Springboot.service;

import com.example.EP1Springboot.entity.Social;
import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.repository.SocialRepository;
import com.example.EP1Springboot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

//layer ที่ใช้ interaction กับ repository
@Service
public class SocialService {
    //Inject userRepository เข้ามาใช้งาน
    private final SocialRepository repository;
    //constructor
    public SocialService(SocialRepository repository){
        this.repository = repository;
    }

    //method สำหรับการค้นหา email ใน database
    public Optional<Social> findByUser(User user){
        return repository.findByUser(user);
    }

    public Social create(User user,String facebook,String line,String instragram,String tiktok){

        //TODO : Validation
        //Create
        Social entity = new Social();

        //set value to attribute
        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instragram);
        entity.setTiktok(tiktok);

        return repository.save(entity); //save to db
    }

}
