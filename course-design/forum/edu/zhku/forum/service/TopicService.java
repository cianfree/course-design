package edu.zhku.forum.service;

import java.util.List;

import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.domain.Topic;
import edu.zhku.fr.dao.DaoSupport;

/**
 * 主题业务接口定义
 *
 * @author XJQ
 * @since 2013-2-27
 */
public interface TopicService extends DaoSupport<Topic> {
	/**
	 * 获取给定板块下面的主题列表，并排序，如置顶帖，最后更新帖，精华帖
	 * @param forum
	 * @return
	 */
	List<Topic> findByForum(Forum forum);

	/**
	 * 移动到Forum板块，还需要判断呀移动的主题是否是当前Forum的lastTopic，如果是，还要同时
	 * 更新当前Forum中的lastTopic
	 * @param topic
	 * @param toForum
	 */
	void move(Topic topic, Forum toForum);
}
