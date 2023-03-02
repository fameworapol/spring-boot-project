package com.example.EP1Springboot.util;

import com.example.EP1Springboot.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil(){

    }
    public static Optional<String> getCurrentUserId (){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null){ //ถ้าไม่มี context
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null){ //ถ้าไม่มีการ authentication
            return Optional.empty();
        }
        //get principal
        Object principal = authentication.getPrincipal();
        if (principal == null){
            return Optional.empty();
        }
        String userId = (String) principal; //แปลง principal to string
        return Optional.of(userId);
    }
}
