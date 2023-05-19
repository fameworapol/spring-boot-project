package com.example.EP1Springboot.business;

import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.ChatException;
import com.example.EP1Springboot.model.ChatMessage;
import com.example.EP1Springboot.model.ChatMessageRequest;
import com.example.EP1Springboot.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChatBusiness {
    private final SimpMessagingTemplate template; //class สำหรับส่งข้อความ
    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    //Method สำหรับ post ข้อความ
    public void post(ChatMessageRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        //Validate กำหนดให้ต้อง login ก่อนถึงจะส่งข้อความได้
        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }
        final String destination = "/topic/chat"; //กำหนดให้คุยกันผ่าน channel chat
        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());
        template.convertAndSend(destination,payload); //ส่งข้อความกลับไปให้ frontend (destination) โดยมีโครงสร้างตามคลาส ChatMessage
    }
}
