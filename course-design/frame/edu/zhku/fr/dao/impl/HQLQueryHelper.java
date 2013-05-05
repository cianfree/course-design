package edu.zhku.fr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.zhku.fr.dao.QueryHelper;

/**
 * 查询辅助类，用于拼接HQL语句的实现类，该类实现了IQueryHelper接口
 * 
 * @author XJQ
 * @since 2012-12-10
 */
@Component
public class HQLQueryHelper implements QueryHelper {

	/**
	 * 记录from 子句
	 */
	private String fromClause;
	/**
	 * 记录where子句，默认是为空：""，即没有where子句
	 */
	private String whereClause = "";
	/**
	 * 记录order by 子句，默认是空：""，即不用进行排序
	 */
	private String orderByClause = "";
	/**
	 * 记录查询参数，一个问号对应一个参数
	 */
	private List<Object> params = new ArrayList<Object>(); // 查询参数

	public HQLQueryHelper(Class<?> clazz, String alias) {
		this.fromClause = new StringBuffer("FROM ")//
				.append(clazz.getSimpleName())//
				.append(" ")//
				.append(alias == null ? "" : alias)//
				.toString();
	}
	
	private boolean flag = true;
	
	public HQLQueryHelper(){}
	
	/**
	 * 清空之前的数据
	 */
	private void clear() {
		if(!flag) {
			fromClause = null;
			whereClause = "";
			orderByClause = "";
			params.clear();
			flag = true;
		} 
	}
	
	@Override
	public QueryHelper setClass(Class<?> clazz) {
		clear();
		this.fromClause = new StringBuffer("FROM ")//
		.append(clazz.getSimpleName())//
		.append(" ")//
		.toString();
		return this;
	}
	
	@Override
	public QueryHelper setAlias(String alias) {
		clear();
		this.fromClause += (alias == null ? "" : alias);
		return this;
	}

	public QueryHelper addCondiction(String condiction, Object... params) {
		// 第一次进行条件添加
		if ("".equals(this.whereClause)) {
			this.whereClause = new StringBuffer(" WHERE ")//
					.append(condiction)//
					.toString();
		} else {
			this.whereClause += new StringBuffer(" AND ")//
					.append(condiction)//
					.toString();
		}
		// 添加参数
		if (params != null) {
			for (Object param : params) {
				this.params.add(param);
			}
		}
		return this;
	}

	public QueryHelper addCondiction(boolean append, String condiction,
			Object... params) {
		if (append) {
			return this.addCondiction(condiction, params);
		}
		return this;
	}

	public QueryHelper addOrderProperty(String propertyName, boolean asc) {
		if (this.orderByClause.length() == 0) {
			this.orderByClause = new StringBuffer(" ORDER BY ")//
					.append(propertyName)//
					.append(asc ? " ASC" : " DESC")//
					.toString();
		} else {
			this.orderByClause += new StringBuffer(", ")//
					.append(propertyName)//
					.append(asc ? " ASC" : " DESC")//
					.toString();
		}
		return this;
	}

	public QueryHelper addOrderProperty(boolean append, String propertyName,
			boolean asc) {
		if (append) {
			return this.addOrderProperty(propertyName, asc);
		}
		return this;
	}

	public String getListQueryString() {
		flag = false;
		return new StringBuffer(this.fromClause)//
				.append(this.whereClause)//
				.append(this.orderByClause)//
				.toString();
	}

	public String getCountQueryString() {
		flag = false;
		return new StringBuffer("SELECT COUNT(*) ")//
				.append(this.fromClause)//
				.append(this.whereClause)//
				.toString();
	}

	public List<Object> getParameters() {
		return params;
	}

}
