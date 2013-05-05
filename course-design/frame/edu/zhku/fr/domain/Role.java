package edu.zhku.fr.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色
 * 
 * @author XJQ
 * @since 2013-1-17
 */
public class Role {
    private Long id; // 数据库中唯一标识
    private String name; // 角色的名称，必须是唯一的
    private String description; // 角色相关的描述
    private Integer type = TYPE_CAN_DEL; // 1表示系统固有角色，不可删除，0表示扩展属性，可删除
    private Set<Privilege> privs = new HashSet<Privilege>(); // 角色具有那些权限
    private Set<User> users = new HashSet<User>(); // 该角色下面有哪些用户

    public static final int TYPE_CAN_DEL = 0;
    public static final int TYPE_NOT_DEL = 1;

    public Role() {
    }

    public Role(String name, String description, Integer type) {
        this.name = name;
        this.description = description;
        this.type = type == null ? 0 : 1;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Set<Privilege> getPrivs() {
        return this.privs;
    }

    public void setPrivs(Set<Privilege> privs) {
        this.privs = privs;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type + "]";
    }

    /**
     * 获取角色相关的权限对象
     * 
     * @param pid
     * @return
     */
    public Privilege getPrivilege(Long pid) {
        for (Privilege priv : this.privs) {
            if (priv.getId().equals(pid)) {
                return priv;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role) {
            Role role = (Role) obj;
            return this.getId().equals(role.getId());
        }
        return false;
    }

}
