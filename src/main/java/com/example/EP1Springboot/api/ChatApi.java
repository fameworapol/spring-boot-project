package com.example.EP1Springboot.api;

import com.example.EP1Springboot.business.ChatBusiness;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.model.ChatMessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//API สำหรับรับส่ง chat
@RestController
@RequestMapping("/chat")
public class ChatApi {
    //Inject chatBusiness
    private final ChatBusiness business;
    public ChatApi(ChatBusiness chatBusiness, ChatBusiness business) {
        this.business = business;
    }

    //chat กลุ่มที่มีหลาย user
    @PostMapping("/message")
    public ResponseEntity<Void> post(@RequestBody ChatMessageRequest request) throws BaseException {
        business.post(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
