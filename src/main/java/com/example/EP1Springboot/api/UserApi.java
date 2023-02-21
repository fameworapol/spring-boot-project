package com.example.EP1Springboot.api;

import com.example.EP1Springboot.business.UserBusiness;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.model.MLogInRequest;
import com.example.EP1Springboot.model.MRegisterResponse;
import com.example.EP1Springboot.model.ModelRegisterRequest;
import com.example.EP1Springboot.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


//🥵สร้าง API (REST) ขึ้นมาเพิ่มจากที่มีอยู่
@RestController
//🥵กำหนด Path ให้ API
@RequestMapping("/user") //ถ้าใส่ที่หัว class คือทุก API ในคลาสนี้จะขึ้นต้นด้วย /user
public class UserApi {

    private final UserBusiness business;

    //Constructor
    public UserApi(UserBusiness business) {
        this.business = business;
    }

    //🍎GET
    //😭ไม่มีการกำหนด RequestMapping แสดงว่าอยู่ที่ path /test เลย
    @GetMapping //😭กำหนดให้เป็น Http Get (response ค่ากลับไปให้ client)
    //😭กำหนดให้ return TestResponse ออกไป
    public TestResponse test() {
        TestResponse response = new TestResponse(); //😭สร้าง object response จาก TestResponse
        //😭เรียกใช้ getter,setter method ได้เลย เพื่อกำหนดค่ให้ attribute name , age
        response.setName("fame");
        response.setAge(23);
        response.setStudentid("6509035025");
        return response; //😭Return object response ออกไปเลย (จะ return เป็น json ออกไป)
        //😭เมื่อไปที่ http://localhost:8080/test จะได้ {"name":"fame","age":23}
    }

    //🍎POST
    //🤕 API สำหรับลงทะเบียน (Register)
    @PostMapping //🤕เป็น Http post => รับค่าจาก client //กำหนดให้รับ request ตาม attribute ในคลาส ModelRegisterRequest
    @RequestMapping("/register") //🤕กำหนด RequestMapping /register แสดงว่า API นี้อยู่ที่ path /test/register
    public ResponseEntity<MRegisterResponse> register(@RequestBody ModelRegisterRequest request) throws BaseException { //เราไม่ try.catch ที่นี่แต่ปล่อยมันต่อไปชั้นที่สูงกว่า
        //🤕ค่าที่รับมาจะเก็บอยู่ใน request
        /* try{
            response = business.register(request); //ส่ง request ที่ได้รับไปทำงานที่ method register ของ TestApiBusiness
            return ResponseEntity.ok(response); //ถ้าทำงานได้ไม่มี Exception ก็ response status 200
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build(); //ถ้า่มี Exception ก็ response status 417
        }*/
        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response); //response ข้อมูลของ user กลับไปหาผู้ใช้
    }

    //API สำหรับอัพโหลดรูป
    @PostMapping
    @RequestMapping("/upload")
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException{ //กำหนดให้รับ parameter เป็น file
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

    //API สำหรับ login
    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody MLogInRequest request) throws UserException {
        String response = business.login(request);
        return ResponseEntity.ok(response);
    }
}

