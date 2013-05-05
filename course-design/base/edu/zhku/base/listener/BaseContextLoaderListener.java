package edu.zhku.base.listener;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.base.BaseKey;
import edu.zhku.base.utils.ConfigLoader;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.log.Log;
import edu.zhku.fr.service.PrivilegeService;
import edu.zhku.fr.service.RoleService;
import edu.zhku.fr.service.UserService;

/**
 * 基础模块的监听器，初始化系统
 * 
 * @author XJQ
 * @since 2013-3-23
 */
@SuppressWarnings("unchecked")
public class BaseContextLoaderListener implements ServletContextListener {

	private ServletContextEvent context = null;
	private ApplicationContext acx = null;
	
	// ----------------------------------------------------------------------
	
	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		Log.info("Destorying base module......");
		Log.info("Finished Dstorying base module......");
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		this.context = sc;
		acx = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		Log.info("Loading base module......");
		// 初始化配置项ConfigLoader
		this.initBaseConfig();
		// 初始化本模块配置数据
		this.initBaseSystemData();
		// 初始化系统所需数据
		this.readyBaseContextData();
		Log.info("Finished Loading base module......");
	}

	/**
	 * 初始化本模块需要的初始化数据
	 */
	private void initBaseSystemData() {
		Log.debug("Installing module data");
		// 1，初始化，安装系统超级管理员数据
		this.installSuperAdmins();
		// 2， 初始化，安装系统固有角色数据
		this.installInherentRoles();
		// 3， 安装默认角色
		this.installDefaultRole();
		Log.debug("Finished installing module data");
	}

	/**
	 * 安装默认角色
	 */
	private void installDefaultRole() {
		Log.info("Installing module defaule role.");
		Role dfRole = ConfigCenter.getConfig(BaseKey.DEFAULT_ROLE, Role.class);
		RoleService roleService = acx.getBean(RoleService.class);
		if(dfRole != null) {
			Role role =  roleService.getByName(dfRole.getName());
			if(role != null) {
				ConfigCenter.setConfig(BaseKey.DEFAULT_ROLE, role);
			} else {
				roleService.save(dfRole);
			}
		}
		Log.info("Finished installing module defaule role.");
	}

	/**
	 * 安装系统固有角色
	 */
	private void installInherentRoles() {
		Log.info("Installing module inherent roles");
		List<Role> roles = ConfigCenter.getConfig(BaseKey.INHERENT_ROLES, List.class);
		Role temp = null;
		RoleService roleService = acx.getBean(RoleService.class);
		for (Role role : roles) {
			temp = roleService.getByName(role.getName());
			if(temp != null) {
				role = temp;
			} else {
				roleService.save(role);
			}
		}
		Log.info("Finished installing module inherent roles");
	}

	/**
	 * 安装超级管理员
	 */
	private void installSuperAdmins() {
		Log.info("Installing module super admins");
		List<User> admins = ConfigCenter.getConfig(BaseKey.SUPER_ADMINS, List.class);
		User temp = null;
		UserService userService = acx.getBean(UserService.class);
		for (User admin : admins) {
			temp = userService.getByAccount(admin.getAccount());
			if(temp != null) {
				admin = temp;
			} else {
				userService.save(admin);
			}
		}
		Log.info("Finished installing module super admins");
	}

	/**
	 * 准备系统基础数据
	 * 
	 * @param sc
	 */
	private void readyBaseContextData() {
		// 中间准备一些数据
		// 获取PrivilegeService
		PrivilegeService privService = acx.getBean(PrivilegeService.class);
		List<Privilege> topPrivs = privService.getTopPrivileges();
		Key.TOP_PRIVILEGE.value(topPrivs);
		context.getServletContext().setAttribute(Key.TOP_PRIVILEGE.keys(), topPrivs);
		ConfigCenter.setConfig(Key.TOP_PRIVILEGE, topPrivs);

		// 获取所有的权限Action作为一个集合
		Collection<String> allPrivActions = privService.getAllPrivActions();
		Key.ALL_PRIV_ACTIONS.value(allPrivActions);
		ConfigCenter.setConfig(Key.ALL_PRIV_ACTIONS, allPrivActions);
		context.getServletContext().setAttribute(Key.ALL_PRIV_ACTIONS.keys(), allPrivActions);

		RoleService roleService = acx.getBean(RoleService.class);
		Role defaultRole = ConfigCenter.getConfig(BaseKey.DEFAULT_ROLE, Role.class);
		if(defaultRole == null || defaultRole.getId() == null) {
			defaultRole = new Role("普通用户", "用户在注册时候就是以普通用户的身份", Role.TYPE_NOT_DEL);
			roleService.save(defaultRole);
		}
		context.getServletContext().setAttribute(BaseKey.DEFAULT_ROLE.keys(), defaultRole);
	}

	/**
	 * 初始化配置项
	 */
	private void initBaseConfig() {
		Log.info("Initializing Base module configuration......");
		ConfigLoader.init(getConfigFile());
		Log.info("Finished Initializing Base module configuration......");
	}

    private String getConfigFile() {
        String file = context.getServletContext().getInitParameter(BaseKey.BASE_CONFIG.keys());
        return file == null ? BaseKey.BASE_CONFIG.values() : file;
    }

}
