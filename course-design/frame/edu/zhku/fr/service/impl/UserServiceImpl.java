package edu.zhku.fr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.UserService;

/**
 * 用户相关业务处理类
 *
 * @author XJQ
 * @since 2013-1-10
 */
@Service
@Transactional
public class UserServiceImpl extends HibernateDaoSupport<User> implements UserService {
	
	@Override
	public User getByAccount(String account) {
		if(account == null || "".equals(account)) return null;
		return (User) this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.account=:account")//
				.setParameter("account", account)//
				.uniqueResult();
	}

	@Override
	public boolean isEmailExists(String email) {
		return (User)this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.email=:email")//
				.setParameter("email", email)//
				.uniqueResult()
				== null ? false : true;
	}

	@Override
	public void save(List<User> users) {
		int n = 0;
		for (User user : users) {
			this.save(user);
			if(n % 20 == 0) {
				this.getSession().flush();
				this.getSession().clear();
			}
			n ++;
		}
	}

}
