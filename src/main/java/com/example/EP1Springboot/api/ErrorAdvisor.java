package com.example.EP1Springboot.api;

import com.example.EP1Springboot.exception.BaseException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorAdvisor {
    //🍎จัดการ Error => คือถ้าเกิด Error ขึ้นจะให้ทำอะไร
    @ExceptionHandler(BaseException.class) //จะเช็คว่ามี Error ที่ชื่อว่า BaseException รึเปล่า
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e){
        //ถ้ามี BaseException
        ErrorResponse response = new ErrorResponse(); //
        response.setError(e.getMessage()); //attribute error เราจะใช้ message ที่อยู่ใน error (error code)
        response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED); //response ค่าตามฟิลด์ใน ErrorResponse ไปยัง client
    }

    //class สำหรับว่าจะให้ return Json ฟิลด์อะไรบ้าง? เมื่อเกิด Error
    @Data
    public static class ErrorResponse{
        private LocalDateTime timeStamp = LocalDateTime.now();
        private int status;
        private String error;
    }
    /*
    🍎error ที่ฝั่ง client
    {
    "timeStamp": "2023-02-04T06:04:05.942019",
    "status": 417,
    "error": "user.register.email.null"
    }
    */
}
