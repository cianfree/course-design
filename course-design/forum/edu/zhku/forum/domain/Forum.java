package edu.zhku.forum.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 板块类
 * 
 * @author 夏集球
 * 
 */
public class Forum {
	private Long id;
	private String name;
	private String description;
	private int position;
	private int topicCount; // 该板块下主题的数量
	private int articleCount; // 该板块下文章的数量，即主题数量+回复数量

	private Topic lastTopic; // 最新创建的主题帖子
	private Set<Topic> topics = new HashSet<Topic>(); // 该主题下的所有主题帖

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public Topic getLastTopic() {
		return lastTopic;
	}

	public void setLastTopic(Topic lastTopic) {
		this.lastTopic = lastTopic;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

}
