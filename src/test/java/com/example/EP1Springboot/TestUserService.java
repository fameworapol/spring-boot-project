package com.example.EP1Springboot;

import com.example.EP1Springboot.entity.Address;
import com.example.EP1Springboot.entity.Social;
import com.example.EP1Springboot.entity.User;
import com.example.EP1Springboot.exception.BaseException;
import com.example.EP1Springboot.exception.UserException;
import com.example.EP1Springboot.service.AddressService;
import com.example.EP1Springboot.service.SocialService;
import com.example.EP1Springboot.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //กำหนดให้มีการ test แบบ chain (เป็นลำดับ)
class TestUserService {
	//Test userService ว่าทำงานได้ถูกต้องไหม?
	@Autowired
	private UserService userService; //Inject userService
	@Autowired
	private SocialService socialService; //Inject socialService
	@Autowired
	private AddressService addressService; //Inject socialService
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
	@Order(3)
	@Test
	void testCreateSocial() throws UserException {
		Optional<User> opt = userService.findByEmail(testCreateData.email); //หา user จาก email
		Assertions.assertTrue(opt.isPresent()); //isPresent คือเช็คว่ามีค่าอยู่
		User user = opt.get(); //get user object

		Social social = user.getSocial(); //ค้นหา social ของ user นั้น (ควรจะเป็นค่า Null)
		Assertions.assertNull(social);

		//ส่งค่าไปทำงานที่ CreateSocial ใน userService
		social = socialService.create(user,
				SocialTestCreateData.facebook,
				SocialTestCreateData.line,
				SocialTestCreateData.instragram,
				SocialTestCreateData.tiktok
		);
		Assertions.assertNotNull(social); //เมื่อ create social แล้วควรจะไม่เป็นค่า Null
		Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());//เช็คว่าเช็ตค่าถูกไหม
	}

	@Order(4)
	@Test
	void testCreateAddress() throws UserException {
		Optional<User> opt = userService.findByEmail(testCreateData.email); //หา user จาก email
		Assertions.assertTrue(opt.isPresent()); //isPresent คือเช็คว่ามีค่าอยู่
		User user = opt.get(); //get user object

		List<Address> addresses = user.getAddresses(); //ค้นหา address ของ user นั้น (ควรจะเป็นค่า Empty)
		Assertions.assertTrue(addresses.isEmpty());

		//สร้าง Address
		createAddress(user,AddressTestData.line1,AddressTestData.line2,AddressTestData.zipcode); //สร้าง Address ที่ 1
		createAddress(user,AddressTestData2.line1,AddressTestData2.line2,AddressTestData2.zipcode); //สร้าง Address ที่ 2

	}
	private void createAddress(User user,String line1,String line2,String zipcode){
		//สร้าง Address
		Address address = addressService.create(
				user,
				line1,
				line2,
				zipcode
		);
		Assertions.assertNotNull(address); //เมื่อ create address แล้วควรจะไม่เป็นค่า Null
		Assertions.assertEquals(line1,address.getLine1());//เช็คว่า line1 เช็ตค่าถูกไหม
		Assertions.assertEquals(line2,address.getLine2());//เช็คว่า line2 เช็ตค่าถูกไหม
		Assertions.assertEquals(zipcode,address.getZipcode()); //เช็คว่า zipcode เช็ตค่าถูกไหม
	}

	@Order(5) //กำหนดให้ test เป็นอันดับ 3
	@Test
	void testDelete(){
		Optional<User> opt = userService.findByEmail(testCreateData.email); //ค้นหาข้อมูลจาก email
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		//check social
		Social social = user.getSocial();
		Assertions.assertNotNull(social);
		Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());
		//check address
		List<Address> addresses = user.getAddresses();
		Assertions.assertFalse(addresses.isEmpty());
		Assertions.assertEquals(2,addresses.size()); //เช็คขนาดของ List ว่าเท่ากันไหม? (Address มี 2 record ไหม)


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
	interface SocialTestCreateData{ //ข้อมูลที่จะนำไป testSocial
		String facebook = "Worrapon Thonfmook";
		String line = "famefem";
		String instragram = "fameworapol";
		String tiktok = "RonHarry";
	}
	interface AddressTestData{ //ข้อมูลที่จะนำไป testAddress
		String line1 = "Golfview TU Maple1 Building Room M1214";
		String line2 = "Klongnueng Klongluang Pathumthani";
		String zipcode = "12120";
	}
	interface AddressTestData2{ //ข้อมูลที่จะนำไป testAddress
		String line1 = "TU mansion Room 623";
		String line2 = "Klongnueng Klongluang Pathumthani";
		String zipcode = "67120";
	}
	interface testUpdateData{
		String name = "bazooka";
	}
	//เสร็จแล้วกดรันเพื่อเช็คได้เลย
}
