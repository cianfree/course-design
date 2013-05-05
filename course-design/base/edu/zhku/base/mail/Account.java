package edu.zhku.base.mail;

/**
 * 普通邮箱
 * 
 * @author XJQ
 * 
 *         2013-3-13
 */
public class Account {
	private String account;
	private String password;
	private String address;

	private ServerConfig serverConfig;

	public Account() {
	}

	public Account(String account, String password, ServerConfig serverConfig) {
		this.account = account;
		this.password = password;
		this.serverConfig = serverConfig;
		this.address = account + "@" + this.getServerConfig().getDomain();
	}
	
	public Account(String address) {
		this.setAddress(address);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public void setAddress(String address) {
		this.address = address;
		String[] array = address.split("@");
		this.account = array[0];
	}
	
	public String getAddress() {
		return address;
	}
}
