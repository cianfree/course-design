package edu.zhku.forum.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.controller.BaseController;
import edu.zhku.forum.domain.Forum;
import edu.zhku.forum.service.ForumService;
import edu.zhku.fr.dao.PageBean;

/**
 * 论坛控制器
 *
 * @author XJQ
 * @since 2013-2-27
 */
@Controller
@RequestMapping("/forumMgr")
public class ForumManagerController extends BaseController {
	@Resource
	private ForumService forumService;
	
	/**
	 * 论坛管理页面
	 * @return
	 */
	@RequestMapping("forumList.html")
	public ModelAndView mgrForumList(Integer currentPage, Integer pageSize) {
		ModelAndView mav = new ModelAndView("forum/mgr/forumList");
		PageBean pb = this.forumService.paging(//
				this.getCurrentPage(currentPage), //
				this.getPageSize(pageSize), //
				this.queryHelper.setClass(Forum.class).setAlias("f")//
								.addOrderProperty("f.position", true));
		mav.addObject("pb", pb);
		return mav;
	}
	
	/**
	 * 删除板块
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteForum.html")
	public ModelAndView deleteForum(Long id) {
		if(id != null) {
			this.forumService.deleteById(id);
		}
		ModelAndView mav = new ModelAndView("redirect:/forumMgr/forumList.html");
		return mav;
	}
	
	/**
	 * 跳转到编辑板块页面
	 * @param id
	 * @return
	 */
	@RequestMapping("editForumUI.html")
	public ModelAndView editForumUI(Long id) {
		ModelAndView mav = new ModelAndView("forum/mgr/editForumUI");
		// 如果传过来的参数不是空的就说明是用来进行修改板块信息的
		if(id != null) {
			mav.addObject("forum", this.forumService.get(id));
		}
		return mav;
	}

	/**
	 * 保存一个板块
	 * @param forum
	 * @return
	 */
	@RequestMapping("saveForum.html")
	public ModelAndView saveForum(Forum forum) {
		ModelAndView mav = new ModelAndView("redirect:/forumMgr/forumList.html");
		this.forumService.save(forum);
		return mav;
	}
	
	/**
	 * 更新板块的信息
	 * @param newForum
	 * @return
	 */
	@RequestMapping("updateForum.html")
	public ModelAndView updateForum(Forum newForum) {
		ModelAndView mav = new ModelAndView("redirect:/forumMgr/forumList.html");
		if(newForum.getId() != null) {
			Forum oldForum = this.forumService.get(newForum.getId());
			oldForum.setName(newForum.getName());
			oldForum.setDescription(newForum.getDescription());
			this.forumService.update(oldForum);
		}
		return mav;
	}
	
	/**
	 * 上移
	 * @param id
	 * @return
	 */
	@RequestMapping("moveUp.html")
	public ModelAndView forumMoveUp(Long id) {
		ModelAndView mav = new ModelAndView("redirect:/forumMgr/forumList.html");
		if(id != null) {
			this.forumService.moveUp(id);
		}
		return mav;
	}
	
	/**
	 * 下移
	 * @param id
	 * @return
	 */
	@RequestMapping("moveDown.html")
	public ModelAndView forumMoveDown(Long id) {
		ModelAndView mav = new ModelAndView("redirect:/forumMgr/forumList.html");
		if(id != null) {
			this.forumService.moveDown(id);
		}
		return mav;
	}
	
}
