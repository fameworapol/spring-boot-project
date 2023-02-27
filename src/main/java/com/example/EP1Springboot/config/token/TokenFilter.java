package com.example.EP1Springboot.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.EP1Springboot.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//ใช้ในการ filter ว่าในการส่ง request มาแต่ละครั้งต้องมีการส่ง Token มาด้วย
public class TokenFilter extends GenericFilterBean {
    private final TokenService tokenService;
    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //ในทุกๆ request ที่มาหา backend มีการแนบ token มาด้วยไหม?
        HttpServletRequest request = (HttpServletRequest) servletRequest; //แปลง request แบบ servlet to http

        //เมื่อเรา login แล้วได้ token มา >> แนบ token มากับ header (ชื่อว่า authorization) ด้วย
        String authorization = request.getHeader("Authorization"); //ดึงค่าจาก header authorization

        //เช็คว่าเป็นค่าว่างไหม?
        if (ObjectUtils.isEmpty(authorization)){
            filterChain.doFilter(servletRequest,servletResponse); //ปล่อยผ่าน
            return;
        }

        //ถ้ามี header ประเภท barrier หรือไม่?
        if (!authorization.startsWith("Bearer")){
            filterChain.doFilter(servletRequest,servletResponse); //ปล่อยผ่าน
            return;
        }

        //ดึงค่า token ออกมาใช้งาน (จาก header authorization)
        String token = authorization.substring(7);

        //verify token ว่าถูกต้องไหม? ถ้าไม่ถูกจะได้ค่า null
        DecodedJWT decodded = tokenService.verify(token);
        if (decodded == null){
            filterChain.doFilter(servletRequest,servletResponse); //ปล่อยผ่าน
            return;
        }

        String principal = decodded.getClaim("principal").asString(); //ดึงค่าใน field principal จาก Token
        String role = decodded.getClaim("role").asString(); //ดึงค่าใน field role จาก Token

        //list สำหรับเก็บ role
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));


        //สร้าง object เพื่อจะกำหนดว่า user เป็นใคร? ex. admin , general user
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,"protected",authorities);
        //นำ object กำหนด context ของ user (บริบท) >> บอกว่า user login เข้ามาแล้ว
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
