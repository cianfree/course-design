package edu.zhku.forum.domain;

/**
 * 回复
 * 
 * @author 夏集球
 * 
 */
public class Reply extends Article {
	private Topic topic; // 所属主题

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
