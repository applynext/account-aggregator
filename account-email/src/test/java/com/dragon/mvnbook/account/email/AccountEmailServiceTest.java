package com.dragon.mvnbook.account.email;

import static org.junit.Assert.*;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {
	private GreenMail greenMail;

	@Before
	public void startMailServer() throws Exception {
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("","");
		greenMail.start();
	}

	@Test
	public void testSendMail() throws AccountEmailException, InterruptedException, MessagingException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService)ctx.getBean("accountEmailService");
		
		String subject = "Test Subject";
		String htmlText = "<h3>Test</he>";
		accountEmailService.sendMail("", subject, htmlText);
		
//		greenMail.waitForIncomingEmail(2000,1);
//		Message[] msgs = greenMail.getReceivedMessages();
//		
//		System.out.println("msgs.length:" + msgs.length);
//		System.out.println("msgs[0].getSubject()" + msgs[0].getSubject());
//		System.out.println("GreenMailUtil.getBody(msgs[0]).trim()" + GreenMailUtil.getBody(msgs[0]).trim());
		
//		assertEquals(1, msgs.length);
//		assertEquals(subject, msgs[0].getSubject());
//		assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
		
	}
	
	@After
	public void stopMailServer() throws Exception {
		greenMail.stop();
	}

}
