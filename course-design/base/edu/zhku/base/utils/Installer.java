package edu.zhku.base.utils;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;

/**
 * 安装程序
 *
 * @author XJQ
 * @since 2013-1-19
 */
@Component("installer")
public class Installer {
	@Resource
	private SessionFactory sessionFactory;
	
	@Transactional
	public void install() {
		Session session = sessionFactory.getCurrentSession();
		
		// 1, 创建固有角色
		/*
		----+------------
		 id | name
		----+------------
		  1 | 学生
		  2 | 教师
		  3 | 超级管理员
		  4 | 论坛管理员
		  5 | 普通用户
		  6 | 资源管理员
		----+------------
		*/
		Role stud, tea, forumMgr, plain, resMgr;
		stud = new Role("学生","学生用户，仲恺农业工程学院学生", 1);
		tea = new Role("教师","教师用户，仲恺农业工程学院教师", 1);
		plain = new Role("普通用户","用户在注册时候就是以普通用户的身份", 1);
		forumMgr = new Role("论坛管理员","论坛管理员，具有论坛管理的所有权限", 1);
		resMgr = new Role("资源管理员","管理资源信息，资源的审核等等", 1);
		
		session.save(stud);
		session.save(tea);
		session.save(forumMgr);
		session.save(plain);
		session.save(resMgr);
		
		// 3, 保存权限数据，权限数据最多三层次
		Privilege oj,		// 在线测评模块相关权限数据  
				netComm,	// 网上交流相关模块权限数据 
				//flow,		// 工作流模块权限数据 
				system,		// 系统管理模块权限数据
				res, 		// 资源中心模块权限数据
				personal;	// 个人设置
		oj = new Privilege("在线测评", "poj", null);
		netComm = new Privilege("网上交流", "communication", null);
		//flow = new Privilege("审批流转", "flow", null);		
		system = new Privilege("系统管理", "system", null);
		res = new Privilege("资源中心", "resource", null);
		personal = new Privilege("个人设置", "personal", null);
		
		// 保存顶级权限
		session.save(oj);
		session.save(netComm);
		//session.save(flow);
		session.save(system);
		session.save(res);
		session.save(personal);
		
		// 处理在线测评相关的权限数据
		Privilege ojm1 = new Privilege("题目列表", "poj/exeList", oj);
		Privilege ojm2 = new Privilege("我的解题", "poj/userExeList", oj);
		session.save(ojm1);
		session.save(ojm2);
		
		session.save(new Privilege("题目列表", "poj/exeList", ojm1));
		session.save(new Privilege("创建题目", "poj/editExe", ojm1));
		session.save(new Privilege("保存题目", "poj/saveExe", ojm1));
		//session.save(new Privilege("创建题目", "poj/createExe", ojm1));
		//session.save(new Privilege("导入题目", "poj/loadExe", ojm1));
		session.save(new Privilege("删除题目", "poj/deleteExe", ojm1));
		session.save(new Privilege("修改题目", "poj/updateExe", ojm1));
		session.save(new Privilege("做题目", "poj/solveExe", ojm1));
		session.save(new Privilege("重做题目", "poj/resolveExe", ojm1));
		
		// 保存网上交流模块权限数据
		Privilege netComm1 = new Privilege("邮件列表", "mail/mailList", netComm);
		Privilege netComm2 = new Privilege("论坛管理", "forum/mgrForumList", netComm);
		Privilege netComm3 = new Privilege("论坛系统", "forum/forumList", netComm);
		Privilege netComm4 = new Privilege("聊天室", "chat/onlineChating", netComm);
		session.save(netComm1);
		session.save(netComm2);
		session.save(netComm3);
		session.save(netComm4);
		
		// 板块管理相关Action
		session.save(new Privilege("板块列表", "forum/forumList", netComm2));
		session.save(new Privilege("保存板块", "forum/saveForum", netComm2));
		session.save(new Privilege("编辑板块", "forum/editForum", netComm2));
		session.save(new Privilege("板块修改", "forum/updateForum", netComm2));
		session.save(new Privilege("板块删除", "forum/deleteForum", netComm2));
		session.save(new Privilege("板块上移", "forum/forumMoveUp", netComm2));
		session.save(new Privilege("板块下移", "forum/forumMoveDown", netComm2));
		// 论坛使用相关action
		session.save(new Privilege("板块主题列表", "forum/forumShow", netComm3));
		session.save(new Privilege("回复列表", "forum/topicShow", netComm3));
		session.save(new Privilege("添加主题", "forum/saveTopic", netComm3));
		session.save(new Privilege("删除主题", "forum/deleteTopic", netComm3));
		session.save(new Privilege("编辑主题", "forum/editTopic", netComm3));
		session.save(new Privilege("主题置顶", "forum/topTopic", netComm3));
		session.save(new Privilege("主题精华", "forum/creamTopic", netComm3));
		session.save(new Privilege("主题普通", "forum/ordinaryTopic", netComm3));
		session.save(new Privilege("主题移动", "forum/moveTopic", netComm3));
		session.save(new Privilege("回复主题", "forum/replyTopic", netComm3));
		session.save(new Privilege("修改回复", "forum/updateReply", netComm3));
		
		// 保存工作流flow模块权限数据
		/*
		Privilege flow1 = new Privilege("流程管理", "flow/processDeflist", flow);
		Privilege flow2 = new Privilege("模板管理", "flow/templateList", flow);         
		Privilege flow3 = new Privilege("起草申请", "flow/applyTemplateList", flow);       
		Privilege flow4 = new Privilege("待我审批", "flow/myTaskList", flow);         
		Privilege flow5 = new Privilege("申请查询", "flow/myAppliedList", flow);
		session.save(flow1);
		session.save(flow2);
		session.save(flow3);
		session.save(flow4);
		session.save(flow5);
		*/
		
		// 系统管理模块权限数据
		Privilege sys1 = new Privilege("用户管理", "user/userList", system);
		Privilege sys2 = new Privilege("角色管理", "role/roleList", system);
		session.save(sys1);
		session.save(sys2);
		session.save(new Privilege("用户列表", "user/userList", sys1));
		session.save(new Privilege("新建用户", "user/editUser", sys1));
		session.save(new Privilege("上传用户", "user/uploadUser", sys1));
		session.save(new Privilege("修改用户", "user/updateUser", sys1));
		session.save(new Privilege("删除用户", "user/deleteUser", sys1));
		session.save(new Privilege("初始化密码", "user/initPwd", sys1));
		session.save(new Privilege("角色列表", "role/roleList", sys2));
		session.save(new Privilege("新建角色", "role/editRole", sys2));
		session.save(new Privilege("修改角色", "role/updateRole", sys2));
		session.save(new Privilege("删除角色", "role/deleteRole", sys2));
		
		// 资源相关的权限数据
		
		// 个人设置相关权限数据
		Privilege p1 = new Privilege("个人信息", "user/personalInfo", personal);
		Privilege p2 = new Privilege("个人密码", "user/personalPwd", personal);
		session.save(p1);
		session.save(p2);
		session.save(new Privilege("更新个人信息", "user/updateUserInfo", p1));
		session.save(new Privilege("更新个人密码", "user/updateUserPwd", p2));
		
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		//installer.install();
		//installer.installProblemDemo();
		installer.installAdmin();
		installer.installOridinaryRole();
	}

	@Transactional
	public void installOridinaryRole() {
		sessionFactory.getCurrentSession().save(new Role("普通用户","用户在注册时候就是以普通用户的身份", 1));
    }

	@Transactional
	public void installAdmin() {
		Session session = sessionFactory.getCurrentSession();
		// 2, 创建一个超级管理员，用户名：admin, 密码，admin
		User user = new User();
		user.setAccount("admin");
		user.setName("超级管理员");
		user.setPassword(DigestUtils.md5Hex("admin"));
		user.setActive(User.ACTIVIED);
		user.setEmail("xiajiqiu1990@163.com");
		session.save(user); // 保存
		System.out.println("Finished......");
    }
	
	/**
	 * 插入Problem演示数据
	 */
	/*
	@Transactional
	public void installProblemDemo() {
		Problem problem = null;
		Session session = this.sessionFactory.getCurrentSession();
		
		// 第一个HelloWorld问题
		problem = new Problem();
		problem.setName("HelloWorld");
		problem.setDescription("在控制台输出HelloWorld");
		problem.setOutputStyle("HelloWorld");
		problem.setAuthor("夏集球");
		problem.setPostTime(new Date());
		problem.setLevel(Problem.LEVEL_PRIMARY);
		session.save(problem);
		
		// 计算A+B
		problem = new Problem();
		problem.setName("A+B 问题");
		problem.setDescription("给定两个整数，计算其和并输出到控制台");
		problem.setInputStyle("2 3");
		problem.setOutputStyle("5");
		problem.setAuthor("夏集球");
		problem.setPostTime(new Date());
		problem.setLevel(Problem.LEVEL_PRIMARY);
		session.save(problem);
		
		// 计算A*B
		problem = new Problem();
		problem.setName("A*B 问题");
		problem.setDescription("给定两个整数，计算其乘积并输出到控制台");
		problem.setInputStyle("2 3");
		problem.setOutputStyle("6");
		problem.setAuthor("夏集球");
		problem.setPostTime(new Date());
		problem.setLevel(Problem.LEVEL_PRIMARY);
		session.save(problem);
		
		// 求两个整数中的较大者
		problem = new Problem();
		problem.setName("求两个整数中的较大者");
		problem.setDescription("给定两个整数，计算其中的较大者并输出到控制台");
		problem.setInputStyle("2 3");
		problem.setOutputStyle("3");
		problem.setAuthor("夏集球");
		problem.setPostTime(new Date());
		problem.setLevel(Problem.LEVEL_MIDDLE);
		session.save(problem);
		
		// 给定一个整数，判断它是不是素数
		problem = new Problem();
		problem.setName("判断给定的数字是否是素数");
		problem.setDescription("给定一个整数，要求判断是不是素数。素数指在一个大于1的自然数中，除了1和此整数自身外，不能被其他自然数整除的数");
		problem.setInputStyle("13");
		problem.setOutputStyle("13是素数");
		problem.setAuthor("夏集球");
		problem.setPostTime(new Date());
		problem.setLevel(Problem.LEVEL_PRIMARY);
		session.save(problem);
	}
	*/

}
