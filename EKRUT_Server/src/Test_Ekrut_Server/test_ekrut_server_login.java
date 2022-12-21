package Test_Ekrut_Server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Command;
import server.EkrutServer;
import server.mysqlConnection;

class test_ekrut_server_login {
	public mysqlConnection mysql;
	public EkrutServer Eserver;
	//public ArrayList<ArrayList<String>> userTable;
	public ArrayList<String> loginValidAccount;
	public ArrayList<String> loginNotValidAccount;
	public ArrayList<String> loginValidAccountWrongPassword;
	public ArrayList<String> loginHalfInput;
	public ArrayList<String> loginNoInput;

	@BeforeEach
	void setUp() throws Exception {
		loginValidAccount = new ArrayList<>(Arrays.asList("Ceo","123456"));
		loginHalfInput = new ArrayList<>(Arrays.asList("Customer1"));
		loginNoInput = new ArrayList<>();
		mysqlConnection.ConnectDb("jdbc:mysql://localhost/ekrut?serverTimezone=IST","root","bbznot123");
		
	}

	
	@Test
	void TestLoginWithValidAccount() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		Command cmdExpected = new Command("user_check",true);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginWithNonValidAccount() {
		loginNotValidAccount = new ArrayList<>(Arrays.asList("Beo","123456"));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNotValidAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginWithValidAccountWrongPassword() {
		loginValidAccountWrongPassword = new ArrayList<>(Arrays.asList("Customer69","123456"));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccountWrongPassword);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginWithNullAccount() {
		loginValidAccount = new ArrayList<>(Arrays.asList(null,null));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginWithNullObject() {
		loginValidAccount = null;
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginWithHalfInput() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginHalfInput);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	

	@Test
	void TestLoginWithNoInput() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNoInput);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	@Test
	void TestLoginUserAlreadyLoggedIn() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}

}
