package com.example.EP1Springboot;

import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //กำหนดให้มีการ test แบบ chain (เป็นลำดับ)
class TestUserService {
	//Test userService ว่าทำงานได้ถูกต้องไหม?
	@Autowired
	private UserService userService; //Inject userService
	@Order(1) //กำหนดให้ test เป็นอันดับ 1
	@Test
	void testCreate() throws BaseException{
		User user = userService.create(
				testCreateData.email,
				testCreateData.password,
				testCreateData.name
		);
		//Check not Null >> ถ้า create ได้จะได้ user มาก้อนนึงและต้องไม่เป็น Null
		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());

		//Check equals >> เช็คว่าข้อมูลที่ส่งไป save ลง db ตามนั้นจริงรึเปล่า
		Assertions.assertEquals(testCreateData.name,user.getName());
		Assertions.assertEquals(testCreateData.email,user.getEmail());
		boolean isMatched = userService.matchPassword(testCreateData.password,user.getPassword());
		Assertions.assertTrue(isMatched); //ค่าที่ได้จากการ check password เท่านั้นถึงจะเป็น true
	}
	@Order(2) //กำหนดให้ test เป็นอันดับ 2
	@Test
	void testUpdate() throws BaseException{
		Optional<User> opt = userService.findByEmail(testCreateData.email);

		Assertions.assertTrue(opt.isPresent()); //isPresent คือเช็คว่ามีค่าอยู่
		User user = opt.get(); //get user object

		User updateUser = userService.updateName(user.getId(),testUpdateData.name);
		Assertions.assertNotNull(updateUser); //check not null
		Assertions.assertEquals(testUpdateData.name,updateUser.getName()); //เช็คว่าข้อมูลที่ส่งไป save ลง db ตามนั้นจริงรึเปล่า

	}
	@Order(3) //กำหนดให้ test เป็นอันดับ 3
	@Test
	void testDelete(){
		Optional<User> opt = userService.findByEmail(testCreateData.email); //ค้นหาข้อมูลจาก email
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();
		userService.deleteById(user.getId()); //ส่ง id ไปที่ deleteById ที่ userService

		//ถ้าลบได้แล้วไม่ควรจะมี email นี้อยู่ > ลองหาซ้ำดู
		Optional<User> optRecheck = userService.findByEmail(testCreateData.email); //ไม่ควรเจอ
		Assertions.assertTrue(optRecheck.isEmpty()); //optRecheck ควรจะเป็นค่าว่าง (True)
	}
	//ข้อมูลที่จะนำไป test
	interface testCreateData{
		String email = "worapol3579@gmail.com";
		String password = "Fg0884210335";
		String name = "fame worapol";
	}
	interface testUpdateData{
		String name = "bazooka";
	}
	//เสร็จแล้วกดรันเพื่อเช็คได้เลย
}
