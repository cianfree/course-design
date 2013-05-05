package edu.zhku.forum.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.domain.Reply;
import edu.zhku.forum.domain.Topic;
import edu.zhku.forum.service.ReplyService;
import edu.zhku.fr.dao.impl.HibernateDaoSupport;

/**
 * 板块业务默认实现类
 * 
 * @author XJQ
 * @since 2013-2-27
 */
@Service
@Transactional
public class ReplyServiceImpl extends HibernateDaoSupport<Reply> implements ReplyService {
	@SuppressWarnings("unchecked")
	@Override
	public List<Reply> findByTopic(Topic topic) {
		return this.getSession().createQuery(//
				"FROM "+this.getEntityName()+" r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}

	@Override
	public void save(Reply reply) {
		// 1，保存
		this.getSession().save(reply);

		// 2，维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();

		forum.setArticleCount(forum.getArticleCount() + 1); // 文章数量（主题数+回复数）
		forum.setLastTopic(topic); // 最新回复的主题
		topic.setReplyCount(topic.getReplyCount() + 1); // 回复数量
		topic.setLastReply(reply); // 最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime()); // 最后更新时间（主题的发表时间或最后回复的时间）

		this.getSession().update(topic);
		this.getSession().update(forum);
	}
}
