package edu.zhku.poj.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.base.controller.BaseController;
import edu.zhku.fr.MemoryPaging;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.priv.tag.Validate;
import edu.zhku.fr.service.UserService;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.domain.State;
import edu.zhku.poj.domain.WorkoutHW;
import edu.zhku.poj.dto.HomeworkSolveStatus;
import edu.zhku.poj.dto.StudentSolveStatus;
import edu.zhku.poj.service.CourseService;
import edu.zhku.poj.service.HomeworkService;
import edu.zhku.poj.service.ProblemService;
import edu.zhku.poj.service.WorkoutHWService;

/**
 * 作业相关的控制
 * 
 * @author XJQ
 * date 2013-4-30
 */
@Controller
@RequestMapping("/poj")
public class HomeworkController extends BaseController {
    @Resource
    private CourseService courseService;
    
    @Resource
    private HomeworkService homeworkService;
    
    @Resource
    private ProblemService problemService;
    
    @Resource
    private WorkoutHWService workoutHWService;
    
    @Resource
    private UserService userService;
    
    // --------------------------------------------------------------------
    /**
     * 课程的作业列表
     * @param id
     * @return
     */
    @RequestMapping("viewCourseHomeworks.html")
    public ModelAndView viewCourseHomeworks(Long id, Integer currentPage, Integer pageSize) {
        ModelAndView mav = new ModelAndView("poj/viewCourseHomeworks");
        if(id != null) {
            Course course = this.courseService.get(id);
            PageBean pb = this.homeworkService.paging(getCurrentPage(currentPage), getPageSize(pageSize), //
                    this.queryHelper.setClass(Homework.class).setAlias("t")//
                        .addCondiction("t.course=?", course));
            
            mav.addObject("course", course);
            mav.addObject("pb", pb);
        }
        return mav;
    }
    
    /**
     * 编辑作业页面
     * @param courseId
     * @param hwId homework id
     * @return 如果hwId是空的表明是要进行作业的添加操作，否则是修改作业界面,前端会更具是否放入hw进行选择表单提交到什么地方
     */
    @RequestMapping("editHomeworkUI.html")
    public ModelAndView editHomeworkUI(Long courseId, Long hwId) {
        ModelAndView mav = new ModelAndView("poj/editHomeworkUI");
        if(hwId != null) {
            Homework hw = this.homeworkService.get(hwId);
            mav.addObject("hw", hw);        
        }
        mav.addObject("course", this.courseService.get(courseId));
        return mav;
    }
    
    /**
     * 添加作业
     * @param hw 作业对象
     * @param courseId 课程ID
     * @param problemId 题目ID
     * @return
     */
    @RequestMapping("saveHomework.html")
    public ModelAndView saveHomework(Long courseId, Long problemId) {
        if(courseId != null && problemId != null) {
            Course course = this.courseService.get(courseId);
            Problem problem = this.problemService.get(problemId);
            Homework hw = this.homeworkService.getByCourseAndProblemAndDate(course, problem);
            if(hw == null) {
                hw = new Homework();
                hw.setCourse(course);
                hw.setProblem(problem);
                hw.setPostTime(new Date());
                this.homeworkService.save(hw);
            }
        }
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("id", courseId);
        return mav;
    }
    
    /**
     * 删除作业
     * @param courseId
     * @param hwId
     * @return
     */
    @RequestMapping("removeHomework.html")
    public ModelAndView removeHomework(Long courseId, Long hwId) {
        if(courseId == null) return new ModelAndView("redirect:/frame/error.html");
        if(hwId != null) {
            Homework hw = this.homeworkService.get(hwId);
            this.workoutHWService.deleteByHomework(hw);
            this.homeworkService.delete(hw);
        }
        ModelAndView mav = new ModelAndView("redirect:/poj/viewCourse.html");
        mav.addObject("id", courseId);
        return mav;
    }
    
    /**
     * 获取本作业的所有学生完成情况列表
     * @param hwid
     * @param state 状态，从State类中获取
     * @return
     */
    @RequestMapping("homeworkSolveStatus.html")
    public ModelAndView homeworkSolveStatus(Long hwId, String state, Integer currentPage, Integer pageSize) {
        if(hwId == null) return new ModelAndView("redirect:/frame/error.html");
        ModelAndView mav = new ModelAndView("poj/homeworkSolveStatus");
        Homework hw = this.homeworkService.get(hwId);
        // 准备数据: 作业本身，该作业每个学生的做题情况
        List<WorkoutHW> whws = this.workoutHWService.getHomeworkSolveList(hw);
        Set<User> students = hw.getCourse().getStudents();
        PageBean pb = MemoryPaging.paging(//
                getCurrentPage(currentPage), //
                getPageSize(pageSize), //
                getAllStudentHomeworkSolveStatus(whws, students, state, hw), //
                new Comparator<HomeworkSolveStatus>() {
                    @Override
                    public int compare(HomeworkSolveStatus o1, HomeworkSolveStatus o2) {
                        if(o1.getUserId() > o2.getUserId()) return 1;
                        if(o1.getUserId() < o2.getUserId()) return -1;
                        return 0;
                    }
        });
        mav.addObject("pb", pb);
        mav.addObject("hw", hw);
        return mav;
    }

    /**
     * 整理输出结果
     * @param whws
     * @param students
     * @param state
     * @return
     */
    private List<HomeworkSolveStatus> getAllStudentHomeworkSolveStatus(List<WorkoutHW> whws, Set<User> students, String state, Homework hw) {
        List<HomeworkSolveStatus> results = new ArrayList<HomeworkSolveStatus>();
        HomeworkSolveStatus hss = null;
        for(User user : students) {
            hss = new HomeworkSolveStatus();
            hss.setProblemName(hw.getProblem().getName());
            hss.setUserId(user.getId());
            hss.setUserName(user.getName());
            String status = State.NOTSOLVE;
            boolean add = false;
            for(int i=0; i<whws.size(); ++i) {
                WorkoutHW whw = whws.get(i);
                if(whw.getWorker().equals(user)) {
                    status = whw.getState();
                    if(status.equals(state)) {
                        add = true;
                    }
                    hss.setWorkTime(whw.getWorkTime());
                    hss.setAttemptCount(whw.getAttemptTimes());
                    break;
                }
            }
            if(state == null) add = true;
            hss.setState(status);
            if(add) results.add(hss);
        }
        return results;
    }

    /**
     * 获取学生该课程下的作业解答情况，所有作业
     * @param studentId
     * @param hwtime 作业布置的时间
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("studentSolveStatus.html")
    public ModelAndView studentSolveStatus(Long courseId, Long studentId, Date hwtime, Integer currentPage, Integer pageSize) {
        if(studentId == null || courseId == null) return new ModelAndView("redirect:/frame/error.html");
        ModelAndView mav = new ModelAndView("poj/studentSolveStatus");
        // 查询该课程下的所有Homework并
        List<Homework> hws = this.homeworkService.getCourseHomeworks(this.courseService.get(courseId));
        // 获取到安装时间进行排序
        List<WorkoutHW> whws = this.workoutHWService.getUserAllWorkoutHW(this.userService.get(studentId));
        
        List<StudentSolveStatus> list = getSingleStudentSolveStatus(hws, whws, hwtime);
        PageBean pb = MemoryPaging.paging(//
                this.getCurrentPage(currentPage), //
                this.getPageSize(pageSize),//
                list,
                new Comparator<StudentSolveStatus>() {
                    @Override
                    public int compare(StudentSolveStatus o1, StudentSolveStatus o2) {
                        if(o1.getHwId() > o2.getHwId()) return 1;
                        if(o1.getHwId() < o2.getHwId()) return -1;
                        return 0;
                    }
                });
        mav.addObject("pb", pb);
        mav.addObject("hwtime", hwtime);
        return mav;
    }

    private List<StudentSolveStatus> getSingleStudentSolveStatus(List<Homework> hws, List<WorkoutHW> whws, Date hwtime) {
        List<StudentSolveStatus> results = new ArrayList<StudentSolveStatus>();
        StudentSolveStatus hss = null;
        for(Homework hw : hws) {
            hss = new StudentSolveStatus();
            hss.setHwId(hw.getId());
            hss.setProblemName(hw.getProblem().getName());
            hss.setProblemDesc(Validate.fixLen(hw.getProblem().getDescription(), 25));
            boolean isWorked = false;
            for(WorkoutHW whw : whws) {
                if(whw.getHomework().getId().equals(hw.getId())) {
                    hss.setAttemptCount(whw.getAttemptTimes());
                    hss.setState(whw.getState());
                    hss.setWorkTime(whw.getWorkTime());
                    isWorked = true;
                }
            }
            if(!isWorked) {
                hss.setState(State.NOTSOLVE);
            }
            if(hwtime == null || isEqualDate(hwtime, hw.getPostTime())) {
                results.add(hss);
            }
        }
        return results;
    }

    /**
     * 只需要年月日相等即可
     * @param hwtime
     * @param postTime
     * @return
     */
    @SuppressWarnings("deprecation")
    private boolean isEqualDate(Date hwtime, Date postTime) {
        return hwtime.getYear() == postTime.getYear()
            && hwtime.getMonth() == postTime.getMonth()
            && hwtime.getDate() == postTime.getDate();
    }
    
    /**
     * 做作业界面
     * @return
     */
    @RequestMapping("solveHomeworkUI.html")
    public ModelAndView solveHomeworkUI(Long hwId) {
        if(hwId == null) return new ModelAndView("redirect:/frame/error.html");
        ModelAndView mav = new ModelAndView("poj/solveHomeworkUI");
        Homework hw = this.homeworkService.get(hwId);
        mav.addObject("hw", hw);
        return mav;
    }
    
}
