package com.dragon.mvnbook.account.captcha;

import java.util.List;

/**
 * 验证码接口服务
 * @author Dragon
 *
 */
public interface AccountCaptchaService {
	/**
	 * 生成随机的验证码主键
	 * 
	 * @return 验证码主键
	 * @throws AccountCaptchaException
	 */
	String generateCaptchaKey() throws AccountCaptchaException;
	/**
	 * 根据难证码主键生成验证码图片
	 * 
	 * @param captchaKey 验证码主键
	 * @return 验证码图片字节数组
	 * @throws AccountCaptchaException
	 */
	byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
	/**
	 * 验证用户反馈的主键和值
	 * 
	 * @param captchaKey  验证码主键
	 * @param captchaValue 验证码的值
	 * @return 是否验证成功
	 * @throws AccountCaptchaException
	 */
	boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;
	/**
	 * 获取预定义验证码内容
	 * 
	 * @return 验证码
	 */
	List<String> getPreDefinedTexts();
	/**
	 * 设定预定义验证码图片的内容
	 * 
	 * @param preDefinedTexts 预定义验证码文本列表对象
	 */
	void setPreDefinedTexts(List<String> preDefinedTexts);
}
