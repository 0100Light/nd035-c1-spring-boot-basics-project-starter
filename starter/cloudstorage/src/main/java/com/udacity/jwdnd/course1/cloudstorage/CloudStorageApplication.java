package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CloudStorageApplication implements CommandLineRunner {

	private UserMapper userMapper;

	public CloudStorageApplication(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		System.out.println("run method");
////		userMapper.addUser(new User("run", "run", "run", "run"));
//		List<User> users = userMapper.findAllUsers();
//
//		for (User u: users) {
//			String fn = u.getFirstname();
//			System.out.println("--------");
//			System.out.println("FN" + fn);
//			System.out.println("--------");
//		}
//		System.out.println("user created");
	}
}
