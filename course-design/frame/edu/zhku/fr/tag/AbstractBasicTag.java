package edu.zhku.fr.tag;

public abstract class AbstractBasicTag {
	protected String id;
	protected String name;
	protected String title;
	protected String cssClass;
	protected String style;
	
	/**
	 * 返回属性列表。如id="cian" title="title"
	 * @return
	 */
	protected String getAttributeString() {
		StringBuilder sb = new StringBuilder();
		String sep = "'";
		if(id != null) {
			sep = this.getSeperater(id);
			sb.append(" id='").append(id).append("' ");
		}
		if(name != null) {
			sep = this.getSeperater(name);
			sb.append(" name=").append(sep).append(name).append(sep);
		}
		if(title != null) {
			sep = this.getSeperater(title);
			sb.append(" title=").append(sep).append(title).append(sep);
		}
		if(cssClass != null) {
			sep = this.getSeperater(cssClass);
			sb.append(" class=").append(sep).append(cssClass).append(sep);
		}
		if(style != null) {
			sep = this.getSeperater(style);
			sb.append(" style=").append(sep).append(style).append(sep);
		}
		String res = sb.toString();
		return "".equals(res) ? res : " " + res;
	}
	
	protected String getSeperater(String str) {
		if(str == null) return "";
		int singleSep = str.indexOf("'");
		int doubleSep = str.indexOf("\"");
		if(singleSep > -1) {
			if(doubleSep > -1) {
				if(singleSep > doubleSep) {
					return "\"";
				} else {
					return "'";
				}
			} else {
				return "\"";
			}
		} else {
			return "'";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
