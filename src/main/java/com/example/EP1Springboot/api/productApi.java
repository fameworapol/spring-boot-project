package com.example.EP1Springboot.api;

import com.example.EP1Springboot.business.ProductBusiness;
import com.example.EP1Springboot.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class productApi {
    //Inject Business
    private final ProductBusiness business;
    public productApi(ProductBusiness business) {
        this.business = business;
    }

    //🤢API ดึงข้อมูลสินค้าจาก ID
    //เป็นการรับ parameter จากที่มาพร้อมกับ path => path นั้นส่งค่ามาด้วย
    @GetMapping("/{id}") //ค่าที่ส่งมาอยู่ที่ /product/ค่าที่ส่งมา
    public ResponseEntity<String> getProductById(@PathVariable("id") String id) throws BaseException { //ค่าที่ส่งมาจะเก็บลงใน id
        String response = business.getProductById(id); //ส่ง parameter ที่รับมาไปทำงานที่ method getProductById ใน TestApiBusiness
        return ResponseEntity.ok(response); //return ค่าใน response พร้อม http status
    }

    @GetMapping("/testApi")
    public String testApi() {
        return "fame";
    }
}
