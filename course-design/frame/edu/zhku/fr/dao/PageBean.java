package edu.zhku.fr.dao;

import java.util.List;

/**
 * 用于保存分页的信息，方便对当前页，下一页，末页，首页等进行访问
 *
 * @author XJQ
 * @since 2012-12-9
 */
public class PageBean {

	/**
	 * 指定当前页
	 */
	private int currentPage;   // 指定当前页
	/**
	 * 指定每一页显示多少条记录
	 */
	private int pageSize;  // 指定每一页显示多少条记录

	// ----------------------------------------
	
	/**
	 * 查询结果的总记录数目
	 */
	private int recordCount;   // 查询结果的总记录数目
	/**
	 * 查询结果，保存了本页的查询列表
	 */
	private List<?> recordList;    // 查询结果，保存了本页的查询列表

	// -------------计算-------------------
	/**
	 * 总页数，通过总记录数目和每页显示的记录数计算
	 */
	private int pageCount; // 总页数，通过总记录数目和每页显示的记录数计算
	/**
	 * 页码列表的开始索引以便产生下面的效果：<br/>
	 * <font color=red>首页 4 5 6 <a href="#">7</a> 8 9 10 尾页</font>
	 * <br/>
	 * 其中这里的4就是开始的索引
	 */
	private int beginPageIndex;
	/**
	 * 结束索引，和开始索引对应
	 */
	private int endPageIndex;

	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属性的值
	 * 
	 * @param currentPage 当前页
	 * @param pageSize	每页的大小
	 * @param recordCount	总记录数目
	 * @param recordList	查询的结果
	 */
	public PageBean(int currentPage, int pageSize, int recordCount, List<?> recordList) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.recordList = recordList;

		// 计算总页码
		pageCount = (recordCount + pageSize - 1) / pageSize;

		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1;
			}
		}
	}

	public List<?> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<?> recordList) {
		this.recordList = recordList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

}
