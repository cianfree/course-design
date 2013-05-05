package edu.zhku.fr.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.DaoRunntimeException;
import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.dao.QueryHelper;

/**
 * 使用Hibernate实现的DaoSupport
 * 
 * @author XJQ
 * @since 2012-12-10
 * @param <T>
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class HibernateDaoSupport<T> implements DaoSupport<T> {
	/**
	 * 保存实际泛型参数的类型，用于查询，将在构造函数中进行初始化
	 */
	protected Class<?> clazz = null;

	/**
	 * SessionFactory是Hibernate中十分重要的对象，数据操作都要借助这个对象
	 */
	protected SessionFactory sessionFactory = null;

	/**
	 * 提供一个方式设置sessionFactory，可以通过Spring外部的属性进行注入
	 * 
	 * @param sessionFactory
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 无参构造函数，主要是为了获取clazz的实际类型，即泛型参数的实际类型
	 */
	public HibernateDaoSupport() {
		this.clazz = (Class<?>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 获取实体类的名称，用于进行HQL查询
	 * 
	 * @return 返回实体类的类名，如User
	 */
	protected String getEntityName() {
		return this.clazz.getSimpleName();
	}

	/**
	 * 获取Hibernate的Session对象以便进行实体的数据访问操作
	 * 
	 * @return 返回Hibernate的Session对象
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) throws DaoRunntimeException {
	    if(entity != null) this.getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) throws DaoRunntimeException {
		if(entity != null) this.getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) throws DaoRunntimeException {
		if(entity != null) this.getSession().delete(entity);
	}
	
	@Override
	public void deleteById(Long id) throws DaoRunntimeException {
		if(id != null) this.delete(this.get(id));
	}
	
	@Override
	public void clearModule() {
		this.getSession().createQuery(//
				"DELETE FROM " + this.getEntityName())//
				.executeUpdate();
	}

	@Override
	public void update(T entity) throws DaoRunntimeException {
		if(entity != null) this.getSession().update(entity);
	}

	@Override
	public T get(Long id) throws DaoRunntimeException {
		if(id == null) return null;
		return (T) this.getSession().get(clazz, id);
	}

	@Override
	public List<T> getByIds(Long... ids) throws DaoRunntimeException {
	    if(ids != null && ids.length > 0)
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " WHERE id IN (:ids)")//
				.setParameterList("ids", ids)//
				.list();
	    return new ArrayList<T>();
	}

	@Override
	public List<T> queryAll() throws DaoRunntimeException {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName())//
				.list();
	}

	@Override
	public PageBean paging(int pageIndex, int pageSize,
			QueryHelper queryHelper) throws DaoRunntimeException,
			IllegalArgumentException {
		// 参数检查
		if(pageIndex <=0 ||pageSize<=0 || queryHelper == null) {
			throw new IllegalArgumentException("IllegalArgumentException,Please check the arguments!\npageIndex: required >0, actually " + pageIndex + "\npageSize: required >0, actually " + pageSize + "\nqueryHelper: required not null, actually " + queryHelper);
		}
		 
		// 1，获取参数列表
		List<Object> params = queryHelper.getParameters();
		
		// 2, 创建查询总记录数目的查询对象
		Query countQuery = this.getSession().createQuery(queryHelper.getCountQueryString());
		
		if(params != null) {
			for(int i=0; i<params.size(); ++i) {
				countQuery.setParameter(i, params.get(i));
			}
		}
		int recordCount = ((Long)countQuery.uniqueResult()).intValue();
		
		// 3，创建查询结果的查询对象
		Query listQuery = this.getSession().createQuery(queryHelper.getListQueryString());
		if(params != null) {
			for(int i=0; i<params.size(); ++i) {
				listQuery.setParameter(i, params.get(i));
			}
		}
		// 设置分页查询信息
		listQuery.setFirstResult((pageIndex - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List<T> recordList = listQuery.list();
		
		return new PageBean(pageIndex, pageSize, recordCount, recordList);
	}

}
