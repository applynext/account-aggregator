package com.dragon.mvnbook.account.email;

/**
 * 邮件服务
 * @author Dragon
 *
 */
public interface AccountEmailService {
	/**
	 * 发送邮件
	 * @param to 发送目标对象
	 * @param subject 邮件主题
	 * @param htmlText 邮件网页文本内容
	 * @throws AccountEmailException
	 */
	void sendMail(String to, String subject, String htmlText) throws AccountEmailException;
}
