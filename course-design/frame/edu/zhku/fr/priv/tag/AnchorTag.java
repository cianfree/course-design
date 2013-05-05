package edu.zhku.fr.priv.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import edu.zhku.fr.Key;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.tag.AbstractTag;

/**
 * 自定义标签，用于辅助实现权限的显示控制
 * 
 * @author XJQ
 * @since 2013-1-18
 */
public class AnchorTag extends AbstractTag {
	protected String href;
	protected String target;
	private boolean isAccess = true;
	private Long pid;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public int doStartTag() throws JspException {
		if (!hasPrivilege()) {
			this.isAccess = false;
			return SKIP_BODY;
		}
		try {
			this.getOut().print("<a" + this.getAttributeString() + ">");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	/**
	 * 验证是否有权限
	 * 
	 * @return
	 */
	private boolean hasPrivilege() {
		User user = this.getCurrentUser();
		if (user == null)
			return false; // 如果没有登录就返回false，表示没有权限
		if (this.pid != null && !"".equals(this.pid)) {
			boolean flag = user.hasPrivilege(this.pid);
			return flag;
		}

		// 根据链接来判断权限
		String action = this.getSimpleHref();

		if (action != null)
			return user.hasPrivilege(action);

		return true;
	}

	/**
	 * href的可能格式如下：<br>
	 * <li>/user/userList.html</li> <li>/user/loginUI.html</li> <li>
	 * user/loginUI.html</li> <li>user/login.html</li> <br>
	 * 无论哪种格式都不合适，需要将这些变成下面的统一格式：<br>
	 * <li>user/login</li>
	 * 
	 * @return
	 */
	private String getSimpleHref() {
		if (this.href == null || "".equals(this.href)) {
			return null;
		}
		String simpleHref = this.href;
		// 除去开头的多余部分
		if (this.href.startsWith("/") || this.href.startsWith("\\")) {
			simpleHref = simpleHref.substring(1);
		}
		// 除去UI后最
		int index = simpleHref.lastIndexOf("UI");
		if (index > -1) {
			simpleHref = simpleHref.substring(0, index);
		} else {
			// 除去后缀.html
			index = simpleHref.lastIndexOf(".");
			if (index > -1) {
				simpleHref = simpleHref.substring(0, index);
			}
		}
		return simpleHref;
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @return 如果没有登录就返回null,否则返回登录的用户
	 */
	private User getCurrentUser() {
		return (User) pageContext.getSession().getAttribute(Key.CURRENT_USER);
	}

	@Override
	protected String getAttributeString() {
		StringBuilder sb = new StringBuilder();
		String sep = "'";
		if (href != null) {
			sep = this.getSeperater(href);
			sb.append(" href=").append(sep).append(href).append(sep);
		}
		if (target != null) {
			sep = this.getSeperater(target);
			sb.append(" target=").append(sep).append(target).append(sep);
		}
		String res = sb.toString();
		return ("".equals(res) ? res : " " + res) + super.getAttributeString();
	}

	@Override
	public int doEndTag() throws JspException {
		if (!this.isAccess)
			return SKIP_BODY;
		try {
			this.getOut().print("</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
