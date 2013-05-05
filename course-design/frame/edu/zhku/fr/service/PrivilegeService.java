package edu.zhku.fr.service;

import java.util.Collection;
import java.util.List;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.Privilege;

/**
 * 权限业务组件接口
 *
 * @author XJQ
 * @since 2013-1-19
 */
public interface PrivilegeService extends DaoSupport<Privilege> {
	/**
	 * 获取顶级权限
	 * @return
	 */
	public List<Privilege> getTopPrivileges();

	/**
	 * 获取所有的Action
	 * @return
	 */
	public Collection<String> getAllPrivActions();
	
	/**
	 * 根据权限的名称获取
	 * @param name
	 * @return
	 */
	public Privilege getByName(String name);
	
	/**
	 * 根据权限的动作名称获取
	 * @param action
	 * @return
	 */
	public List<Privilege> getByAction(String action);

	/**
	 * 精确匹配，只有name和action都匹配的时候，并且parent为null才会返回
	 * @param name
	 * @param action
	 * @return
	 */
	public Privilege getTopPrivilegeByNameAndAction(String name, String action);

	/**
	 * 将所有权限设置为未激活状态
	 */
    public void setNotActive();

    /**
     * 激活
     * @param priv
     */
    public void activePrivilege(Privilege priv);

    /**
     * 删除没有激活的权限
     */
    public void deleteNotActivePriv();
	
}
