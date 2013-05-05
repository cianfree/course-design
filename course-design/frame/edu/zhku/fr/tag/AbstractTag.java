package edu.zhku.fr.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


public abstract class AbstractTag extends AbstractEventTag implements Tag {
	protected PageContext pageContext;

	@Override
	public void release() {
	}

	protected JspWriter getOut() {
		return this.pageContext.getOut();
	}
	
	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	protected Tag parent;
	
	@Override
	public void setParent(Tag parent) {
		this.parent = parent;
	}
	
	@Override
	public Tag getParent() {
		return this.parent;
	}
	
	@Override
	public int doStartTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	@Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

}
