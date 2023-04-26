package com.example.EP1Springboot;

import com.example.EP1Springboot.business.EmailBusiness;
import com.example.EP1Springboot.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmailBusiness {
    //Inject email business
    @Autowired
    private EmailBusiness emailBusiness;

    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserEmail(
                TestData.email,TestData.name,TestData.token
        );
    }
    interface TestData{
        String email = "worapol3579@gmail.com";
        String name = "worrapon thongmook";
        String token = "www.fdafjafjajfamfmalfa.com";
    }
}
