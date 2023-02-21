package com.example.EP1Springboot.exception;

public class UserException extends BaseException{ //สืบทอดมาจาก BaseException
    public UserException(String code){
        super("user."+code);
    }
    //🍎user.request.null => error ที่เกิดจาก request เป็นค่า null
    public static UserException requestNull(){ //สร้าง Exception เองที่ชื่อว่า requestNull
        return new UserException("register.request.null");
    }
    //🍎user.register.email.null => error ที่เกิดจาก email เป็นค่า null
    public static UserException emailNull(){ //สร้าง Exception เองที่ชื่อว่า EmailNull
        return new UserException("register.email.null");
    }
    public static UserException passwordlNull(){ //สร้าง Exception เองที่ชื่อว่า PasswordNull
        return new UserException("register.password.null");
    }

    //🍎Exception สำหรับ Create user
    public  static  UserException createEmailNull(){return new UserException("create.email.null");}
    public  static  UserException createPasswordNull(){return new UserException("create.password.null");}
    public  static  UserException createNameNull(){return new UserException("create.name.null");}
    public  static UserException emailAlreadyExist(){return new UserException("create.email.already.exist");}

    //🍎Exception สำหรับ login
    public static UserException loginFailEmailNotFound(){return  new UserException("log.fail");}
    public static UserException loginFailPasswodIncorrect(){return  new UserException("log.fail");}
    //จริงๆไม่ควรระบุว่า email or password ที่ผิด

    public static UserException notFound(){return  new UserException("user.not.found");}
}
