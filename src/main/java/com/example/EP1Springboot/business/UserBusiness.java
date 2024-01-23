package com.example.EP1Springboot.business;

import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.FileException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.mapper.UserMapper;
import com.example.EP1Springboot.model.MLogInRequest;
import com.example.EP1Springboot.model.MRegisterResponse;
import com.example.EP1Springboot.model.ModelRegisterRequest;
import com.example.EP1Springboot.service.TokenService;
import com.example.EP1Springboot.service.UserService;
import com.example.EP1Springboot.util.SecurityUtil;
//import com.example.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service //กำหนดให้เป็น Service
@Log4j2
public class UserBusiness {
    private final UserService userservice;
    private final EmailBusiness emailBusiness;
    //Inject JWT
    private final TokenService tokenService;

    //เรียกใช้ mapper
    private final UserMapper userMapper;



    public UserBusiness(UserService userservice, EmailBusiness emailBusiness, TokenService tokenService, UserMapper userMapper) {
        this.userservice = userservice;
        this.emailBusiness = emailBusiness;
        this.tokenService = tokenService;
        this.userMapper = userMapper;

    }
    //method ลงทะเบียน
    public MRegisterResponse register(ModelRegisterRequest request) throws BaseException { //throw exception ออกไปที object ที่เรียกใช้ (throws IOException)
        String token = SecurityUtil.generateToken();
        //🍎เรียกใช้ Service และส่ง parameter เข้าไปทำงาน
        User user = userservice.create(request.getEmail(), request.getPassword(), request.getName(),token); //user จะเก็บค่าของ user ในรูป json

        //ทุกครั้งที่มีการเข้าสู่ระบบจะให้เรียกใช้ kafka เพื่อส่งข้อมูลไปหา email
        sendEmail(user);

        //ใช้ mapper
        return userMapper.toRegisterResponse(user);

    }

    public void sendEmail(User user) throws BaseException {
        String token = user.getToken();
//        emailBusiness.sendActivateUserEmail(user.getEmail(),user.getName(),token);
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
        String token = tokenService.tokenize(user); //สร้าง Token
        return token; //return Token ไปยัง API
    }

    public String refreshToken() throws BaseException{
        //ดึงค่า id(principal) จาก token ที่มีอยู่
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()){
            throw UserException.notFound();
        }
        String userId = opt.get();
        //ค้นหา user จาก id
        Optional<User> optUser = userservice.findById(userId);
        if (optUser.isEmpty()){ //ถ้าไม่เจอ throw UserException
            throw UserException.notFound();
        }
        //ถ้าเจอ user
        User user = optUser.get();
        //สร้าง token ให้ user ใหม่
        return tokenService.tokenize(user);
    }
}
