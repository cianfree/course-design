package edu.zhku.poj.domain;

import java.util.Date;

/**
 * 领域模型对象--代表一个题目
 * 
 * @author XJQ
 * @since 2013-1-31
 */
public class Problem {
	private Long id;
	private String name; // 题目名称
	private String description; // 题目描述
	private String author; // 作者，这里为什么使用String类型呢？因为题目创建者可能不是本系统的用户
	private Date postTime; // 提交时间，就是本题目录入本系统的时间
	private String level = Problem.LEVEL_PRIMARY; // 题目等级，有几个级别：初级，中级，高级，使用的是本类的静态常量,默认是初级
	private String inputStyle = ""; // 输入示例
	private String outputStyle = ""; // 输出示例

	public static final String LEVEL_PRIMARY = "初级"; // 等级--初级
	public static final String LEVEL_MIDDLE = "中级"; // 等级--中级
	public static final String LEVEL_ADVANCE = "高级"; // 等级--高级

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getOutputStyle() {
		return outputStyle;
	}

	public void setOutputStyle(String outputStyle) {
		this.outputStyle = outputStyle;
	}

	// TODO 这里的的toString方法在项目发行的时候要删除
	@Override
	public String toString() {
		return "Problem [name=" + name + ", description=" + description + ", author=" + author + ", level=" + level + ", inputStyle=" + inputStyle + ", outputStyle=" + outputStyle
				+ "]";
	}

}
