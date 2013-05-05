package edu.zhku.poj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.service.HomeworkService;

/**
 * 作业业务组件默认实现类
 * 
 * @author XJQ date 2013-4-29
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class HomeworkServiceImpl extends HibernateDaoSupport<Homework> implements HomeworkService {

    @Override
    public List<Homework> getCourseHomeworks(Course course) {
        if (course != null)
            return this.getSession().createQuery("FROM " + this.getEntityName() + " t WHERE t.course=:course ORDER BY t.postTime ASC")//
                    .setParameter("course", course)//
                    .list();
        return null;
    }

    @Override
    public Homework getByCourseAndProblemAndDate(Course course, Problem problem) {
        if(course == null || problem == null) return null;
        Homework hw = (Homework) this.getSession().createQuery(//
                "FROM " + this.getEntityName() + " t WHERE t.course=:course AND t.problem=:problem")//
                .setParameter("course", course)//
                .setParameter("problem", problem)//
                .uniqueResult();
        if(hw != null) {
            if(isEqualDate(new Date(), hw.getPostTime())) {
                return null;
            }
        }
        return hw;
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
}
