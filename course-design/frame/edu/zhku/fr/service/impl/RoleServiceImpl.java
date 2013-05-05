package edu.zhku.fr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.DaoRunntimeException;
import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.service.RoleService;

/**
 * Role业务组件实现
 *
 * @author XJQ
 * @since 2013-1-18
 */
@Service
@Transactional
public class RoleServiceImpl extends HibernateDaoSupport<Role> implements RoleService {

	@Override
	public Role getByName(String roleName) {
		if(roleName == null || "".equals(roleName)) return null;
		return (Role)this.getSession().createQuery("FROM " + this.getEntityName() + " r where r.name=:name")//
				.setParameter("name", roleName)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getByNames(String... names) {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " WHERE name IN (:names)")//
				.setParameterList("names", names)//
				.list();
	}

    @Override
    public void deleteByName(String name) {
        this.getSession().createQuery(//
                "DELETE FROM " + this.getEntityName() + " t WHERE t.name=:name")
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public void save(Role role) throws DaoRunntimeException {
        if(this.getByName(role.getName()) == null) {
            super.save(role);
        }
    }
}
