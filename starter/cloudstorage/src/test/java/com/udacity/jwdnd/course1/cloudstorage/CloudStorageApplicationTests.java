package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}

	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	private void getPage(String url){
		driver.get("http://localhost:" + this.port + url);
	}

	// 1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
	// Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	public void unauthCanAccessLoginAndSignUp(){
		getPage("/signin");
		Assertions.assertEquals("Login", driver.getTitle());
		getPage("/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void unauthCannotAccessHome(){
		getPage("/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}
	// Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	@Test
	public void CanSignupLogout(){
		signupAndLogin("X");
		getPage("/home");
		Assertions.assertEquals("Home", driver.getTitle());
		getPage("/logout");
		getPage("/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	private void signUp(String username){
		getPage("/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys(username + Keys.TAB +username + Keys.TAB +username + Keys.TAB + username);
		driver.findElement(By.cssSelector("body > div > form > button")).click();
	}
	private void login(String username){
		getPage("/login");
		driver.findElement(By.id("inputUsername")).sendKeys(username + Keys.TAB + username);
		driver.findElement(By.cssSelector("body > div > form > button")).click();
	}
	private void signupAndLogin(String username){
		signUp(username);
		login(username);
	}

	// 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
	// Write a test that creates a note, and verifies it is displayed.
	// Write a test that edits an existing note and verifies that the changes are displayed.
	// Write a test that deletes a note and verifies that the note is no longer displayed.

	// 3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
	// Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	// Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	// Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.

	@Test
	public void devAuto() {
		driver.get("http://localhost:8080/login");
		driver.findElement(By.id("inputUsername")).sendKeys("a" + Keys.TAB);
		driver.findElement(By.id("inputPassword")).sendKeys("a" + Keys.ENTER);
		driver.findElement(By.id("nav-notes-tab")).click();
	}


	public void keepTestRunning(){
		// keep the test running
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
