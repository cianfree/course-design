package edu.zhku.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.utils.Validator;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.UserService;

/**
 * 个人管理控制器
 * 
 * @author XJQ
 * @since 2013-3-14
 */
@Controller
public class PersonalController extends BaseController {
	@Resource
	private UserService userService;
	
	////--------------------------------------------------------------
	/**
	 * 跳转到个人信息编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/personal/personalInfo.html")
	public ModelAndView personalInfoUI() {
		return new ModelAndView("base/personal/editUserInfoUI");
	}

	/**
	 * 保存用户基本编辑的信息
	 * 
	 * @param name
	 *            编辑之后的名字
	 * @param sex
	 *            编辑之后的性别
	 * @return
	 */
	@RequestMapping("/personal/updateUserInfo.html")
	public ModelAndView updateUserInfo(String name, String sex, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 获取当前用户
		User user = this.getCurrentUser(session);
		if (name != null && sex != null) {
			// 如果两个中至少有一个不相等才进行修改
			if (!name.equals(user.getName()) || !sex.equals(user.getSex())) {
				user.setName(name);
				user.setSex(sex);
				this.userService.update(user);
				mav.addObject("message", "恭喜您！更新个人信息成功...");
			}
		}
		mav.setViewName("base/personal/editUserInfoUI");
		return mav;
	}

	/**
	 * 跳转到修改密码界面
	 * 
	 * @return
	 */
	@RequestMapping("/personal/personalPassword.html")
	public ModelAndView personalPwdUI() {
		return new ModelAndView("base/personal/editUserPwdUI");
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPwd
	 *            传入的旧密码
	 * @param newPwd
	 *            传入的新密码
	 * @param rePwd
	 *            传入的重新输入密码
	 * @return
	 */
	@RequestMapping("/personal/updateUserPassword.html")
	public ModelAndView updatePwd(String oldPwd, String newPwd, String rePwd, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 获取当前登录用户
		User user = this.getCurrentUser(session);
		// 检查旧密码
		if (user.getPassword().equals(DigestUtils.md5Hex(oldPwd))) {
			if (Validator.length(newPwd, 5, 25)) {
				String password = DigestUtils.md5Hex(newPwd);
				if (newPwd.equals(rePwd)) {
					if (!user.getPassword().equals(password)) {
						user.setPassword(password);
						this.userService.update(user);
						mav.setViewName("base/personal/updateUserPwdSuccess");
					} else {
						mav.addObject("message", "新密码不能和旧密码一致...");
						mav.setViewName("base/personal/editUserPasswordUI");
					}
				} else {
					mav.addObject("message", "前后密码不一致...");
					mav.setViewName("base/personal/editUserPwdUI");
				}
			} else {
				mav.addObject("message", "新密码长度必须在5-25位之间...");
				mav.setViewName("base/personal/editUserPwdUI");
			}
		} else {
			mav.addObject("message", "原密码输入不正确...");
			mav.setViewName("base/personal/editUserPwdUI");
		}
		return mav;
	}
}
