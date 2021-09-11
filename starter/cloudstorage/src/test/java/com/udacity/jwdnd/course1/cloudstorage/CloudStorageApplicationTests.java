package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

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
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		login(username);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
	// Write a test that creates a note, and verifies it is displayed.
	@Test
	public void canCreateNote() throws InterruptedException {
		signupAndLogin("X");
		driver.findElement(By.id("nav-notes-tab")).click();
		driver.findElement(By.cssSelector("#nav-notes > button")).click();
		driver.findElement(By.id("note-title")).sendKeys("ttttt" + Keys.TAB + "ddddd");
		driver.findElement(By.cssSelector("#noteModal > div > div > div.modal-footer > button.btn.btn-primary")).click();
		driver.findElement(By.id("nav-notes-tab")).click();

		Thread.sleep(500);

		Assertions.assertTrue(driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child > th")).getText().contains("ttttt"));
		Assertions.assertEquals("ddddd", driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child > td:last-child")).getText());
	}

	public void createNote(String title, String description){

		driver.findElement(By.id("nav-notes-tab")).click();
		driver.findElement(By.cssSelector("#nav-notes > button")).click();
		driver.findElement(By.id("note-title")).sendKeys(title + Keys.TAB + description);
		driver.findElement(By.cssSelector("#noteModal > div > div > div.modal-footer > button.btn.btn-primary")).click();
		driver.findElement(By.id("nav-notes-tab")).click();

	}
	// Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void canEditNote() throws InterruptedException {
		signupAndLogin("asdkfjh");
		createNote("nnnnn", "ddddd");


//		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(200);
		// edit button
		driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child > td:first-child > a.btn.btn-success")).click();

		Thread.sleep(200);
		driver.findElement(By.id("edit-note-title")).sendKeys("x" + Keys.TAB + "x");
		driver.findElement(By.id("btn-edit-note-submit")).click();

		Thread.sleep(200);
		// get updated note text
		String newTitle = driver.findElement(By.id("edit-note-title")).getText();
		Assertions.assertEquals("nnnnnx", newTitle);
		String newDescription = driver.findElement(By.id("edit-note-description")).getText();
		Assertions.assertEquals("xddddd", newDescription);
	}
	// Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void canDeteleNote(){
		signupAndLogin("x");
		// to make cssSelector workable
		createNote("xxx", "xxx");
		createNote("tt", "dd");

		driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child > td:first-child > a.btn.btn-danger")).click();
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertFalse(driver.findElement(By.cssSelector("#userTable > tbody > tr:last-child > th")).getText().contains("tt"));
	}

	// 3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
	private void signupAndLoginToCredPage(){

		signupAndLogin("xus");
		clickCredTab();
	}
	private void clickCredTab(){
		driver.findElement(By.id("nav-credentials-tab")).click();
	}
	// Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	@Test
	public void canCreateCred() throws InterruptedException {
		signupAndLoginToCredPage();
		createCred("placeholder");
		driver.findElement(By.id("btn-add-credential")).click();
		driver.findElement(By.id("credential-url")).sendKeys("uu" + Keys.TAB + "ss" + Keys.TAB + "pp");
		driver.findElement(By.id("btn-cred-submit")).click();
		clickCredTab();
		Thread.sleep(200);
		String getTitle = driver.findElement(By.cssSelector("#credentialTable > tbody > tr:last-child> th")).getText();
		Assertions.assertEquals("uu", getTitle);
	}

	private void createCred(String url){
		getPage("/home");
		clickCredTab();
		driver.findElement(By.id("btn-add-credential")).click();
		driver.findElement(By.id("credential-url")).sendKeys(url + Keys.TAB + "ss" + Keys.TAB + "pp");
		driver.findElement(By.id("btn-cred-submit")).click();
		clickCredTab();
	}
	// Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	@Test
	public void canDecryptAndUpdateCred() throws InterruptedException {
		signupAndLoginToCredPage();
		createCred("placeholder");
		driver.findElement(By.id("btn-add-credential")).click();
		driver.findElement(By.id("credential-url")).sendKeys("uu" + Keys.TAB + "ss" + Keys.TAB + "pp");
		driver.findElement(By.id("btn-cred-submit")).click();

		// click edit
		getPage("/home");
		clickCredTab();
		Thread.sleep(200);
		driver.findElement(By.cssSelector("#credentialTable > tbody > tr:last-child > td:nth-child(1) > a.btn.btn-success")).click();
		// check if decrypted
		String decPass = driver.findElement(By.id("edit-cred-password")).getAttribute("value");
		Assertions.assertEquals("pp", decPass);

		// check can update
		driver.findElement(By.id("edit-cred-password")).clear();
		driver.findElement(By.id("edit-cred-password")).sendKeys("newpass");
		String newPass = driver.findElement(By.id("edit-cred-password")).getAttribute("value");
		Assertions.assertEquals("newpass", newPass);
	}
	// Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	@Test
	public void canDeleteCred() throws InterruptedException {
		signupAndLoginToCredPage();
		createCred("cenDeleteCredTest");
		clickCredTab();
		createCred("2nd placeholder");
		driver.findElement(By.id("btn-add-credential")).click();
		driver.findElement(By.id("credential-url")).sendKeys("uu" + Keys.TAB + "ss" + Keys.TAB + "pp");
		driver.findElement(By.id("btn-cred-submit")).click();

		getPage("/home");
		clickCredTab();
		Thread.sleep(200);

		// click delete
		driver.findElement(By.cssSelector("#credentialTable > tbody > tr:last-child > td:nth-child(1) > a.btn.btn-danger")).click();

		// #credentialTable > tbody > tr:nth-child(5) > td:nth-child(1) > a.btn.btn-danger
		clickCredTab();
		String lastUrl = driver.findElement(By.cssSelector("#credentialTable > tbody > tr:last-child > th")).getText();
		Assertions.assertNotEquals("uu", lastUrl);

	}

	@Test
	public void canShowLogoutSuccess(){
		signupAndLogin("asdfhk");
		getPage("/logout");
		String formText = driver.findElement(By.cssSelector("body > div > form")).getText();
		Assertions.assertTrue(formText.contains("You have been logged out"));
	}

	@Test
	public void canShowLoginFailed(){
		login("ajdpsfoijapdfojiapiodf");
		String formText = driver.findElement(By.cssSelector("body > div > form")).getText();
		Assertions.assertTrue(formText.contains("Invalid username or password"));
	}

	@Test
	public void canShowSignupSuccess() throws InterruptedException {
		signUp("yyyy");

		Thread.sleep(500);
		String formText = driver.findElement(By.cssSelector("body > div > form")).getText();
		Assertions.assertTrue(formText.contains("successfully signed up"));
	}

	@Test
	public void canShowSignupError(){

		signUp("yyyy");
		signUp("yyyy");

		String formText = driver.findElement(By.cssSelector("body > div > form")).getText();
		Assertions.assertTrue(formText.contains("User already exists"));
		// User already exists
	}

	@Test
	public void canShowCustomErrorPage() throws InterruptedException {
		signupAndLogin("asdf");
		Thread.sleep(500);
		getPage("/asdoppasdo");

		Thread.sleep(500);
		String pageContent = driver.findElement(By.cssSelector("body")).getText();
		Assertions.assertTrue(pageContent.contains("something went wrong"));
	}

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
