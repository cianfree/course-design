package edu.zhku.base.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.BaseKey;
import edu.zhku.base.exceptions.NoSuchMailAddressException;
import edu.zhku.base.mail.MailService;
import edu.zhku.base.utils.Validator;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.RoleService;
import edu.zhku.fr.service.UserService;

/**
 * 用户相控制器，如用户邮件激活，用户的添删改查
 * 
 * @author XJQ
 * @since 2013-1-11
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({ BaseKey.CURRENT_USER })
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private MailService mailService;

    @Resource
    private RoleService roleService;

    /**
     * 返回一个登录界面
     * 
     * @return
     */
    @RequestMapping("/loginUI.html")
    public String loginUI() {
        return "base/home/loginUI";
    }

    /**
     * 登录，如果验证成功就返回主页home.jsp, 否则就返回到登录界面，并在登录界面显示登录错误信息
     * 
     * @param account
     *            帐号
     * @param password
     *            密码
     * @return
     */
    @RequestMapping("login.html")
    public ModelAndView login(String account, String password, ModelMap map) {
        ModelAndView mav = new ModelAndView();
        User u = this.userService.getByAccount(account);
        if (u == null) {
            mav.addObject("message", "对不起，该用户不存在...");
            mav.setViewName("base/home/loginUI");
        } else if (!u.getPassword().equals(DigestUtils.md5Hex(password))) {
            mav.addObject("message", "抱歉，密码错误...");
            mav.setViewName("base/home/loginUI");
        } else {
            mav.setViewName("redirect:/home.html");
            map.addAttribute(BaseKey.CURRENT_USER, u);
            initializeUserRoles(u.getRoles());
        }
        return mav;
    }

    private void initializeUserRoles(Collection<Role> cols) {
        for (Role role : cols) {
            for (Privilege priv : role.getPrivs()) {
                Hibernate.initialize(priv);
            }
        }
    }

    /**
     * 激活帐号
     * 
     * @param account
     *            要激活的帐号
     * @param code
     *            激活码
     * @param map
     * @return
     */
    @RequestMapping("activate.html")
    public ModelAndView activate(String account,
            @RequestParam("code") String code, ModelMap map) {
        ModelAndView mav = new ModelAndView();
        User user = this.userService.getByAccount(account);

        if (user != null) {
            if (code != null
                    && code.equals(DigestUtils.md5Hex(user.getEmail()))) {
                user.setActive(User.ACTIVIED);
                this.userService.update(user);
                map.addAttribute("currentUser", user);
                initializeUserRoles(user.getRoles());
                mav.setViewName("redirect:/home.html");
                return mav;
            }
        }
        // 如果user是空的，那么就跳转到注册页面
        mav.setViewName("base/home/resendmail");
        mav.addObject("reguser", user);
        new Thread(new ReMailActivateTask(user)).start();
        return mav;
    }

    /**
     * 注册页面
     * 
     * @return
     */
    @RequestMapping("regUI.html")
    public String regUI() {
        return "base/home/regUI";
    }

    /**
     * 注册用户
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "reg.html", method = { RequestMethod.POST })
    public ModelAndView reg(User user, String repwd, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String message = validateUser(user, repwd);
        if (message == null) {
            // 给密码进行MD5加密
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setRegTime(new Date()); // 注册时间
            user.getRoles().add(this.getDefaultRole(session));
            user.setActive(User.NOT_ACTIVIED);
            mav.setViewName("base/home/regSuccess");
            // 同时把用户添加到session中，让其跳转到成功页面
            mav.addObject("reguser", user);

            this.sendActivateMail(user);
            return mav;
        }
        mav.addObject("message", message);
        mav.setViewName("redirect:/user/regUI.html");
        return mav;
    }

    /**
     * 发送激活邮件
     * 
     * @param user
     */
    private void sendActivateMail(User user) {
        new Thread(new MailActivateTask(user)).start();
    }

    /**
     * 服务端验证注册用户信息
     * 
     * @param user
     * @return
     */
    private String validateUser(User user, String repwd) {
        if (user == null)
            return "很抱歉，服务器错误...";
        if (!Validator.length(user.getAccount(), 6, 25))
            return "用户名长度必须在6-25个字符内...";
        if (user.getPassword() == null)
            return "用户密码不能为空...";
        else if (!user.getPassword().equals(repwd))
            return "两次输入的密码不一致...";
        if (!Validator.isEmail(user.getEmail()))
            return "抱歉，您输入的邮箱格式不正确...";
        if (this.userService.getByAccount(user.getAccount()) != null)
            return "很抱歉，该用户已经存在...";
        return null;
    }

    /**
     * 内部类，用于创建一个邮件发送的线程，因为邮件发送可能比较费时间
     * 
     * @author XJQ
     * @since 2013-1-11
     */
    private class MailActivateTask implements Runnable {

        private User user;

        public MailActivateTask(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            try {
                mailService.sendActivateMail(user);
                userService.save(user);
            } catch (NoSuchMailAddressException e) {
                // 如果没有这样的邮件地址，就删除这个用户
                if (user.getId() != null) {
                    userService.delete(user);
                }
            }
        }

    }

    private class ReMailActivateTask implements Runnable {

        private User user;

        public ReMailActivateTask(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            try {
                mailService.sendActivateMail(user);
            } catch (NoSuchMailAddressException e) {
                // 如果没有这样的邮件地址，就删除这个用户
                if (user.getId() != null) {
                    userService.delete(user);
                }
            }
        }

    }

    /**
     * 跳转到用户列表界面
     * 
     * @param account
     *            帐号,如果没有写的话就是""
     * @param name
     *            名字,如果没有写的话就是""
     * @param roleId
     *            角色ID 默认是null
     * @param currentPage
     *            当前页，默认是null
     * @param pageSize
     *            每页显示的大小，默认是null
     * @return
     */
    @RequestMapping("userList.html")
    public ModelAndView userList(String account, String name, Long roleId,
            Integer currentPage, Integer pageSize) {
        PageBean pb = this.userService.paging(
                //
                this.getCurrentPage(currentPage), //
                this.getPageSize(pageSize), //
                this.queryHelper
                        .setClass(User.class)
                        .setAlias("u")
                        .addCondiction(!Validator.isEmpty(account),
                                "u.account LIKE ?", "%" + account + "%")//
                        .addCondiction(!Validator.isEmpty(name),
                                "u.name LIKE ?", "%" + name + "%")//
                );

        ModelAndView mav = new ModelAndView("base/user/userList");

        mav.addObject("account", account);
        mav.addObject("name", name);

        mav.addObject("pb", pb);
        mav.addObject("defPwd", ConfigCenter.getConfigString(BaseKey.USER_INIT_PWD));
        return mav;
    }

    /**
     * 跳转到创建用户/修改用户的界面，如果是修改的话，就需要准备一个user对象，否则直接跳转
     * 
     * @param id
     *            用户的id，修改的时候需要，如果没有的话就是null
     * @return
     */
    @RequestMapping("editUserUI.html")
    public ModelAndView editUserUI(Long id) {
        ModelAndView mav = new ModelAndView("base/user/editUserUI");
        if (id != null) {
            mav.addObject("user", this.userService.get(id));
        }
        // 准备所有角色信息
        mav.addObject("roles", this.roleService.queryAll());
        return mav;
    }

    /**
     * 添加用户，包括添加角色信息
     * 
     * @param user
     *            要添加的用户
     * @param roleIds
     *            该用户包含的角色id数组
     * @return
     */
    @RequestMapping("saveUser.html")
    public ModelAndView saveUser(User user, Long[] roleIds, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/user/userList.html");

        user.setRegTime(new Date());
        user.setActive(User.ACTIVIED);
        user.setPassword(this.getEncodePwd(null));
        List<Role> roles = this.roleService.getByIds(roleIds);
        if(!roles.contains(this.getDefaultRole(session))) {
            user.getRoles().add(this.getDefaultRole(session));
        }
        user.getRoles().addAll(roles);
        this.userService.save(user);
        return mav;
    }

    /**
     * 删除用户
     * 
     * @param user
     * @return
     */
    @RequestMapping("deleteUser.html")
    public ModelAndView deleteUser(User user) {
        if (user != null && user.getId() != null) {
            this.userService.delete(user);
        }
        return new ModelAndView("redirect:/user/userList.html");
    }

    /**
     * 更新用户信息，属于管理员的操作之一
     * 
     * @param newUser
     * @param roleIds
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("updateUser.html")
    public ModelAndView updateUser(User newUser, Long[] roleIds,
            HttpSession session) throws Exception {
        if (newUser == null || newUser.getId() == null) {
            throw new Exception("服务器出错了~");
        }
        ModelAndView mav = new ModelAndView("redirect:/user/userList.html");
        // 获取旧用户
        User oldUser = this.userService.get(newUser.getId());
        oldUser.setName(newUser.getName());
        oldUser.setSex(newUser.getSex());
        List<Role> roles = this.roleService.getByIds(roleIds);
        oldUser.getRoles().clear();
        if(!roles.contains(this.getDefaultRole(session))) {
            roles.add(this.getDefaultRole(session));
        }
        oldUser.getRoles().addAll(roles);
        this.userService.update(oldUser);
        return mav;
    }

    /**
     * 获得一个密码并且经过加密
     * 
     * @param pwd
     *            要加密的密码
     * @return
     */
    private String getEncodePwd(String pwd) {
        if (pwd == null || "".equals(pwd))
            return DigestUtils.md5Hex(ConfigCenter.getConfig(BaseKey.USER_INIT_PWD).toString());
        return DigestUtils.md5Hex(pwd);
    }
}
