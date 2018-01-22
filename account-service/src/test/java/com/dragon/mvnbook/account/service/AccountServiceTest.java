package com.dragon.mvnbook.account.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dragon.mvnbook.account.captcha.AccountCaptchaException;
import com.dragon.mvnbook.account.captcha.AccountCaptchaService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountServiceTest {
	
	private AccountService accountService;
	private GreenMail greenMail;

	@Before
	public void prepare() {
		String[] springConfigFiles = {"account-email.xml", "account-persist.xml",
				"account-captcha.xml", "account-service.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(springConfigFiles);
		
		AccountCaptchaService accountCaptchaService = (AccountCaptchaService) ctx.getBean("accountCaptchaService");
		
		List<String> preDefinedTexts = new ArrayList<>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountCaptchaService.setPreDefinedTexts(preDefinedTexts);
		
		accountService = (AccountService) ctx.getBean("accountService");
		
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("", "");
		greenMail.start();
		
		File persistDataFile = new File("target/test-classes/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}
	}

	@Test
	public void testAccountService() throws AccountServiceException, InterruptedException, MessagingException, AccountCaptchaException {
		//1.Get Captcha
		String captchaKey = accountService.generateCaptchaKey();
		accountService.generateCaptchaImage(captchaKey);
		String captchaValue = "12345";
		
		//2.Submit sign up Request
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setCaptchaKey(captchaKey);
		signUpRequest.setCaptchaValue(captchaValue);
		signUpRequest.setId("dragon");
		signUpRequest.setName("dragon");
		signUpRequest.setEmail("");
		signUpRequest.setPassword("admin");
		signUpRequest.setConfirmPassword("admin");
		signUpRequest.setActivateServiceUrl("http://localhost:8080/acount/activate");
		accountService.signUp(signUpRequest);
		
		//3.read activation link
//		greenMail.waitForIncomingEmail(2000, 1);
//		Message[] msgs = greenMail.getReceivedMessages();
//		assertEquals(1, msgs.length);
//		assertEquals("Please Activate Your Account", msgs[0].getSubject());
//		String activationLink = GreenMailUtil.getBody(msgs[0]).trim();
		
		String activationLink = signUpRequest.getActivateServiceUrl() + "";
		//3a.Try login but not activated
		try {
			accountService.login("dragon", "admin");
			fail("Disable account shouldn't be able to login");
		} catch (AccountServiceException e) {
		}
		
		//4.Activate account
		String activationId = activationLink.substring(activationLink.indexOf("=") + 1);
		accountService.activited(activationId);
		
		//5.Login with correct id and password
		accountService.login("dragon", "admin");
		
		//5a.Login with incorrect password
		try {
			accountService.login("dragon", "admin1");
			fail("Password id incorrect ,shouldn't be able to login");
		} catch (AccountServiceException e) {
		}
	}
	
	@After
	public void stopMailServer() {
		greenMail.stop();
	}
}
