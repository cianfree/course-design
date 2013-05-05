package edu.zhku.fr.domain;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;

/**
 * 用户业务模型
 * 
 * @author XJQ
 * @since 2013-1-10
 */
public class User {
    private Long id;    // 用于数据库中唯一标识
    private String account; // 帐号
    private String password; // 密码
    private String name; // 真实姓名
    private String sex; // 性别
    private String email; // 邮件
    private Date regTime; // 注册时间
    private Integer active; // 邮件激活的状态，1表示已经激活, 0表示未激活

    private Set<Role> roles = new HashSet<Role>();  // 包含的角色

    public static final Integer ACTIVIED = 1;
    public static final Integer NOT_ACTIVIED = 0;

    public User() {
        this.active = User.NOT_ACTIVIED;
    }

    public User(String account) {
        this.account = account;
        this.active = User.NOT_ACTIVIED;
    }

    public User(String account, String name, String password, Integer active,
            String email) {
        this.account = account;
        this.name = name;
        this.password = DigestUtils.md5Hex(password);
        this.active = active == null ? NOT_ACTIVIED : active;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "User [id=" + id + ", account=" + account + ", password="
                + password + ", name=" + name + ", regTime: "
                + (this.regTime == null ? "" : sdf.format(this.regTime)) + "]";
    }

    /**
     * 判断是是否包含指定id的角色
     * 
     * @param id
     * @return
     */
    public boolean hasRole(Long id) {
        for (Role role : roles) {
            if (role.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * 验证是否包含权限
     * 
     * @param pid
     *            权限对象的唯一标识符：id
     * @return
     */
    public boolean hasPrivilege(Long pid) {
        if (isAdmin()) return true; // 如果是超级管理员就应该包含所有权限
        for (Role role : this.roles) {
            if (role.getPrivilege(pid) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是超级管理员
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean isAdmin() {
        List<User> admins = ConfigCenter.getConfig(Key.SUPER_ADMINS, List.class);
        for (User user : admins) {
            if (this.getAccount().equals(user.getAccount())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据href来确定权限
     * 
     * @param action
     * @return
     */
    public boolean hasPrivilege(String action) {
        if (action == null || "".equals(action) || this.isAdmin() || !isUnderControlled(action))
            return true;
        for (Role role : this.getRoles()) {
            for (Privilege priv : role.getPrivs()) {
                if (action.equals(priv.getAction())) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean isUnderControlled(String action) {
        Collection<String> actions = ConfigCenter.getConfig(Key.ALL_PRIV_ACTIONS, Collection.class);
        return actions.contains(action);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User user = (User) obj;
            return this.getId().equals(user.getId());
        }
        return false;
    }
}
