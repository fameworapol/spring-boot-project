package com.example.EP1Springboot.config;

import com.example.EP1Springboot.config.token.TokenFilterConfiguerer;
import com.example.EP1Springboot.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    //😡ถ้าใช้ dependency security แล้วจะไม่สามารถเข้าถึง API ได้ถ้าไม่ล็อกอิน
    //แต่เราจะกำหนดว่าให้มี API ไหนที่เข้าถึงได้โดยไม่ต้องล็อกอินได้บ้าง? ได้แก่ login,register นอกนั้นต้องให้ login ก่อน
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //ใช้เข้ารหัส password
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().disable().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //backend จะเป็น stateless
                .and().authorizeHttpRequests().requestMatchers(
                        "/user",
                        "/user/register",
                        "/user/login",
                        "/actuator/**",
                        "/socket/**",
                        "/chat/**").anonymous()//ถ้า request มาจากการลงทะเบียน และล็อกอินจะเช้าได้เลย
                .anyRequest().authenticated() //ถ้าเป็น API อื่นๆต้อง login ก่อน
                .and().apply(new TokenFilterConfiguerer(tokenService))
                .and().build();//กำหนดให้เรียกใช้ Token filter
    }
    //Config policy อนุญาตให้ frontend ยิงมาหา backend ได้
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("http://localhost:4200"); //กำหนดให้เชื่อมต่อได้จาก http://localhost:4200 (Angular)
        config.setAllowedOriginPatterns(Collections.singletonList("http://*"));
        config.addAllowedHeader("*"); //อนุญาตทุก header
        //Http method ที่สามารถรับในการ request
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**",config); //filter ทุก path ด้วย config นี้
        return new CorsFilter(source);
    }


}
