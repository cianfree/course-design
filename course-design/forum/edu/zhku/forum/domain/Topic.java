package edu.zhku.forum.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 论坛主题
 * 
 * @author 夏集球
 * 
 */
public class Topic extends Article {
	/** 普通帖 */
	public static final int TYPE_NORMAL = 0;

	/** 精华帖 */
	public static final int TYPE_BEST = 1;

	/** 置顶帖 */
	public static final int TYPE_TOP = 2;
	
	// 关联关系字段
	private Forum forum; // 所属板块
	private Set<Reply> replies = new HashSet<Reply>(); // 所有回复
	private Reply lastReply; // 最后一次回复
	
	// 特殊字段，用于解决特殊问题
	private int type = Topic.TYPE_NORMAL; // 帖子类型，1为置顶帖，2为精华帖，3为普通帖
	private int replyCount; // 回复数
	private Date lastUpdateTime; // 最后更新时间

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	public Reply getLastReply() {
		return lastReply;
	}

	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}

}
