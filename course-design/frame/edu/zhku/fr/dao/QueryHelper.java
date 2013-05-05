package edu.zhku.fr.dao;

import java.util.List;

/**
 * 查询辅助类，用于拼接语句
 * 
 * @author XJQ
 * @since 2012-12-09
 * 
 */
public interface QueryHelper {
	// 设置针对谁的查询
	QueryHelper setClass(Class<?> clazz);

	// 设置别名
	QueryHelper setAlias(String alias);
	/**
	 * 给where子句添加条件同时指定参数
	 * 
	 * @param condiction	条件，如username like ? 
	 * @param params	对应的参数
	 * @return	返回本对象，用于支持链式调用
	 */
	public QueryHelper addCondiction(String condiction, Object... params);
	/**
	 * 如果第一个参数为true，则添加该条件到Where子句中，前面可能就是一个boolean表达式，只有满足一定条件的时候才会添加查询条件
	 * 
	 * @param append	是否要添加查询条件
	 * @param condiction	要添加的查询条件
	 * @param params	对应查询占位符的参数
	 * @return	返回本对象，用于支持链式调用
	 */
	public QueryHelper addCondiction(boolean append, String condiction, Object... params);

	/**
	 * 拼接order by子句
	 * 
	 * @param propertyName	要进行排序的属性名称，当然是指数据库中的字段名称
	 * @param asc	ASC为true表示升序排列，false表示降序排列[DESC]
	 * @return 返回本对象，用于支持链式调用
	 */
	public QueryHelper addOrderProperty(String propertyName, boolean asc);
	/**
	 * 如果append为true，则拼接
	 * 
	 * @param append	指定是否要添加排序属性，如果条件判断为true就添加
	 * @param propertyName	要排序的属性名
	 * @param asc	true表示升序，false表示降序
	 * @return	返回本对象，用于支持链式调用
	 */
	public QueryHelper addOrderProperty(boolean append, String propertyName, boolean asc);
	/**
	 * 获取生成的用于查询数据列表的查询语句
	 * 
	 * @return 返回查询列表的查询语句，可能是HQL，也可能是SQL语句，具体看是怎么实现的
	 */
	public String getListQueryString();
	/**
	 * 获取用于查询数据列表的数量查询语句
	 * 
	 * @return 返回查询语句
	 */
	public String getCountQueryString();
	/**
	 * 获取查询参数
	 * 
	 * @return
	 */
	public List<Object> getParameters();
}
