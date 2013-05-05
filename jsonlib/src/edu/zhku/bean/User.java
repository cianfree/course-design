package edu.zhku.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户Json的测试类
 * 
 * @author arvin
 * 
 */
public class User extends UserBean {
	private String name;
	private User wife;
	private Set<User> friends;
	private List<User> familys;
	private Map<String, User> others;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getWife() {
		return wife;
	}

	public void setWife(User wife) {
		this.wife = wife;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public List<User> getFamilys() {
		return familys;
	}

	public void setFamilys(List<User> familys) {
		this.familys = familys;
	}

	public Map<String, User> getOthers() {
		return others;
	}

	public void setOthers(Map<String, User> others) {
		this.others = others;
	}

}
