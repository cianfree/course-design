package edu.zhku.poj.tag;

import org.springframework.context.ApplicationContext;

import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.RoleService;
import edu.zhku.fr.service.UserService;
import edu.zhku.poj.POJKey;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.service.CourseService;

public class PojFun {

    /**
     * 判断是否是具有学生用户角色的用户
     * @param id
     * @return
     */
    public static boolean isStudent(User user) {
        if(user == null) return false;
        RoleService roleService = getBean(RoleService.class);
        Role role = roleService.getByName(ConfigCenter.getConfigString(POJKey.ROLE_STUDENT_NAME));
        if (user.isAdmin()) return false;
        for(Role r : user.getRoles()) {
            if(r.getId().equals(role.getId())) return true;
        }
        return false;
    }
    
    private static ApplicationContext acx = null;
    
    public static final <T> T getBean(Class<T> clazz) {
        if(acx == null) {
            acx = ConfigCenter.getConfig(Key.ACX, ApplicationContext.class);
        }
        return acx.getBean(clazz);
    }
    
    /**
     * 判断是否是具有参加课程的资格
     * @param id
     * @return
     */
    public static boolean canAttend(User user, Long courseId) {
        return isStudent(user) && !isInCourse(user.getId(), courseId);
    }

    /**
     * 判断是否是已经参加了该课程
     * @param course
     * @return
     */
    public static boolean isInCourse(Long userId, Long courseId) {
        CourseService courseService = getBean(CourseService.class);
        UserService userService = getBean(UserService.class);
        User user = userService.get(userId);
        Course course = courseService.getReal(courseId);
        return course.getStudents().contains(user);
    }
    
    /**
     * 检测是不是我创建的课程，如果不是教师用户，就不用检测了
     * 
     * @param user 当前用户
     * @param courseId
     * @return
     */
    public static boolean isMyCourse(User user, Long courseId) {
        if(user == null) return false;
        if(user.isAdmin()) return true;
        CourseService courseService = getBean(CourseService.class);
        return courseService.isOwner(user, courseId);
    }
    
    public static boolean isMyStudent(User user, Long courseId) {
        CourseService courseService = getBean(CourseService.class);
        return courseService.containStudent(user, courseId);
    }
}
