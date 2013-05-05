package edu.zhku.forum.dto;

/**
 * 板块主题列表的条件DTO
 * 
 * @author XJQ
 * @since 2013-2-28
 */
public class ForumShowCondition {
	private Long id;
	private Integer viewType = 0; // 显示类型
	private Integer orderBy = 0; // 排序规则
	private Boolean reverse = true; // 升序还是降序，false表示升序，true表示降序
	private Integer currentPage; // 当前页
	private Integer pageSize; // 每页显示条目

	@Override
	public String toString() {
		return "ForumShowCondition [id=" + id + ", viewType=" + viewType + ", orderBy=" + orderBy + ", reverse=" + reverse + ", currentPage=" + currentPage + ", pageSize="
				+ pageSize + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getViewType() {
		return viewType;
	}

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
