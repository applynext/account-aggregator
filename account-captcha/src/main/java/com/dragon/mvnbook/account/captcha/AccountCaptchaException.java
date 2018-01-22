package com.dragon.mvnbook.account.captcha;

import java.io.IOException;

public class AccountCaptchaException extends Exception {

	public AccountCaptchaException(String string) {
		super(string);
	}

	public AccountCaptchaException(String string, Throwable throwable) {
		super(string, throwable);
	}

}
