package com.example.EP1Springboot.exception;

public class FileException extends BaseException{
    //สร้าง Exception สำหรับการ upload file
    public FileException(String code){super("file."+code);}
    public static FileException fileNull(){
        return new FileException("null");
    }
    public static FileException maxSize(){
        return new FileException("max.size");
    }
    public static FileException fileTypeUnsupported(){
        return new FileException("unsupported");
    }
}
