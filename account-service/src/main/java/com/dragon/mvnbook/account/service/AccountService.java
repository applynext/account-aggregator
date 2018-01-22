package com.dragon.mvnbook.account.service;

import com.dragon.mvnbook.account.captcha.AccountCaptchaException;

/**
 * 账户服务接口
 * @author Dragon
 *
 */
public interface AccountService {
	/**
	 * 生成验证码主键
	 * @return	验证码主键
	 * @throws AccountCaptchaException 
	 * @throws AccountServiceException 
	 */
	String generateCaptchaKey() throws AccountServiceException;
	
	/**
	 * 生成验证码图片
	 * @param captchaKey	验证码主键
	 * @return	验证码字节数组
	 * @throws AccountServiceException 
	 */
	byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;
	
	/**
	 * 账户注册
	 * @param signUpRequest	账户注册信息类
	 * @throws AccountServiceException 
	 */
	void signUp(SignUpRequest signUpRequest) throws AccountServiceException;
	
	/**
	 * 根据激活码激活账户
	 * @param activationNumber	激活码
	 * @throws AccountServiceException 
	 */
	void activited(String activationId) throws AccountServiceException;
	
	/**
	 * 登录
	 * @param id	账户id
	 * @param password	账户密码
	 * @throws AccountServiceException 
	 */
	void login(String id, String password) throws AccountServiceException;
	
}
