package com.dragon.mvnbook.account.captcha;

import java.util.Random;

public class RandomGenerator {

	// 验证码字符范围
	private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

	/**
	 * 静态且安全地获取一个长度为8的随机字符串
	 * 
	 * @return 随机字符串值
	 */
	public static synchronized String getRandomString() {
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			result.append(range.charAt(random.nextInt(range.length())));
		}
		return result.toString();
	}

}
