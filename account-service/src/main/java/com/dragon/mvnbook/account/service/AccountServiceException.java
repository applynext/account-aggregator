package com.dragon.mvnbook.account.service;


public class AccountServiceException extends Exception {

	public AccountServiceException(String string, Throwable e) {
		super(string, e);
	}

	public AccountServiceException(String string) {
		super(string);
	}

}
