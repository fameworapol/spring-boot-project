package com.example.EP1Springboot.schedule;

import com.example.EP1Springboot.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2 //dependencies สำหรับการ print ข้อมูล (wtf หาตั้งนาน)
public class UserSchedule {
    //Inject userService
    private final UserService userservice;
    public UserSchedule(UserService userservice) {
        this.userservice = userservice;
    }

    //▼ @Scheduled(cron = "second minute hour day month year")
    @Scheduled(cron = "0 0 * * * *") //กำหนดให้ method นี้ทำงานทุกๆ 1 นาที
    public void testEveryMinute() {
        log.info("Hello Spring");
    }

    @Scheduled(cron = "0 16 7 * * *") //กำหนดให้ method ทุกวันตอน 7.16
    public void TestTime(){
        log.info("Test");
    }

    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Bangkok") //กำหนดให้ method ทุกวันตอนเที่ยงคืน / กำหนด timezone ของ thai
    public void TestEveryMidnight(){
        log.info("0.00");
    }
}
