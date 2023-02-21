package com.example.EP1Springboot.mapper;

import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.model.MRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //🦷 Map class User กับ MRegisterResponse => จะเอาฟิลด์ที่คล้ายกันไป map กัน
    //user มี email,password,name แต่ MRegisterResponse มี email,name ❗️จะเอาฟิลด์ที่ชื่อเหมือนกันมาเซ็ตค่าให้
    MRegisterResponse toRegisterResponse(User user);
}
