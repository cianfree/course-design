package edu.zhku.poj.service;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Course;

/**
 * 课程业务组件接口
 * 
 * @author XJQ
 * date 2013-4-29
 */
public interface CourseService extends DaoSupport<Course> {

    /**
     * 获取非代理的对象
     * @param id
     * @return
     */
    Course getReal(Long id);

    /**
     * 判断是否本课程的创建人
     * @param user
     * @return
     */
    boolean isOwner(User user, Long courseId);

    /**
     * 判断是否是本课程的学生
     * @param user
     * @param courseId
     * @return
     */
    boolean containStudent(User user, Long courseId);

}
