package edu.zhku.forum.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.controller.BaseController;
import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.domain.Reply;
import edu.zhku.forum.domain.Topic;
import edu.zhku.forum.dto.ForumShowCondition;
import edu.zhku.forum.service.ForumService;
import edu.zhku.forum.service.ReplyService;
import edu.zhku.forum.service.TopicService;
import edu.zhku.fr.dao.PageBean;

/**
 * 论坛使用的相关控制
 *
 * @author XJQ
 * @since 2013-2-28
 */
@Controller
@RequestMapping("/forum")
public class ForumController extends BaseController {
	@Resource
	private ForumService forumService;
	
	@Resource
	private TopicService topicService;
	
	@Resource
	private ReplyService replyService;
	
	// ----------------------------------------------------------------------------
	
	
	/**
	 * 查询所有的板块
	 * @return
	 */
	@RequestMapping("forumList.html")
	public ModelAndView forumList() {
		ModelAndView mav = new ModelAndView("forum/use/forumList");
		mav.addObject("forums", this.forumService.queryAll());
		return mav;
	}
	
	/**
	 * 显示单个板块下面的所有主题列表
	 * @param id
	 * @return
	 */
	@RequestMapping("forumShow.html")
	public ModelAndView forumShow(ForumShowCondition condition) {
		ModelAndView mav = new ModelAndView("forum/use/forumShow");
		Forum forum = this.forumService.get(condition.getId());
		mav.addObject("forum", forum);
		
		PageBean pb = null;
		pb = this.topicService.paging(
				this.getCurrentPage(condition.getCurrentPage()),//
				this.getPageSize(condition.getPageSize()),//
				this.queryHelper.setClass(Topic.class).setAlias("t")//
								.addCondiction("t.forum=?", forum)//
								.addCondiction(condition.getViewType() == 1, "t.type=?", Topic.TYPE_BEST)//
								.addOrderProperty((condition.getOrderBy() == 1), "t.lastUpdateTime", condition.getReverse()) // 1
								.addOrderProperty((condition.getOrderBy() == 2), "t.postTime", condition.getReverse()) // 2
								.addOrderProperty((condition.getOrderBy() == 3), "t.replyCount", condition.getReverse()) // 3
								.addOrderProperty((condition.getOrderBy() == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
								.addOrderProperty((condition.getOrderBy() == 0), "t.lastUpdateTime", false) // 0
				);
		
		mav.addObject("pb", pb);
		mav.addObject("condition", condition);
		return mav;
		
	}
	
	/**
	 * 跳转到主题编辑页面
	 * @param forumId 所属板块id
	 * @param topicId 
	 * @return
	 */
	@RequestMapping("editTopicUI.html")
	public ModelAndView editTopicUI(Long forumId, Long topicId) {
		ModelAndView mav = new ModelAndView("forum/use/editTopicUI");
		if(forumId != null) {
			mav.addObject("forum", this.forumService.get(forumId));
		}
		if(topicId != null) {
			mav.addObject("topic", this.topicService.get(topicId));
		}
		return mav;
	}

	/**
	 * 更新主题
	 * @param newTopic
	 * @return
	 */
	@RequestMapping("updateTopic.html")
	public ModelAndView updateTopic(Topic newTopic) {
		Topic topic = this.topicService.get(newTopic.getId());
		topic.setTitle(newTopic.getTitle());
		topic.setContent(newTopic.getContent());
		this.topicService.update(topic);
		return new ModelAndView("redirect:/forum/topicShow.html?id=" + newTopic.getId());
	}
	
	/**
	 * 添加新的主题
	 * @param topic
	 * @param forumId
	 * @param session
	 * @return
	 */
	@RequestMapping("saveTopic.html")
	public ModelAndView saveTopic(Topic topic, Long forumId, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
		
		topic.setAuthor(this.getCurrentUser(session));
		topic.setForum(this.forumService.get(forumId));
		topic.setPostTime(new Date());
		this.topicService.save(topic);
		
		return mav;
	}
	
	/**
	 * 删除主题帖子
	 * @param id
	 * @param forumId
	 * @return
	 */
	@RequestMapping("deleteTopic.html")
	public ModelAndView deleteTopic(Long id, Long forumId) {
		if(id != null) {
			this.topicService.deleteById(id);
		}
		return new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
	}
	
	/**
	 * 列出主题下面的所有回复
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("topicShow.html")
	public ModelAndView topicShow(Long id, Integer currentPage, Integer pageSize) {
		ModelAndView mav = new ModelAndView("forum/use/topicShow");
		
		// 需要准备两个数据，在页面中需要Topic和Replies
		// 准备Topic 
		if(id != null) {
			mav.addObject("topic", this.topicService.get(id));
			// 准备该Topic 的回复信息
			PageBean pb = this.replyService.paging(//
					this.getCurrentPage(currentPage),//
					this.getPageSize(pageSize),//
					this.queryHelper.setClass(Reply.class).setAlias("r")//
									.addCondiction("r.topic.id=?", id)//
									.addOrderProperty("r.postTime", true)//
					);
			mav.addObject("pb", pb);
		}
		return mav;
	}
	
	/**
	 * 跳转到回复主题页面
	 * @param id
	 * @return
	 */
	@RequestMapping("replyTopicUI.html")
	public ModelAndView replyTopicUI(Long topicId, Long replyId) {
		ModelAndView mav = new ModelAndView("forum/use/replyTopicUI");
		mav.addObject("topic", this.topicService.get(topicId));
		mav.addObject("reply", this.replyService.get(replyId));
		return mav;
	}
	
	/**
	 * 修改回复
	 * @param newReply
	 * @param topicId
	 * @return
	 */
	@RequestMapping("updateReply.html")
	public ModelAndView updateReply(Reply newReply, Long topicId) {
		ModelAndView mav = new ModelAndView("redirect:/forum/topicShow.html?id=" + topicId);
		Reply reply = this.replyService.get(newReply.getId());
		if(reply != null) {
			reply.setContent(newReply.getContent());
			this.replyService.saveOrUpdate(reply);
		}
		return mav;
	}
	
	/**
	 * 添加给定topic的回复
	 * @param topicId
	 * @param reply
	 * @return
	 */
	@RequestMapping("replyTopic.html")
	public ModelAndView replyTopic(Long topicId, Reply reply, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/forum/topicShow.html?id=" + topicId);
		if(topicId != null) {
			reply.setTopic(this.topicService.get(topicId));
			reply.setAuthor(this.getCurrentUser(session));
			reply.setPostTime(new Date());
			
			this.replyService.save(reply);
		}
		return mav;
	}
	
	/**
	 * 移动本主题到其他板块
	 * @param id
	 * @return
	 */
	@RequestMapping("moveTopicUI.html")
	public ModelAndView moveTopicUI(Long id) {
		ModelAndView mav = new ModelAndView("forum/use/moveTopicUI");
		if(id != null) {
			mav.addObject("topic", this.topicService.get(id));
			mav.addObject("forumList", this.forumService.queryAll());
		}
		return mav;
	}
	
	/**
	 * 移动主题
	 * @param id
	 * @param forumId
	 * @return
	 */
	@RequestMapping("moveTopic.html")
	public ModelAndView moveTopic(Long id, Long forumId) {
		if(id != null) {
			Topic topic = this.topicService.get(id);
			Forum forum = this.forumService.get(forumId);
			if(forum != null) {
				this.topicService.move(topic, forum);
			}
		}
		return new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
	}
	
	/**
	 * 将本主题置为精华帖
	 * @param id
	 * @param forumId
	 * @return
	 */
	@RequestMapping("creamTopic.html")
	public ModelAndView creamTopic(Long id, Long forumId) {
		if(id != null) {
			Topic topic = this.topicService.get(id);
			topic.setType(Topic.TYPE_BEST);
			this.topicService.update(topic);
		}
		return new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
	}
	
	/**
	 * 将本主题帖子置为置顶帖
	 * @param id
	 * @param forumId
	 * @return
	 */
	@RequestMapping("topTopic.html")
	public ModelAndView topTopic(Long id, Long forumId) {
		if(id != null) {
			Topic topic = this.topicService.get(id);
			topic.setType(Topic.TYPE_TOP);
			this.topicService.update(topic);
		}
		return new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
	}
	
	/**
	 * 将本主题帖子置为普通帖子
	 * @param id
	 * @param forumId
	 * @return
	 */
	@RequestMapping("ordinaryTopic.html")
	public ModelAndView ordinaryTopic(Long id, Long forumId) {
		if(id != null) {
			Topic topic = this.topicService.get(id);
			topic.setType(Topic.TYPE_NORMAL);
			this.topicService.update(topic);
		}
		return new ModelAndView("redirect:/forum/forumShow.html?id=" + forumId);
	}
}
