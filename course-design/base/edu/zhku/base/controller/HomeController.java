package edu.zhku.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.zhku.base.BaseKey;

/**
 * 主页相关控制，包括登录，注销，相关
 *
 * @author XJQ
 * @since 2013-1-10
 */
@Controller
@SessionAttributes(value={BaseKey.CURRENT_USER})
public class HomeController {

	@RequestMapping("/home.html")
	public String home() {
		return "base/home/home";
	}
	
	@RequestMapping("/index.html")
	public String index() {
		return "base/home/home";
	}
	
	@RequestMapping("/noPrivilege.html")
	public String noPrivilege() {
		return "base/home/noPrivilege";
	}
	
	// ---------------------------------------------------------
	@RequestMapping("home/introduction.html")
	public String introduction() {
		return "base/home/introduction";
	}
	
}
