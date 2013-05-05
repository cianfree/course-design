package edu.zhku.fr.service;

import java.util.List;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.User;

/**
 * 用户业务逻辑处理
 *
 * @author XJQ
 * @since 2013-1-10
 */
public interface UserService extends DaoSupport<User> {

	/**
	 * 根据User的账户查找相应的User对象
	 * @param account 要查找的用户的帐号，帐号是唯一的
	 * @return	如果存在就返回User对象，否则就返回null
	 */
	public User getByAccount(String account);

	/**
	 * 验证邮箱是否存在，如果存在就返回true，否则返回false
	 * @param email	要验证的邮箱
	 * @return true-->存在， false-->不存在
	 */
	public boolean isEmailExists(String email);

	/**
	 * 批量保存用户
	 * @param users
	 */
	public void save(List<User> users);

}
