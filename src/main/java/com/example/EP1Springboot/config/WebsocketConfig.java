package com.example.EP1Springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

//Config ค่าให้กับ websocket
@Configuration
@EnableWebSocketMessageBroker //เปิดใช้งาน websocket
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    //กำหนดว่าจะให้เชื่อมต่อ websocket ทาง path ไหน?
    @Override
    //กำหนด prefix หรือคำนำหน้าที่ใช้กับ URI ของ WebSocket ในแอพพลิเคชัน Spring WebSocket
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                //ใช้เชื่อมต่อระหว่าง frontend กับ backend (channel) เหมือนสายไฟ
                .enableSimpleBroker("/topic"); //กำหนด /topic เป็นคำนำหน้าที่ใช้กับ topic ในการสื่อสารในระบบ WebSocket
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket") //Endpoint คือ URI ที่เป็นจุดปลายทางสำหรับการสื่อสารผ่าน WebSocket ระหว่าง client กับ server
                .setAllowedOriginPatterns("http://*") //อนุญาตให้มีการเชื่อมต่อจาก origin ที่เป็น http://localhost กับ WebSocket endpoint นี้ (.setAllowedOrigins("http://localhost*")
                .withSockJS(); //จะเป็นการเปิดใช้งาน SockJS สำหรับการสื่อสารผ่าน WebSocket ซึ่ง SockJS เป็น library ที่ช่วยให้การสื่อสารผ่าน
                //WebSocket นั้นสามารถทำงานได้ผ่านหลาย protocol ไม่ว่าจะเป็น WebSocket, HTTP streaming, หรือ HTTP long-polling เพื่อให้การเชื่อมต่อและการสื่อสารผ่าน
                //WebSocket นั้นมีความยืดหยุ่นและสามารถทำงานได้หลากหลายเครื่องมือและแพลตฟอร์มต่าง ๆ อีกด้วย
    }



}
