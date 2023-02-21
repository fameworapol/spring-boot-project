package com.example.EP1Springboot.exception;

public abstract class BaseException extends Exception{ //สืบทอดคุณสมบัติมาจาก class Exception
    //🍎คลาสสำหรับเอาไว้สร้าง Exception (เอง)
    //Constructor
    public BaseException(String code){
        super(code); //เรียกใช้ constructor ของคลาสแม่ (message)
    }
}
