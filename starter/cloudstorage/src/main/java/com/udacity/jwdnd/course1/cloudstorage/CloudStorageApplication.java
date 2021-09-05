package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication implements CommandLineRunner {

//	private UserMapper userMapper;

//	public CloudStorageApplication(UserMapper userMapper) {
//		this.userMapper = userMapper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

//		devAutomation();
	}

	public void devAutomation(){
		WebDriverManager.chromedriver().setup();
		var driver = new ChromeDriver();
		driver.get("http://localhost:8080/login");
		driver.findElement(By.id("inputUsername")).sendKeys("o" + Keys.TAB);
		driver.findElement(By.id("inputPassword")).sendKeys("o" + Keys.ENTER);
//		driver.findElement(By.id("nav-notes-tab")).click();
	}
}
