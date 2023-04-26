package com.example.EP1Springboot.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    //ดึงค่าจาก application.yaml เก็บลงใน from
    @Value("${app.email.from}")
    private String from;
    //Inject
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Method สำหรับส่ง email
    public void send(String to,String subject,String html){
        //MimeMessage > มีความสามารถในการกำหนดและเชื่อมต่อระหว่างส่วนต่างๆของอีเมล์ เช่น ผู้ส่ง (sender), ผู้รับ (recipient), หัวข้อ (subject), เนื้อหา (content) และไฟล์แนบ (attachments)
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //set detail for send email
            helper.setFrom(from); //ผู้ส่ง
            helper.setTo(to); //ผู้รับ
            helper.setSubject(subject); //หัวข้อ
            helper.setText(html,true); //ข้อความ
        };
        mailSender.send(message);
    }
}
