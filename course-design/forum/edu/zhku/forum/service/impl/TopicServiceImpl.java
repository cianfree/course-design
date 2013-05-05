package edu.zhku.forum.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.domain.Topic;
import edu.zhku.forum.service.TopicService;
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
public class TopicServiceImpl extends HibernateDaoSupport<Topic> implements TopicService {

	@Override
	public void save(Topic topic) throws DaoRunntimeException {
		// 1，设置属性并保存
		topic.setType(Topic.TYPE_NORMAL); // 默认为普通帖
		topic.setReplyCount(0); // 回复数
		topic.setLastReply(null); // 最后回复
		topic.setLastUpdateTime(topic.getPostTime()); // 最后更新时间
		this.getSession().save(topic); // 保存

		// 2，维护相关的特殊属性
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount() + 1); // 主题数量
		forum.setArticleCount(forum.getArticleCount() + 1);// 文章数量（主题数+回复数）
		forum.setLastTopic(topic); // 最后发表的主题
		this.getSession().update(forum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> findByForum(Forum forum) {
		return this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Override
	public void move(Topic topic, Forum toForum) {
		// 1. 获取当前所属的forum-->currentForum
		Forum currentForum = topic.getForum();
		// 更新topicCount和articleCount
		currentForum.setArticleCount(currentForum.getArticleCount() - topic.getReplyCount() - 1);
		currentForum.setTopicCount(currentForum.getTopicCount() - 1);

		// 2. 判断当前板块currentForum 的 lastTopic是否是要修改板块的Topic，如果是
		// 就需要修改currentForum的lastTopic
		if (currentForum.getLastTopic() != null && currentForum.getLastTopic().getId() == topic.getId()) {
			// 如果currentForum的lastTopic就是topic，那么就需要更新currentForum
			// 找到currentForum的lastTopic
			Topic lastTopic = (Topic) this.getSession().createQuery(//
					"FROM "+this.getEntityName()+" t WHERE t.forum=? AND t.lastUpdateTime<? ORDER BY t.lastUpdateTime DESC")//
					.setParameter(0, currentForum)//
					.setParameter(1, topic.getLastUpdateTime())//
					.setFirstResult(0)//
					.setMaxResults(1)//
					.uniqueResult();
			// 更改currentForum的lastTopic
			currentForum.setLastTopic(lastTopic);
		}
		// 更新到数据库，这步可以不用写，因为在session缓存中，会自动同步更新到数据库
		this.getSession().update(currentForum);

		// 3. 重新设置板块
		topic.setForum(toForum);

		// 4. 更新toForum中的lastTopic
		if (toForum.getLastTopic() == null || toForum.getLastTopic().getLastUpdateTime().getTime() < topic.getLastUpdateTime().getTime()) {
			// 如果为空就直接设置为当前的topic，或者，当前topic较新，就更新为当前topic为toForum的lastTopic
			toForum.setLastTopic(topic);
		}
		toForum.setArticleCount(toForum.getArticleCount() + topic.getReplyCount() + 1);
		toForum.setTopicCount(toForum.getTopicCount() + 1);
		this.getSession().update(toForum);
		// 5. 更新对象
		this.update(topic);
	}

	/**
	 * 删除一个帖子，需要做特殊处理，首先得更新Forum表中的最后更新帖子为上一个
	 */
	@Override
	public void delete(Topic topic) throws DaoRunntimeException {

		/**
		 * 首先获取currentForum对象，看看currentForum.lastTopic是不是topic
		 * 1、是-->到数据库中查找比topic更晚一点的lastTopic,作为新的currentForum的lastTopic
		 * 2、否-->直接把topic级联删除 1) 更新维护的数据 -->
		 * currentForum.setArticleCount(currentForum.getArticleCount() -
		 * topic.getReplyCount() - 1); -->
		 * currentForum.setTopicCount(currentForum.getTopicCount() - 1);
		 */
		// 获取topic当前的Forum-->currentForum
		Forum currentForum = topic.getForum();
		// 更新维护数据
		currentForum.setArticleCount(currentForum.getArticleCount() - topic.getReplyCount() - 1);
		currentForum.setTopicCount(currentForum.getTopicCount() - 1);

		if (currentForum.getLastTopic().getId() == topic.getId()) {
			// 如果当前的topic就是currentForum的lastTopic-->级联删除topic
			// 到数据库中查找比topic更晚一点的lastTopic,作为新的currentForum的lastTopic
			Topic lastTopic = (Topic) this.getSession().createQuery(//
					"FROM "+this.getEntityName()+" t WHERE t.forum=? AND t.lastUpdateTime<? ORDER BY t.lastUpdateTime DESC")//
					.setParameter(1, topic.getLastUpdateTime())//
					.setFirstResult(0)//
					.setMaxResults(1)//
					.uniqueResult();
			currentForum.setLastTopic(lastTopic);
		}
		this.getSession().update(currentForum);
		this.getSession().delete(topic);
	}
}
