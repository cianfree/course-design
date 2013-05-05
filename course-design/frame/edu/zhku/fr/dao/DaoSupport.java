package edu.zhku.fr.dao;

import java.util.List;

/**
 * 通用DAO数据访问接口支持，制定接口，其实现可以使用Hibernate,Ibatis(MyBatis)或其他方式实现
 * 
 * @author XJQ
 * @since 2012-12-9
 * @param<T> 泛型参数，指定具体的实体参数类型
 */
public interface DaoSupport<T> {

	/**
	 * 完整保存一个实体对象，同时保存对象之间的关系，如一对多，多对一，多对多等等
	 * 
	 * @param entity 要保存的实体对象
	 * @exception DaoRunntimeException 运行时异常，如果执行出错就会抛出这个异常
	 */
	public void save(T entity) throws DaoRunntimeException;
	
	public void saveOrUpdate(T entity) throws DaoRunntimeException;

	/**
	 * 删除一个实体，并且级联删除与该实体有关的其他关联关系
	 * 
	 * @param entity 要删除的实体对象
	 * @throws DaoRunntimeException 一旦出现任何错误将抛出异常
	 */
	public void delete(T entity) throws DaoRunntimeException;
	
	public void deleteById(Long id) throws DaoRunntimeException;
	
	/**
	 * 删除本表的所有数据，级联删除
	 */
	public void clearModule();
	
	/**
	 * 更新一个实体对象，同时更新关联关系对象
	 * 
	 * @param entity 要更新的实体对象
	 * @throws DaoRunntimeException	一旦出错抛出异常
	 */
	public void update(T entity) throws DaoRunntimeException;
	
	/**
	 * 根据实体的唯一标识符ID获得一个实体对象
	 * 
	 * @param id 实体的唯一标识符ID
	 * @return 如果存在就返回一个实体对象，否则就放回null
	 * @throws DaoRunntimeException 运行时异常
	 */
	public T get(Long id) throws DaoRunntimeException;
	
	/**
	 * 根据给定的实体的id获取所有对象
	 * 
	 * @param ids	对象的唯一标志符集合
	 * @return 返回一组对象，如果查询结果为空也不会返回null，而是返回一个list，该list的长度是0
	 * @throws DaoRunntimeException 运行时异常
	 */
	public List<T> getByIds(Long ... ids) throws DaoRunntimeException;
	
	/**
	 * 查询所有对象，一般很少会使用，只是在某些特殊的对象的时候需要
	 * 
	 * @return 返回T类型的所有对象
	 * @throws DaoRunntimeException 运行时异常
	 */
	public List<T> queryAll() throws DaoRunntimeException;
	
	/**
	 * 公共的分页查询方法,带条件的查询，可以设置相关的where查询条件和order排序语句
	 * 
	 * @param pageIndex	当前页
	 * @param pageSize	每页的大小
	 * @param queryHelper	查询帮助，提供查询条件查询和排序
	 * @return
	 * 		一旦传入的参数不正确就抛出参数不正确的异常，如果是其他错误就咆啸虎Dao数据访问异常<br/>
	 * 		以下情况认为参数不正确：<br/>
	 * 		<ul>
	 * 			<li>pageIndex<=0</li>
	 * 			<li>pageSize<=0</li>
	 * 			<li>queryHelper==null</li>
	 * 		</ul>
	 * @throws DaoRunntimeException
	 * @throws {@link IllegalArgumentException}
	 */
	public PageBean paging(int pageIndex, int pageSize, QueryHelper queryHelper) throws DaoRunntimeException, IllegalArgumentException;
}
