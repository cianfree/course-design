package edu.zhku.forum.service;

import java.util.List;

import edu.zhku.forum.domain.Reply;
import edu.zhku.forum.domain.Topic;
import edu.zhku.fr.dao.DaoSupport;

/**
 * 回复业务接口定义
 *
 * @author XJQ
 * @since 2013-2-27
 */
public interface ReplyService extends DaoSupport<Reply> {

	/**
	 * 通过topic查询reply
	 * @param topic
	 * @return
	 */
	List<Reply> findByTopic(Topic topic);

}
