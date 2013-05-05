package edu.zhku.poj.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.controller.BaseController;
import edu.zhku.base.mail.Validator;
import edu.zhku.fr.MemoryPaging;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.RoleService;
import edu.zhku.fr.service.UserService;
import edu.zhku.poj.POJKey;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.dto.CourseQueryCondition;
import edu.zhku.poj.service.CourseService;

/**
 * 教学辅助控制器
 * 
 * @author XJQ
 * date 2013-4-29
 */
@Controller
@RequestMapping("/poj")
public class CourseController extends BaseController {
    @Resource
    private CourseService courseService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private RoleService roleService;
    
    // ------------------------------------------------------------------------------------

    /**
     * 课程列表
     * @return
     */
    @RequestMapping("courseList.html")
    public ModelAndView courseList(CourseQueryCondition condition, Integer currentPage, Integer pageSize) {
        ModelAndView mav = new ModelAndView("poj/courseList");
        PageBean pb = this.courseService.paging(
                this.getCurrentPage(currentPage),// 
                this.getPageSize(pageSize), //
                this.queryHelper.setClass(Course.class).setAlias("c")//
                    .addCondiction(!Validator.isEmpty(condition) && !Validator.isEmpty(condition.getUserName()), "c.owner.name LIKE ?", "%" + condition.getUserName() + "%")//
                    .addCondiction(!Validator.isEmpty(condition) && !Validator.isEmpty(condition.getCourseName()), "(c.name LIKE ? or c.description LIKE ?)", "%" + condition.getCourseName() + "%", "%" + condition.getCourseName() + "%")//
                    .addOrderProperty(!Validator.isEmpty(condition) && "DESC".equals(condition.getOrderRule()),"c.buildTime", false)//
                    );
        mav.addObject("pb", pb);
        mav.addObject("condition", condition);
        return mav;
    }
    
    /**
     * 所有课程
     * @param condition
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("allCourse.html")
    public ModelAndView allCourse(CourseQueryCondition condition, Integer currentPage, Integer pageSize) {
        return courseList(condition, currentPage, pageSize);
    }
    
    /**
     * 编辑课程页面
     * 
     * @param id 如果是空的就说明本次请求是用于创建课程的，否则就是用来修改课程信息用的
     * @return
     */
    @RequestMapping("editCourseUI.html")
    public ModelAndView editCourseUI(Long id) {
        ModelAndView mav = new ModelAndView("poj/editCourseUI");
        mav.addObject("course", this.courseService.get(id));
        return mav;
    }
    
    /**
     * 保存课程信息到数据库
     * @param course    要保存的课程
     * @param session  
     * @return
     */
    @RequestMapping("saveCourse.html")
    public ModelAndView saveCourse(Course course, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/poj/courseList.html");
        if(course != null) {
            course.setBuildTime(new Date());
            course.setOwner(this.getCurrentUser(session));
            this.courseService.save(course);
        }
        return mav;
    }
    
    /**
     * 更新课程信息
     * @param newCourse 新的课程信息
     * @param owner 所有者
     * @param session
     * @return
     */
    @RequestMapping("updateCourse.html")
    public ModelAndView updateCourse(Course newCourse, User owner, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/poj/courseList.html");
        if(newCourse != null) {
            Course course = this.courseService.get(newCourse.getId());
            course.setName(newCourse.getName());
            course.setDescription(newCourse.getDescription());
            course.setOwner(owner == null ? this.getCurrentUser(session) : owner);
            this.courseService.saveOrUpdate(course);
        }
        return mav;
    }
    
    /**
     * 删除课程
     * @param course
     * @return
     */
    @RequestMapping("removeCourse.html")
    public ModelAndView removeCourse(Long id) {
        ModelAndView mav = new ModelAndView("redirect:/poj/courseList.html");
        this.courseService.deleteById(id);
        return mav;
    }
    
    /**
     * 课程列表应该显示一些课程的基本信息，然后显示这个课程下面的学生列表，在学生下面显示作业列表，学生列表和作业列表还要有独立页面
     * @param id
     * @return
     */
    @RequestMapping("viewCourse.html")
    public ModelAndView viewCourse(Long id) {
        ModelAndView mav = new ModelAndView("poj/viewCourse");
        if(id != null) {
            mav.addObject("course", this.courseService.get(id));
        }
        return mav;
    }

    /**
     * 查看课程的学生列表
     * @param id
     * @return
     */
    @RequestMapping("viewCourseStudents.html")
    public ModelAndView viewCourseStudents(Long courseId, Integer currentPage, Integer pageSize) {
        ModelAndView mav = new ModelAndView("poj/viewCourseStudents");
        if(courseId != null) {
            Course course = this.courseService.get(courseId);
            PageBean pb = MemoryPaging.paging(//
                    this.getCurrentPage(currentPage),//
                    this.getPageSize(pageSize),//
                    course.getStudents(),//
                    new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {
                            if(o1.getId() > o2.getId()) return 1;
                            if(o1.getId() < o2.getId()) return -1;
                            return 0;
                        }
                        
                    });
            mav.addObject("course", course);
            mav.addObject("pb", pb);
        }
        return mav;
    }
    
    /**
     * 加入学生到课程，由学生自己申请加入
     * @param courseId
     * @param userId
     * @return
     */
    @RequestMapping("joinCourse.html")
    public ModelAndView jsonCourse(Long id, HttpSession session) {
        if(id != null) {
            Course course = this.courseService.get(id);
            course.getStudents().add(this.getCurrentUser(session));
            this.courseService.update(course);
        }
        //return viewCourseStudents(id, getCurrentPage(null), getPageSize(null));
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("courseId", id);
        return mav;
    }
    
    /**
     * 退出课程， 由学生申请
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("quitCourse.html")
    public ModelAndView quitCourse(Long id, HttpSession session) {
        if(id != null) {
            Course course = this.courseService.get(id);
            course.getStudents().remove(this.getCurrentUser(session));
            this.courseService.update(course);
        }
        //return viewCourseStudents(id, getCurrentPage(null), getPageSize(null));
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("courseId", id);
        return mav;
    }
    
    /**
     * 删除课程中的某个学生，是由教师用户进行操作的
     * @param courseId
     * @param userId
     * @return
     */
    @RequestMapping("removeCourseStudent.html")
    public ModelAndView removeCourseStudent(Long courseId, Long[] studentIds) {
        if(courseId != null && studentIds != null) {
            Course course = this.courseService.get(courseId);
            if(course != null) {
                course.getStudents().removeAll(this.userService.getByIds(studentIds));
            }
            this.courseService.update(course);
        }
        //return viewCourseStudents(courseId, getCurrentPage(null), getPageSize(null));
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("id", courseId);
        return mav;
    }
    
    /**
     * 添加学生到某个课程中去
     * @param courseId
     * @param studentId
     * @return
     */
    @RequestMapping("addStudentToCourse.html")
    public ModelAndView addStudentToCourse(Long courseId, Long[] studentIds) {
        if(courseId != null && studentIds != null) {
            Course course = this.courseService.get(courseId);
            if(course != null) {
                List<User> list = this.userService.getByIds(studentIds);
                for(User user : list) {
                    if(!course.getStudents().contains(user)) { 
                        course.getStudents().add(user);
                    }
                }
            }
            this.courseService.update(course);
        }
        //return viewCourseStudents(courseId, getCurrentPage(null), getPageSize(null));
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("id", courseId);
        return mav;
    }
    
    /**
     * 所有的学生用户列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("studentList.html")
    public ModelAndView studentList(Integer currentPage, Integer pageSize, Long courseId) {
        ModelAndView mav = new ModelAndView("poj/studentList");
        Role role = this.roleService.getByName(POJKey.ROLE_STUDENT_NAME.values());
        List<User> list = getNotAttendUsers(role.getUsers(), courseId);
        PageBean pb = MemoryPaging.paging(//
                this.getCurrentPage(currentPage), //
                this.getPageSize(pageSize),// 
                list, //
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        if(o1.getId() > o2.getId()) return 1;
                        if(o1.getId() < o2.getId()) return -1;
                        return 0;
                    }
                });
        mav.addObject("pb", pb);
        mav.addObject("courseId", courseId);
        return mav;
    }

    private List<User> getNotAttendUsers(Set<User> users, Long courseId) {
        Course course = this.courseService.get(courseId);
        List<User> list = new ArrayList<User>();
        for(User user : users) {
            if(!course.getStudents().contains(user)) list.add(user); 
        }
        return list;
    }
}
