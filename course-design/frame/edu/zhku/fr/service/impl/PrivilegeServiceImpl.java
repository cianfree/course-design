package edu.zhku.fr.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.DaoRunntimeException;
import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.service.PrivilegeService;

/**
 * 权限业务组件默认实现
 * 
 * @author XJQ
 * @since 2013-1-19
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends HibernateDaoSupport<Privilege> implements PrivilegeService {

	@Override
	public List<Privilege> queryAll() throws DaoRunntimeException {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.active=:active ORDER BY t.id ASC")//
				.setParameter("active", true)//
				.list();
	}

	@Override
	public List<Privilege> getTopPrivileges() {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.parent IS NULL AND t.active=:active ORDER BY t.id ASC")//
				.setParameter("active", true)//
				.list();
	}

	@Override
	public Collection<String> getAllPrivActions() {
		return this.getSession().createQuery(//
				"SELECT t.action FROM " + this.getEntityName() + " t WHERE t.active=:active AND t.action IS NOT NULL")//
				.setParameter("active", true)//
				.list();
	}

	@Override
	public Privilege getByName(String name) {
		return (Privilege) this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.active=:active AND t.name=:name")//
				.setParameter("name", name)//
				.setParameter("active", true)//
				.uniqueResult();
	}

	@Override
	public List<Privilege> getByAction(String action) {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.active=:active AND t.action=:action")//
				.setParameter("action", action)//
				.setParameter("active", true)//
				.list();

	}

	@Override
	public Privilege getTopPrivilegeByNameAndAction(String name, String action) {
		return (Privilege) this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.name=:name AND t.action=:action AND t.parent IS NULL")//
				.setParameter("name", name)//
				.setParameter("action", action)//
				.uniqueResult();
	}

    @Override
    public void setNotActive() {
        this.getSession().createQuery(//
                "UPDATE " + this.getEntityName() + " t SET t.active=:active")//
                .setParameter("active", false)//
                .executeUpdate();
    }

    @Override
    public void activePrivilege(Privilege priv) {
        priv.setActive(true);
        this.update(priv);
        this.getSession().createQuery(//
                "UPDATE " + this.getEntityName() + " t SET t.active=:active WHERE t.parent=:parent")//
                .setParameter("active", true)//
                .setParameter("parent", priv)//
                .executeUpdate();
    }

    @Override
    public void deleteNotActivePriv() {
        this.getSession().createQuery(//
                "DELETE FROM " + this.getEntityName() + " t WHERE t.active=:active AND t.parent IS NOT NULL")//
                .setParameter("active", false)//
                .executeUpdate();
        this.getSession().createQuery(//
                "DELETE FROM " + this.getEntityName() + " t WHERE t.active=:active")//
                .setParameter("active", false)//
                .executeUpdate();
    }
}
