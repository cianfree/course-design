package edu.zhku.fr.service;

import java.util.List;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.Role;

/**
 * 角色相关的业务服务组件
 *
 * @author XJQ
 * @since 2013-1-18
 */
public interface RoleService extends DaoSupport<Role> {

	/**
	 * 通过角色名获取角色
	 * @param roleName
	 * @return	如果存在就返回一个角色，否则就返回null
	 */
	Role getByName(String roleName);

	/**
	 * 获取角色
	 * @param names
	 * @return
	 */
	List<Role> getByNames(String... names);

	/**
	 * 根据名称来删除角色信息
	 * @param name
	 */
    void deleteByName(String name);
	
}
