package com.example.EP1Springboot.service;

import com.example.EP1Springboot.entity.Address;
import com.example.EP1Springboot.entity.Social;
import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.repository.AddressRepository;
import com.example.EP1Springboot.repository.SocialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//layer ที่ใช้ interaction กับ repository
@Service
public class AddressService {
    //Inject userRepository เข้ามาใช้งาน
    private final AddressRepository repository;
    //constructor
    public AddressService(AddressRepository repository){
        this.repository = repository;
    }

    //method สำหรับการค้นหา email ใน database
    public List<Address> findByUser(User user){
        return repository.findByUser(user);
    }

    public Address create(User user,String line1,String line2,String zipcode){
        //TODO : Validation
        //Create
        Address entity = new Address();

        //set value to attribute
        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return repository.save(entity); //save to db
    }

}
