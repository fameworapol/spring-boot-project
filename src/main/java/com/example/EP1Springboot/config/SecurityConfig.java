package com.example.EP1Springboot.config;

import org.hibernate.StatelessSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //ถ้าใช้ dependency security แล้วจะไม่สามารถเข้าถึง API ได้ถ้าไม่ล็อกอิน
    //แต่เราจะกำหนดว่าให้มี API ไหนที่เข้าถึงได้โดยไม่ต้องล็อกอินได้บ้าง? ได้แก่ login,register นอกนั้นต้องให้ login ก่อน
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //ใช้เข้ารหัส password
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //backend จะเป็น stateless
                .and().authorizeHttpRequests().requestMatchers("/user/register","/user/login").anonymous()//ถ้า request มาจากการลงทะเบียน และล็อกอินจะเช้าได้เลย
                .anyRequest().authenticated(); //ถ้าเป็น API อื่นๆต้อง login ก่อน
        return http.build();
    }
}
