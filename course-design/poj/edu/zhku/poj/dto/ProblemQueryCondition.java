package edu.zhku.poj.dto;

/**
 * 题目查询条件
 * 
 * @author XJQ
 * @since 2013-2-6
 */
public class ProblemQueryCondition {
	private Long exeId; // 题目ID
	private String keyword; // 关键字
	private String level; // 难度
	private String orderRule; // 排序规则

	public Long getExeId() {
		return exeId;
	}

	public void setExeId(Long exeId) {
		this.exeId = exeId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}

	@Override
	public String toString() {
		return "ProblemQueryCondition [exeId=" + exeId + ", keyword=" + keyword + ", level=" + level + ", orderRule=" + orderRule + "]";
	}

}
