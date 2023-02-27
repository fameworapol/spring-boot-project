package com.example.EP1Springboot.business;

import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.ProductException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductBusiness {
    public String getProductById(String id) throws BaseException {
        //ไปดึงชื่อสินค้าจาก database ทีี่มีค่า id กับที่รับมาและ return กลับไป
        if(Objects.equals("1234",id)){
            throw ProductException.notFound();
        }
        return id;
    }
}
