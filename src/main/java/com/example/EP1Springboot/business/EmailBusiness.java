package com.example.EP1Springboot.business;

import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.EmailException;
import com.example.EP1Springboot.service.EmailService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {
    //Inject EmailService
    private final EmailService emailService;
    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }

    //method สำหรับให้ activate email
    public void sendActivateUserEmail(String email, String name , String Token) throws BaseException {
        //prepare content (html)
        String html = null;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }
        String finalLink = "http://localhost:8080/activate/"+Token;
        //แทนที่ตัวแปรใน html
        html = html.replace("${P_Name}",name);
        html = html.replace("${LINK}",finalLink); //ให้เขาไป activete ที่ http://localhost:8080/activate/token
        //prepare subject
        String subject = "Please activate your account.";
        emailService.send(email,subject,html);
    }

    //method สำหรับอ่านไฟล์ html มาใช้
    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/"+filename); //get file
        return FileCopyUtils.copyToString(new FileReader(file)); //อ่านไฟล์ html เป็น string
    }
}
