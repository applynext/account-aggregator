package com.dragon.mvnbook.account.persist;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountPersistServiceTest {
	private AccountPersistService service;
	
	@Before
	public void prepare() throws AccountPersistException {
		File persistDataFile = new File("target/test-classes/persist-data.xml");
		
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
		service = (AccountPersistService)ctx.getBean("accountPersistService");
		
		Account account = new Account();
		
		account.setId("dragon");
		account.setName("applynext");
		account.setEmail("applynext@163.com");
		account.setPassword("this is password");
		account.setActivited(true);
		
		service.createAccount(account);
	}

	@Test
	public void testCreateAccount() {
		
	}

	@Test
	public void testReadAccount() throws AccountPersistException {
		Account account = service.readAccount("dragon");
		
		assertNotNull(account);
		assertEquals("dragon", account.getId());
		assertEquals("applynext", account.getName());
		assertEquals("applynext@163.com", account.getEmail());
		assertEquals("this is password", account.getPassword());
		assertEquals(true, account.isActivited());
		
	}
	
	@Test
	public void testUpdateAccount() throws AccountPersistException {
		Account account = service.readAccount("dragon");
		
		account.setName("applynext2");
		account.setEmail("applynext2@163.com");
		account.setPassword("this is password 2");
		account.setActivited(false);

		service.updateAccount(account);
		
		account = service.readAccount("dragon");
		
		assertEquals("applynext2", account.getName());
		assertEquals("applynext2@163.com", account.getEmail());
		assertEquals("this is password 2", account.getPassword());
		assertEquals(false, account.isActivited());
		
	}
}
