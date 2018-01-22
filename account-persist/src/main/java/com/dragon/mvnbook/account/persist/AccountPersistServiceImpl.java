package com.dragon.mvnbook.account.persist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class AccountPersistServiceImpl implements AccountPersistService {

	private static final String ELEMENT_ROOT = "account-persist";
	private static final String ELEMENT_ACCOUNTS = "accounts";
	private static final String ELEMENT_ACCOUNT_ID = "id";
	private static final String ELEMENT_ACCOUNT = "account";
	private static final String ELEMENT_ACCOUNT_NAME = "name";
	private static final String ELEMENT_ACCOUNT_EMAIL = "email";
	private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
	private static final String ELEMENT_ACCOUNT_ACTIVITED = "activited";
	private String file;
	private SAXReader reader = new SAXReader();

	public Account createAccount(Account account) throws AccountPersistException {
		Document doc = readDocument();
		Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
		accountsEle.add(buildAccountElement(account));
		writeDocument(doc);
		return account;
	}

	private Element buildAccountElement(Account account) {
		Element element = DocumentFactory.getInstance().createElement(ELEMENT_ACCOUNT);
		element.addElement(ELEMENT_ACCOUNT_ID).setText(account.getId());
		element.addElement(ELEMENT_ACCOUNT_NAME).setText(account.getName());
		element.addElement(ELEMENT_ACCOUNT_EMAIL).setText(account.getEmail());
		element.addElement(ELEMENT_ACCOUNT_PASSWORD).setText(account.getPassword());
		element.addElement(ELEMENT_ACCOUNT_ACTIVITED).setText(account.isActivited() ? "true" : "false");
		return element;
	}

	private Document readDocument() throws AccountPersistException {
		File dataFile = new File(file);
		if (!dataFile.exists()) {
			dataFile.getParentFile().mkdirs();
			Document doc = DocumentFactory.getInstance().createDocument();
			Element rootEle = doc.addElement(ELEMENT_ROOT);
			rootEle.addElement(ELEMENT_ACCOUNTS);
			writeDocument(doc);
		}
		try {
			return reader.read(new File(file));
		} catch (DocumentException e) {
			throw new AccountPersistException("不能正确读取到XML文件", e);
		}
	}

	private void writeDocument(Document doc) throws AccountPersistException {
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
			writer.write(doc);
		} catch (IOException e) {
			throw new AccountPersistException("不能写入XML文件", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new AccountPersistException("不能关闭XML文件", e);
			}
		}
		
	}

	public Account readAccount(String id) throws AccountPersistException {
		Document doc = readDocument();
		Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
		for (Element accountEle : (List<Element>)accountsEle.elements()) {
			if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id)) {
				return buildAccount(accountEle);
			}
		}
		return null;
	}

	private Account buildAccount(Element accountEle) {
		Account account = new Account();
		
		account.setId(accountEle.elementText(ELEMENT_ACCOUNT_ID));
		account.setName(accountEle.elementText(ELEMENT_ACCOUNT_NAME));
		account.setPassword(accountEle.elementText(ELEMENT_ACCOUNT_PASSWORD));
		account.setEmail(accountEle.elementText(ELEMENT_ACCOUNT_EMAIL));
		account.setActivited(accountEle.elementText(ELEMENT_ACCOUNT_ACTIVITED).equals("true") ? true : false);
		
		return account;
	}

	public Account updateAccount(Account account) throws AccountPersistException {
		if (readAccount(account.getId()) != null) {
			deleteAccount(account.getId());
			
			return createAccount(account);
		}
		return null;
	}

	public void deleteAccount(String id) throws AccountPersistException {
		Document doc = readDocument();
		
		Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
		
		for (Element accountEle : (List<Element>) accountsEle.elements()) {
			if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id)) {
				accountEle.detach();
				
				writeDocument(doc);
				
				return;
			}
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
