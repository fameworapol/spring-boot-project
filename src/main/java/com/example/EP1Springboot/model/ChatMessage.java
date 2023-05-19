package com.example.EP1Springboot.model;

import lombok.Data;

import java.util.Date;

//กำหนดว่าจะส่งข้อความอะไรกลับไปบ้าง
@Data
public class ChatMessage {
    private String from; //message จากใคร
    private String message; //message อะไร
    private Date created; //ส่งเมื่อไหร่
    public ChatMessage(){
        created = new Date();
        //เรียกใช้เมื่อสร้าง object
    }
}
