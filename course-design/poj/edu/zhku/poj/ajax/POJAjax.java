package edu.zhku.poj.ajax;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.MemoryPaging;
import edu.zhku.fr.ajax.BaseAjax;
import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.RoleService;
import edu.zhku.json.Json;
import edu.zhku.module.AjaxMethod;
import edu.zhku.module.AjaxModule;
import edu.zhku.pj.core.HandleStatus;
import edu.zhku.pj.core.Language;
import edu.zhku.pj.core.SourceHandler;
import edu.zhku.pj.core.SourceHandlerManager;
import edu.zhku.poj.POJKey;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.domain.Workout;
import edu.zhku.poj.domain.WorkoutHW;
import edu.zhku.poj.service.CourseService;
import edu.zhku.poj.service.HomeworkService;
import edu.zhku.poj.service.ProblemService;
import edu.zhku.poj.service.WorkoutHWService;
import edu.zhku.poj.service.WorkoutService;

/**
 * POJ在线评测模块Ajax服务
 * 
 * @author XJQ
 * date 2013-4-28
 */
@AjaxModule("poj")
public class POJAjax extends BaseAjax {
    
    /**
     * 普通用户做题
     * @param kvList
     * @return
     */
    @AjaxMethod("fixProblem")
    public static String fixProblem(KVList kvList) {
        String language = kvList.getString("language");
        String source = kvList.getString("source");
        Long id = kvList.getLong("id");
        ProblemService problemService = getBean(ProblemService.class, kvList);
        final WorkoutService workoutService = getBean(WorkoutService.class, kvList);
        
        if(source == null) return "您还没有键入任何代码......";
        final Problem problem = problemService.get(id);
        final User user = getCurrentUser(kvList);
        final String state = handler(kvList, source, language, user.getAccount(), problem);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Workout workout = workoutService.queryUserWorkout(user, problem);
                if(workout == null) {
                    workout = new Workout();
                    workout.setProblem(problem);
                    workout.setWorker(user);
                    workout.setWorkTime(new Date());
                }
                workout.setAttemptTimes(workout.getAttemptTimes() + 1);
                workout.setState(state);
                workoutService.saveOrUpdate(workout);
            }
        }).start();
        // 编译和执行，并返回结果
        return state;
    }
    
    /**
     * 处理本次编译和执行任务
     * @param sourceStr
     * @param lanName
     * @param account
     * @return
     */
    private static String handler(KVList kvList, String sourceStr, String language, String account, Problem problem) {
        SourceHandlerManager sourceHandlerManager = getBean(SourceHandlerManager.class, kvList);
        // 获取处理器
        SourceHandler handler = sourceHandlerManager.getSourceHandler(language);
        // 设置个人工作目录
        String path = handler.getWorkFolder() + File.separator + account;
        handler.setWorkFolder(path);
        
        HandleStatus info = handler.handle(sourceStr, problem.getInputStyle(), problem.getOutputStyle());
        
        return info.toString();
    }
    
    /**
     * 获取具体编程语言的模版
     * @param kvList
     * @return
     */
    @AjaxMethod("sourceTemplate")
    public static String getLanguageSourceTemplate(KVList kvList) {
        String name = kvList.getString("name");
        SourceHandlerManager sourceHandlerManager = getBean(SourceHandlerManager.class, kvList);
        Language lan = sourceHandlerManager.getLanguage(name);
        if(lan != null) {
            String temp = lan.getTemplate().substring(1, lan.getTemplate().length() - 1);
            return temp;
        }
        return "";
    }
    
    /**
     * 学生用户做题
     * @param kvList
     * @return
     */
    @AjaxMethod("fixHomework")
    public static String fiexHomework(KVList kvList) {
        // required: hwid, language, source
        Long hwId = kvList.getLong("hwId");
        Long proId = kvList.getLong("proId");
        String language = kvList.getString("language");
        String source = kvList.getString("source");
        if(source == null) return "您还没有键入任何代码......";
        HomeworkService homeworkService = getBean(HomeworkService.class, kvList);
        ProblemService problemService = getBean(ProblemService.class, kvList);
        Problem problem = problemService.get(proId);
        final WorkoutHWService workoutHWService = getBean(WorkoutHWService.class, kvList);
        final User user = getCurrentUser(kvList);
        final Homework hw = homeworkService.get(hwId);
        final String state = handler(kvList, source, language, user.getAccount(), problem);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutHW workout = workoutHWService.getUserWorkoutHW(user, hw);
                if(workout == null) {
                    workout = new WorkoutHW();
                    workout.setHomework(hw);
                    workout.setWorker(user);
                    workout.setWorkTime(new Date());
                }
                workout.setAttemptTimes(workout.getAttemptTimes() + 1);
                workout.setState(state);
                workoutHWService.saveOrUpdate(workout);
            }
        }).start();
        return state;
    }
    
    @SuppressWarnings("unchecked")
    @AjaxMethod("getCourses")
    public static String getCourses(KVList kvList) {
        CourseService courseService = getBean(CourseService.class, kvList);
        int pageIndex = kvList.getInt("currentPage");
        int pageSize = kvList.getInt("pageSize");
        
        PageBean pb = courseService.paging(getCurrentPage(pageIndex), getPageSize(pageSize), //
                queryHelper.setClass(Course.class).setAlias("c"));
        List<Course> courses = (List<Course>) pb.getRecordList();
        if(courses.size() <= 0) return "null";
        for (Course course : courses) {
            course.getOwner();
        }
        String json = Json.toJson(courses);
        json = json.substring(0, json.lastIndexOf("]"));
        json += ",{\"pageCount\":\"" + pb.getPageCount() +
                "\",\"pageSize\":\"" + pb.getPageSize() +
                "\",\"recordCount\":\"" + pb.getRecordCount() +
                "\"}]";
        return json;
    }
    
    @SuppressWarnings("unchecked")
    @AjaxMethod("getProblems")
    public static String getProblems(KVList kvList) {
        ProblemService problemService = getBean(ProblemService.class, kvList);
        int pageIndex = kvList.getInt("currentPage");
        int pageSize = kvList.getInt("pageSize");
        
        PageBean pb = problemService.paging(getCurrentPage(pageIndex), getPageSize(pageSize), //
                queryHelper.setClass(Problem.class).setAlias("c"));
        List<Problem> problems = (List<Problem>) pb.getRecordList();
        String json = Json.toJson(problems);
        if("null".endsWith(json)) return "null";
        json = json.substring(0, json.lastIndexOf("]"));
        json += ",{\"pageCount\":\"" + pb.getPageCount() +
                "\",\"pageSize\":\"" + pb.getPageSize() +
                "\",\"recordCount\":\"" + pb.getRecordCount() +
                "\"}]";
        return json;
    }
    
    /**
     * 获取某个课程的学生列表
     * @param kvList
     * @return
     */
    @AjaxMethod("getCourseStudents")
    public static String getCourseStudents(KVList kvList) {
        CourseService courseService = getBean(CourseService.class, kvList);
        Long courseId = kvList.getLong("courseId");
        Integer currentPage = kvList.getInt("currentPage");
        Integer pageSize = kvList.getInt("pageSize");
        if(courseId == null) return "null";
        Course course = courseService.get(courseId);
        PageBean pb = MemoryPaging.paging(//
                getCurrentPage(currentPage),//
                getPageSize(pageSize),//
                course.getStudents(),//
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        if(o1.getId() > o2.getId()) return 1;
                        if(o1.getId() < o2.getId()) return -1;
                        return 0;
                    }
                });
        String json = Json.toJson(pb);
        return json;
    }
    
    /**
     * 获取课程的作业列表
     * @param kvList
     * @return
     */
    @AjaxMethod("getCourseHomeworks")
    public static String getCOurseHomeworks(KVList kvList) {
        CourseService courseService = getBean(CourseService.class, kvList);
        HomeworkService homeworkService = getBean(HomeworkService.class, kvList);
        Long courseId = kvList.getLong("courseId");
        if(courseId == null) return "null";
        
        Integer currentPage = kvList.getInt("currentPage");
        Integer pageSize = kvList.getInt("pageSize");
        Course course = courseService.get(courseId);
        PageBean pb = homeworkService.paging(//
                getCurrentPage(currentPage), //
                getPageSize(pageSize), //
                queryHelper.setClass(Homework.class).setAlias("t")//
                    .addCondiction("t.course=?", course));
        String json = Json.toJson(pb);
        return json;
    }
    
    /**
     * 判断是不是学生，如果是管理员，就不算是
     * @param kvList
     * @return
     */
    @AjaxMethod("isStudent")
    public static boolean isStudent(KVList kvList) {
        User user = getCurrentUser(kvList);
        if (user != null && user.isAdmin()) return true;
        RoleService roleService = getBean(RoleService.class, kvList);
        Role role = roleService.getByName(ConfigCenter.getConfigString(POJKey.ROLE_STUDENT_NAME));
        for(Role r : user.getRoles()) {
            if(r.getId().equals(role.getId())) return true;
        }
        return false;
    }
    
    /**
     * 检测是否是我创建测课程
     * @param kvList
     * @return
     */
    @AjaxMethod("isMyCourse")
    public static boolean isMyCourse(KVList kvList) {
        CourseService courseService = getBean(CourseService.class, kvList);
        Long courseId = kvList.getLong("courseId");
        if(courseId == null) return false;
        User user = getCurrentUser(kvList);
        if(user == null) return false;
        if(user.isAdmin()) return true;
        return courseService.isOwner(user, courseId);
    }
    
    /**
     * 判断当前用户是否是给定课程的学生
     * @param kvList
     * @return
     */
    @AjaxMethod("isMyStudent")
    public static boolean isMyStudent(KVList kvList) {
        Long courseId = kvList.getLong("courseId");
        return getBean(CourseService.class, kvList).containStudent(getCurrentUser(kvList), courseId);
    }
}
