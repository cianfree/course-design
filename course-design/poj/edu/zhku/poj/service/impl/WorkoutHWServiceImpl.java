package edu.zhku.poj.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.WorkoutHW;
import edu.zhku.poj.service.WorkoutHWService;

/**
 * 作业解答的默认实现组件
 * 
 * @author XJQ date 2013-4-29
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class WorkoutHWServiceImpl extends HibernateDaoSupport<WorkoutHW> implements WorkoutHWService {

    @Override
    public List<WorkoutHW> getHomeworkSolveList(Homework hw) {
        if(hw == null) return null;
        return this.getSession().createQuery(//
                "FROM " + this.getEntityName() + " t WHERE t.homework=:hw")//
                .setParameter("hw", hw)//
                .list();
    }

    @Override
    public WorkoutHW getUserWorkoutHW(User worker, Homework hw) {
        if(worker == null || hw == null) return null;
        return (WorkoutHW) this.getSession().createQuery(//
                "FROM " + this.getEntityName() + " t WHERE t.worker=:worker AND t.homework=:hw")//
                .setParameter("worker", worker)//
                .setParameter("hw", hw)//
                .uniqueResult();
    }

    @Override
    public List<WorkoutHW> getUserAllWorkoutHW(User user) {
        if(user == null) return null;
        return this.getSession().createQuery(//
                "FROM " + this.getEntityName() + " t WHERE t.worker=:worker")//
                .setParameter("worker", user)//
                .list();
    }

    @Override
    public void deleteByHomework(Homework hw) {
        if(hw == null) return ;
        this.getSession().createQuery(//
                "DELETE FROM " + this.getEntityName() + " t WHERE t.homework=:hw")
                .setParameter("hw", hw)//
                .executeUpdate();
    }

}
