package edu.zhku.poj.service.impl;

import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.DaoRunntimeException;
import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.service.CourseService;

/**
 * 课程业务组件实现
 * 
 * @author XJQ
 * date 2013-4-29
 */
@Service
@Transactional
public class CourseServiceImpl extends HibernateDaoSupport<Course> implements CourseService {

    @Override
    public void delete(Course course) throws DaoRunntimeException {
        // 获取到Course
        if(course != null) {
            course = this.get(course.getId());
            course.setStudents(null);
            course.setOwner(null);
            this.getSession().delete(course);
        }
    }

    @Override
    public Course getReal(Long id) {
        Course course = this.get(id);
        course.getOwner();
        for(User user : course.getStudents()) {
            Hibernate.initialize(user);
        }
        return course;
    }

    @Override
    public boolean isOwner(User user, Long courseId) {
        Course course = this.get(courseId);
        return course.getOwner().equals(user);
    }

    @Override
    public boolean containStudent(User user, Long courseId) {
        if(user == null || courseId == null) return false;
        if(user.isAdmin()) return true;
        Set<User> users = this.get(courseId).getStudents();
        for (User u : users) {
            if(user.equals(u)) {
                return true;
            }
        }
        return false;
    }

    
}
