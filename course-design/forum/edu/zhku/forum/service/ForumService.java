package edu.zhku.forum.service;

import edu.zhku.forum.domain.Forum;
import edu.zhku.fr.dao.DaoSupport;

/**
 * 板块业务接口定义
 *
 * @author XJQ
 * @since 2013-2-27
 */
public interface ForumService extends DaoSupport<Forum> {
	/**
	 * 上移
	 * @param id
	 */
	void moveUp(Long id);

	/**
	 * 下移
	 * @param id
	 */
	void moveDown(Long id);
}
