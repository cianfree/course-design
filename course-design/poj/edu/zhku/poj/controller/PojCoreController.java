package edu.zhku.poj.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.controller.BaseController;
import edu.zhku.base.mail.Validator;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.pj.core.SourceHandlerManager;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.domain.Workout;
import edu.zhku.poj.dto.ProblemQueryCondition;
import edu.zhku.poj.service.ProblemService;

/**
 * 处理所有在线评测相关的动作
 *
 * @author XJQ
 * @since 2013-1-16
 */
@Controller
@RequestMapping("/poj")
public class PojCoreController extends BaseController {
	@Resource
	private ProblemService problemService;
	
	@Resource
	protected SourceHandlerManager sourceHandlerManager;
	
	// -------------------------------------------------------------------------------------------------------
	
	/**
	 * 跳转到题目列表页面，同时应该准备相关的显示数据
	 * @return
	 */
	@RequestMapping("problemList.html")
	public ModelAndView problemList(ProblemQueryCondition condition, Integer currentPage, Integer pageSize) {
		ModelAndView mav = new ModelAndView("poj/problemList");
		
		PageBean pb = this.problemService.paging(//
				this.getCurrentPage(currentPage), //
				this.getPageSize(pageSize), //
				this.queryHelper.setClass(Problem.class).setAlias("p")//
						.addCondiction(!Validator.isEmpty(condition) && !Validator.isEmpty(condition.getExeId()), "p.id=?", condition.getExeId())//
						.addCondiction(!Validator.isEmpty(condition) && !Validator.isEmpty(condition.getKeyword()), "p.name LIKE ? or p.description LIKE ?", "%" + condition.getKeyword() + "%", "%" + condition.getKeyword() + "%")//
						.addCondiction(!Validator.isEmpty(condition) && !Validator.isEmpty(condition.getLevel()) && !"null".equals(condition.getLevel()), "p.level=?", condition.getLevel())//
						.addOrderProperty(!Validator.isEmpty(condition) && "DESC".equals(condition.getOrderRule()),"p.postTime", false)//
				);

		// 查询条件，准备数据
		mav.addObject("condition", condition);

		mav.addObject("pb", pb);
		return mav;
	}
	
	/**
	 * 跳转到用户自身的做题列表
	 * @return
	 */
	@RequestMapping("myGrade.html")
	public ModelAndView userProblemList(HttpSession session, Integer currentPage, Integer pageSize) {
		ModelAndView mav = new ModelAndView("poj/myGrade");
		try {
		PageBean pb = this.problemService.paging(//
				this.getCurrentPage(currentPage), //
				this.getPageSize(pageSize), //
				this.queryHelper.setClass(Workout.class).setAlias("w")//
						.addCondiction("w.worker=?", this.getCurrentUser(session))//
				);
		mav.addObject("pb", pb);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 跳转到创建题目页面
	 * 
	 * @param id 如果是空的就说明本次请求是用于创建题目的，否则就是用来修改题目信息用的
	 * 
	 * @return
	 */
	@RequestMapping("editProblemUI.html")
	public ModelAndView editProblemUI(Long id) {
		ModelAndView mav = new ModelAndView("poj/editProblemUI");
		// 如果id不是空的，那么本次请求就是用于更新
        mav.addObject("pro", this.problemService.get(id));
		return  mav;
	}
	
	/**
	 * 添加题目
	 * @param problem
	 * @return
	 */
	@RequestMapping("saveProblem.html")
	public ModelAndView saveProblem(Problem problem, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:/poj/problemList.html");
		
		if(problem != null) {
			problem.setPostTime(new Date());
			if(problem.getAuthor() == null) problem.setAuthor(this.getCurrentUser(session).getName());
			this.problemService.save(problem);
		}
		return mav;
	}
	
	/**
	 * 更新题目基本信息
	 * @param newPro
	 * @return
	 */
	@RequestMapping("updateProblem.html")
	public ModelAndView updateProblem(Problem newPro) {
		ModelAndView mav = new ModelAndView("redirect:/poj/problemList.html");
		
		if(newPro != null && newPro.getId() != null) {
			Problem oldPro = this.problemService.get(newPro.getId());
			oldPro.setName(newPro.getName());
			oldPro.setAuthor(newPro.getAuthor());
			oldPro.setDescription(newPro.getDescription());
			oldPro.setLevel(newPro.getLevel());
			oldPro.setInputStyle(newPro.getInputStyle());
			oldPro.setOutputStyle(newPro.getOutputStyle());
			this.problemService.update(oldPro);
		}
		
		return mav;
	}
	
	/**
	 * 删除题目
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteProblem.html")
	public ModelAndView deleteProblem(Long id) {
		if(id != null) {
			this.problemService.deleteById(id);
		}
		return new ModelAndView("redirect:/poj/problemList.html");
	}
	
	/**
	 * 解题目
	 * @param id
	 * @return
	 */
	@RequestMapping("solveProblemUI.html")
	public ModelAndView solveProblemUI(Long id) {
		if(id == null) {
			return new ModelAndView("redirect:/poj/problemList.html");
		}
		ModelAndView mav = new ModelAndView("poj/solveProblemUI");
		mav.addObject("pro", this.problemService.get(id));
		return mav;
	}
	
	/**
	 * 解题目
	 * @param id
	 * @return
	 */
	@RequestMapping("resolveProblemUI.html")
	public ModelAndView resolveExe(Long id) {
		return solveProblemUI(id);
	}
	
}
