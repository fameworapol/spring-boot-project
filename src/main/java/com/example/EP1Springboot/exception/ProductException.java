package com.example.EP1Springboot.exception;

//🥶Exception สำหรับ Product
public class ProductException extends BaseException{
    public ProductException(String code){
        super("product."+code);
    }
    //Exception notFound() => หา product จาก id ไม่เจอ
    public static ProductException notFound(){
        return new ProductException("not.found");
    }
}
