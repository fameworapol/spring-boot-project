package com.example.EP1Springboot.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.EP1Springboot.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    //นำค่ามาใช้จาก application.yaml
    @Value(value = "${app.token.secret})")
    private String secret; //นำค่าใน secret เก็บลงใน secret
    @Value("({app.token.issuer})")
    private String issuer;

    //🍎 method สำหรับสร้าง token
    public String tokenize(User user) {

        Calendar calendar = Calendar.getInstance(); //เวลาปัจจุบัน
        calendar.add(Calendar.MINUTE,60); //เวลาปัจจุบัน +60 นาที
        Date expireAt = calendar.getTime();

        //สร้าง Token จาก Algorithms ที่เลือก
        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim("principal",user.getId()) //withClaim ข้อมูลที่ฝังไปกับ Token
                .withClaim("role","USER")
                .withExpiresAt(expireAt) //กำหนด role ให้กับ user แต่ละคน
                .sign(algorithms())
                ;

        /*{
          "principal": "ff8080818680950f0186809613370000",
          "role": "USER",
          "iss": "({app.token.issuer})"
        }
        ข้อมูล payload ที่เก็บใน token
        */
        return token;
    }
    //method เช็คว่า token นั้นถูกไหม?
    public DecodedJWT verify(String token ){
        try {
            JWTVerifier verifier = JWT.require(algorithms())
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token); //ถ้า verify ผ่านก็จะ return true
        }catch (Exception e){
            return null;
        }

    }

    private Algorithm algorithms(){
        //เลือก Algorithms ที่จะใช้
        return Algorithm.HMAC256(secret);
    }
}
