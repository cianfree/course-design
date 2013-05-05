package edu.zhku.poj.service;

import java.util.List;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.Problem;

/**
 * 作业业务组件接口
 * 
 * @author XJQ
 * date 2013-4-29
 */
public interface HomeworkService extends DaoSupport<Homework> {

    /**
     * 获取该课程下面的所有作业
     * @param course
     * @return
     */
    List<Homework> getCourseHomeworks(Course course);

    /**
     * 获取对应的homework
     * @param course
     * @param problem
     * @return
     */
    Homework getByCourseAndProblemAndDate(Course course, Problem problem);

}
