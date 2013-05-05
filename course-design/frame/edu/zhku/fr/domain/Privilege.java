package edu.zhku.fr.domain;

import java.util.HashSet;
import java.util.Set;


/**
 * 权限类，一个对象代表一个权限
 * 
 * @author XJQ
 * @since 2013-1-18
 */
public class Privilege {
	private Long id;   // 数据库中唯一标识
	private String name;   // 权限名称，唯一
	private String action; // 权限对应的URL动作，唯一
	private Privilege parent;  // 父权限
	private boolean active = true;
	private Set<Privilege> children = new HashSet<Privilege>();    // 子权限
	private Set<Role> roles = new HashSet<Role>(); // 包含的角色

	public Privilege() {}
	
	public Privilege(String name, String action, Privilege parent) {
		this.name = name;
		this.action = action;
		this.parent = parent;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public Set<Privilege> getChildren() {
		return children;
	}

	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", name=" + name + ", action=" + action + "]";
	}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
