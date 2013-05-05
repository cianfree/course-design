package edu.zhku.base.ajax;

import org.apache.commons.codec.digest.DigestUtils;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.base.BaseKey;
import edu.zhku.base.utils.Validator;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.ajax.BaseAjax;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.log.Log;
import edu.zhku.fr.priv.tag.Validate;
import edu.zhku.fr.service.RoleService;
import edu.zhku.fr.service.UserService;
import edu.zhku.module.AjaxMethod;
import edu.zhku.module.AjaxModule;

/**
 * 基础应用模块Ajax服务
 * 
 * @author XJQ
 * date 2013-4-28
 */
@AjaxModule("base")
public class BaseAjaxModule extends BaseAjax {
    
    /**
     * 用户注销操作
     * @param kvList
     * @return
     */
    @AjaxMethod("logout")
    public static boolean logout(KVList kvList) {
        boolean state = false;
        try {
            getSession(kvList).removeAttribute(BaseKey.CURRENT_USER);
            state = true;
        } catch (Exception e) {
            Log.info("Logout error");
        }
        return state;
    }

    /**
     * 使用Ajax请求验证是否存在角色
     * 
     * @param name 角色名
     * @return 0表示未传入，1表示存在，2表示不存在
     */
    @AjaxMethod("hasRole")
    public static int isRoleExists(KVList kvList) {
        String name = kvList.getString("name");
        if (name == null) {
            return 0;
        } else {
            Role role = getBean(RoleService.class, kvList).getByName(name);
            return role == null ? 2 : 1;
        }
    }
    
    /**
     * 验证邮箱是否已经存在
     * 
     * @param email
     *            要验证的邮箱
     * @return 如果存在就返回1，如果邮箱格式不正确就返回-1，如果邮箱不存在就返回0
     */
    @AjaxMethod("hasEmail")
    public static int isEmailExists(KVList kvList) {
        String email = kvList.getString("email");
        if (!Validator.isEmail(email))
            return -1;
        return getBean(UserService.class, kvList).isEmailExists(email) ? 1 : 0;
    }
    
    /**
     * 初始化密码为pwd
     * 
     * @param id
     *            要进初始化的id
     * @param pwd
     *            要初始化的密码
     * @return
     */
    @AjaxMethod("initpwd")
    public static boolean initPwd(KVList kvList) {
        Long id = kvList.getLong("id");
        String newPwd = kvList.getString("pwd");
        UserService userService = getBean(UserService.class, kvList);
        if (id == null)
            return false;
        User user = userService.get(id);
        if (user != null) {
            user.setPassword(getEncodePwd(newPwd));
            userService.update(user);
            return true;
        }
        return false;
    }

    /**
     * 使用Ajax请求验证是否存在用户
     * 
     * @param account
     * @return 0 -- 表示没有该用户， 1 表示存在用户但是没有激活，2表示已经被激活的账户
     */
    @AjaxMethod("hasUser")
    public static int isUserExists(KVList kvList) {
        String account = kvList.getString("account");
        if (account == null) {
            return 0;
        } else {
            User user = getBean(UserService.class, kvList).getByAccount(account);
            return user == null ? 0 : user.getActive() == User.ACTIVIED ? 2 : 1;
        }
    }
    
    /**
     * 密码加密
     * @param newPwd
     * @return
     */
    private static String getEncodePwd(String newPwd) {
        if (newPwd == null || "".equals(newPwd))
            return DigestUtils.md5Hex(ConfigCenter.getConfig(BaseKey.USER_INIT_PWD).toString());
        return DigestUtils.md5Hex(newPwd);
    }
    
    /**
     * 检测用户是否有权限
     * @param kvList
     * @return
     */
    @AjaxMethod("hasPriv")
    public static boolean hasPriv(KVList kvList) {
        Long userId = kvList.getLong("userId");
        String action = kvList.getString("action");
        User user = null;
        if(userId == null) {
            user = getCurrentUser(kvList);
        } else {
            UserService userService = getBean(UserService.class, kvList);
            userService.get(userId);
        }
        return Validate.hasPriv(user, action);
    }
    
}
