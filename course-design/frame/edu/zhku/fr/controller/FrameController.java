package edu.zhku.fr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 框架基础控制
 *
 * @author XJQ
 * @since 2013-3-26
 */
@Controller
@RequestMapping("frame")
public class FrameController {

	@RequestMapping("noModuleDeploy.html")
	public String noModuleDeploy() {
		return "frame/noModuleDeploy";
	}
	
	@RequestMapping("noPrivilege.html")
	public String noPrivilege() {
	    return "frame/noPrivilege";
	}
	
	@RequestMapping("error.html")
	public String error() {
	    return "frame/error";
	}
}
