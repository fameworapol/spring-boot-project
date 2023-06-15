package com.example.EP1Springboot.util;

import com.example.EP1Springboot.entity.User;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
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

    public static String generateToken(){
        List<CharacterRule> rules = Arrays.asList(
                //รวมกันเป็น 30 ตัวพอดี (10+10+5+5)
                new CharacterRule(EnglishCharacterData.UpperCase, 10),
                new CharacterRule(EnglishCharacterData.LowerCase, 10),
                new CharacterRule(EnglishCharacterData.Digit, 5)
                );
        PasswordGenerator generator = new PasswordGenerator();

// Generated password is 12 characters long, which complies with policy
        String password = generator.generatePassword(25, rules); //มัดทุกตัวรวมกันกลายเป็น password
        return password;
    }
}
