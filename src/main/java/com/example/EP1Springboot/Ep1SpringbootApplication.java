package com.example.EP1Springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //เรียกใช้ feature scheduling
public class Ep1SpringbootApplication {
	//🍎 ถ้าจะ run project จะรันที่ class นี้ , เพราะจะมีฟังก์ชัน main อยู่
	public static void main(String[] args) {
		SpringApplication.run(Ep1SpringbootApplication.class, args);
	}
}
