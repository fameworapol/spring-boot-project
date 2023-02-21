package com.example.EP1Springboot.business;

import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.FileException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.mapper.UserMapper;
import com.example.EP1Springboot.model.MLogInRequest;
import com.example.EP1Springboot.model.MRegisterResponse;
import com.example.EP1Springboot.model.ModelRegisterRequest;
import com.example.EP1Springboot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service //กำหนดให้เป็น Business(Controller)
public class UserBusiness {
    private final UserService userservice;

    //เรียกใช้ mapper
    private final UserMapper userMapper;
    public UserBusiness(UserService userservice, UserMapper userMapper) {
        this.userservice = userservice;
        this.userMapper = userMapper;
    }
    //method ลงทะเบียน
    public MRegisterResponse register(ModelRegisterRequest request) throws BaseException { //throw exception ออกไปที object ที่เรียกใช้ (throws IOException)
        //🍎เรียกใช้ Service และส่ง parameter เข้าไปทำงาน
        User user = userservice.create(request.getEmail(), request.getPassword(), request.getName()); //user จะเก็บค่าของ user ในรูป json

        //ใช้ mapper
        return userMapper.toRegisterResponse(user);
        /*🍎 validation
        if (request == null) {
            throw UserException.requestNull(); //ถ้ามีค่า request เป็น null จะกำหนดให้เป็น Exception requestNull
        }
        //ถ้ามีค่า email เป็น null จะกำหนดให้เป็น Exception emailNull
        if (Objects.isNull(request.getEmail())) {
            throw UserException.emailNull(); //throw exception emailNull() ที่สร้างขึ้นมาเองออกไป
        }
        //ถ้ามีค่า password เป็น null จะกำหนดให้เป็น Exception passwordNull
        if (Objects.isNull(request.getPassword())) {
            throw UserException.passwordlNull();
        }
        return "";*/
    }

    //🍎 method สำหรับจัดการ upload file
    public String uploadProfilePicture(MultipartFile file) throws BaseException{
        //🦷validate file
        if(file==null){ //เช็คว่ามีไฟล์ไหม
            //ถ้่าไม่มีจะให้ throw error ออกไป
            throw FileException.fileNull();
        }

        //🦷validate file size
        if (file.getSize() > 1048576*2 ){ //เช็คว่าไฟล์เกิน 2MB ไหม?
            //ถ้าเกินจะให้ throw error
            throw FileException.maxSize();
        }

        String contentType = file.getContentType(); //เช็คว่าเป็น images ประเภท ไหม?
        //🦷กำหนดว่าจะให้ app support type ไหนบ้าง?
        List<String> suportedTypes = Arrays.asList("image/jpeg","image/png"); //กำหนดให้ support png และ jpeg
        if(suportedTypes.contains(contentType)){ //ถ้ารูปที่อัพโหลดมาไม่ตรงกับ type ที่ support
            //ถ้าไม่ตรง (unsupported) จะให้ throw error ออกไป
            throw FileException.fileTypeUnsupported();
        }
        //TODO : Upload file to file storage ex.AWSs3,etc...)
        /*try {
            byte [] bytes = file.getBytes();
        }catch (IIOException e){
            e.printStackTrace();
        }*/
        return "";
    }

    //method ล็อกอิน
    public String login(MLogInRequest request) throws UserException{
        //🦷validate request

        //🦷verify database
        Optional<User> opt = userservice.findByEmail(request.getEmail()); //รับ email จาก request และไปค้นใน database
        if(opt.isEmpty()){
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get(); //ดึงข้อมูลของ email นี้จาก db
        //🦷เช็คว่า password ถูกต้องไหม?
        if(!userservice.matchPassword(request.getPassword(),user.getPassword())){ //เช็คว่าถ้า password ไม่ตรงกันล่ะ?
            throw UserException.loginFailPasswodIncorrect();
        }
        //TODO:ถ้า login ผ่านแล้วให้ generated JWT ให้ user ไปถือไว้เพื่อยืนยันตัวนตนว่า login แล้วนะ
        String token = "JWT";
        return token;
    }
}
