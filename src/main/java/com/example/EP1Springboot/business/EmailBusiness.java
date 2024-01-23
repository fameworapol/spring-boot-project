package com.example.EP1Springboot.business;

import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.EmailException;
//import com.example.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class EmailBusiness {
    //Inject EmailService
//    private final KafkaTemplate<String,EmailRequest> kafkaEmailTemplate;
//    public EmailBusiness( KafkaTemplate<String, EmailRequest> kafkaEmailTemplate) {
//        this.kafkaEmailTemplate = kafkaEmailTemplate;
//    }

    //method สำหรับให้ activate email
//    public void sendActivateUserEmail(String email, String name , String Token) throws BaseException {
//        //prepare content (html)
//        String html = null;
//        try {
//            //อ่านไฟล์ html ที่มีชื่อว่า email-activate-user.html
//            html = readEmailTemplate("email-activate-user.html");
//        } catch (IOException e) {
//            throw EmailException.templateNotFound();
//        }
//        String finalLink = "http://localhost:8080/activate/"+Token;
//        //แทนที่ตัวแปรใน html
//        html = html.replace("${P_Name}",name);
//        html = html.replace("${LINK}",finalLink); //ให้เขาไป activate ที่ http://localhost:8080/activate/token
//        //prepare subject
//        String subject = "Please activate your account.";
//        EmailRequest emailRequest = new EmailRequest();
//
//        emailRequest.setTo(email);
//        emailRequest.setContent(html);
//        emailRequest.setSubject("Please activate your account.");
//
//        CompletableFuture<SendResult<String, EmailRequest>> completableFuture = kafkaEmailTemplate.send("email-activate.", emailRequest);
//
//    }

    //method สำหรับอ่านไฟล์ html มาใช้
    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/"+filename); //get file
        return FileCopyUtils.copyToString(new FileReader(file)); //อ่านไฟล์ html เป็น string
    }

}
