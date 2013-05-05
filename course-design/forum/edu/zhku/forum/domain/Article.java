package edu.zhku.forum.domain;

import java.util.Date;

import edu.zhku.fr.domain.User;

/**
 * 文章
 * 
 * @author XJQ
 * 
 */
public class Article {
	private Long id;
	private String title;
	private String content; // 内容
	private Date postTime; // 创建时间，发表/回复时间
	
	private User author; // 作者

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
