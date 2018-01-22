package com.dragon.mvnbook.account.captcha;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountCaptchaServiceTest {
	
	private AccountCaptchaService service;

	
	@Before
	/** 
     * 运行在测试方法前，初始化名为AccountCaptchaService的bean
     */  
	public void prepare() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
		service = (AccountCaptchaService) ctx.getBean("accountCaptchaService");
	}

	@Test
	/**
	 * 测试验证码图片生成 
	 * @throws AccountCaptchaException
	 * @throws IOException
	 */
	public void testGenerateCaptcha() throws AccountCaptchaException, IOException {
		String captchaKey = service.generateCaptchaKey();
		assertNotNull(captchaKey);
		
		byte[] 	captchaImage = service.generateCaptchaImage(captchaKey);
		assertTrue(captchaImage.length > 0);
		
		//在项目的target目录下创建一个名为主键的jpg格式文件 
		File image = new File("target/" + captchaKey + ".jpg");
		OutputStream output = null;
		try {
			 //将验证码图片字节数组内容写入到jpg文件中  
			output = new FileOutputStream(image);
			output.write(captchaImage);
		} finally {
			if (output != null) {
				output.close();
			}
		}
		
		//检查文件是否存在且包含实际内容
		assertTrue(image.exists() && image.length() > 0);
	}

	@Test
	/**
	 * 测试验证流程正确性
	 * @throws AccountCaptchaException
	 */
	public void testValidateCaptchaCorrect() throws AccountCaptchaException {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		service.setPreDefinedTexts(preDefinedTexts);
		
		String captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		assertTrue(service.validateCaptcha(captchaKey, "12345"));
		
		captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		assertTrue(service.validateCaptcha(captchaKey, "abcde"));
	}
	
	@Test
	/**
	 * 测试用户反馈Captcha错误时发生情况
	 * @throws AccountCaptchaException
	 */
	public void testValidateCaptchaInCorrect() throws AccountCaptchaException {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		service.setPreDefinedTexts(preDefinedTexts);
		
		String captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		assertFalse(service.validateCaptcha(captchaKey, "abced"));
		
	}
}
