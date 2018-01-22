package com.dragon.mvnbook.account.persist;

public class Account {
	private String id;
	private String name;	//帐户显示名称
	private String email;	//帐户名
	private String password;
	private boolean activited;	//帐户是否被激活
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActivited() {
		return activited;
	}
	public void setActivited(boolean activited) {
		this.activited = activited;
	}
	
}
