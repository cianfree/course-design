package edu.zhku.fr.tag;


/**
 * 抽象标签，事件相关的标签
 *
 * @author XJQ
 * @since 2013-1-18
 */
public abstract class AbstractEventTag extends AbstractBasicTag {
	protected String onmouseup;
	protected String onmouseover;
	protected String onmouseout;
	protected String onmousemove;
	protected String onmousedown;
	protected String onkeyup;
	protected String onkeypress;
	protected String onkeydown;
	protected String onhelp;
	protected String onfocus;
	protected String ondblclick;
	protected String onclick;
	protected String onblur;

	@Override
	protected String getAttributeString() {
		StringBuilder sb = new StringBuilder();
		String sep = "'";
		if(onmouseup != null) {
			sep = this.getSeperater(onmouseup);
			sb.append(" onmouseup=").append(sep).append(onmouseup).append(sep);
		}
		if(onmouseover != null) {
			sep = this.getSeperater(onmouseover);
			sb.append(" onmouseover=").append(sep).append(onmouseover).append(sep);
		}
		if(onmouseout != null) {
			sep = this.getSeperater(onmouseout);
			sb.append(" onmouseout=").append(sep).append(onmouseout).append(sep);
		}
		if(onmousemove != null) {
			sep = this.getSeperater(onmousemove);
			sb.append(" onmousemove=").append(sep).append(onmousemove).append(sep);
		}
		if(onmousedown != null) {
			sep = this.getSeperater(onmousedown);
			sb.append(" onmousedown=").append(sep).append(onmousedown).append(sep);
		}
		if(onkeyup != null) {
			sep = this.getSeperater(onkeyup);
			sb.append(" onkeyup=").append(sep).append(onkeyup).append(sep);
		}
		if(onkeypress != null) {
			sep = this.getSeperater(onkeypress);
			sb.append(" onkeypress=").append(sep).append(onkeypress).append(sep);
		}
		if(onkeydown != null) {
			sep = this.getSeperater(onkeydown);
			sb.append(" onkeydown=").append(sep).append(onkeydown).append(sep);
		}
		if(onhelp != null) {
			sep = this.getSeperater(onhelp);
			sb.append(" onhelp=").append(sep).append(onhelp).append(sep);
		}
		if(onfocus != null) {
			sep = this.getSeperater(onfocus);
			sb.append(" onfocus=").append(sep).append(onfocus).append(sep);
		}
		if(ondblclick != null) {
			sep = this.getSeperater(ondblclick);
			sb.append(" ondblclick=").append(sep).append(ondblclick).append(sep);
		}
		if(onclick != null) {
			sep = this.getSeperater(onclick);
			sb.append(" onclick=").append(sep).append(onclick).append(sep);
		}
		if(onblur != null) {
			sep = this.getSeperater(onblur);
			sb.append(" onblur=").append(sep).append(onblur).append(sep);
		}
		String res = sb.toString();
		return ("".equals(res) ? res : " " + res) + super.getAttributeString();
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnhelp() {
		return onhelp;
	}

	public void setOnhelp(String onhelp) {
		this.onhelp = onhelp;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

}
