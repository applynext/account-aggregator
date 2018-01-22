package com.dragon.mvnbook.account.persist;

import java.io.IOException;

import org.dom4j.DocumentException;

public class AccountPersistException extends Exception {

	public AccountPersistException(String string, Throwable e) {
		super(string, e);
	}

}
