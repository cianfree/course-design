package edu.zhku.forum.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.service.ForumService;
import edu.zhku.fr.dao.DaoRunntimeException;
import edu.zhku.fr.dao.impl.HibernateDaoSupport;

/**
 * 板块业务默认实现类
 *
 * @author XJQ
 * @since 2013-2-27
 */
@Service
@Transactional
public class ForumServiceImpl extends HibernateDaoSupport<Forum> implements ForumService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> queryAll() throws DaoRunntimeException {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " f ORDER BY f.position")
				.list();
	}

	@Override
	public void save(Forum forum) throws DaoRunntimeException {
		super.save(forum);
		forum.setPosition(forum.getId().intValue());
	}
	
	public void moveUp(Long id) {
		// 获取当前对象
		Forum currentForum = this.get(id);
		
		// 获取到当前对象的前一个对象
		Forum preForum = (Forum) this.getSession().createQuery(//
				"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")//
				.setParameter(0, currentForum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();
		// 如果没有前一个对象就不用在进行移动了
		if(preForum == null) {
			return ;
		}
		// 交换position
		int temp = preForum.getPosition();
		preForum.setPosition(currentForum.getPosition());
		currentForum.setPosition(temp);
		// 保存
		this.update(preForum);
		this.update(currentForum);
	}

	public void moveDown(Long id) {
		// 获取当前对象
		Forum currentForum = this.get(id);
		
		// 获取到当前对象的后一个对象
		Forum lastForum = (Forum) this.getSession().createQuery(//
				"FROM Forum f WHERE f.position>?")//
				.setParameter(0, currentForum.getPosition())
				.setMaxResults(1)
				.setFirstResult(0)
				.uniqueResult();
		// 如果没有后一个对象就不用在进行移动了
		if(lastForum == null) {
			return ;
		}
		
		// 交换position
		int temp = lastForum.getPosition();
		lastForum.setPosition(currentForum.getPosition());
		currentForum.setPosition(temp);
		// 保存
		this.update(lastForum);
		this.update(currentForum);
	}
}
