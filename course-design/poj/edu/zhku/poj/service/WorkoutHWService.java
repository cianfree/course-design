package edu.zhku.poj.service;

import java.util.List;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.WorkoutHW;

/**
 * 解答作业的业务组件接口
 * 
 * @author XJQ
 * date 2013-4-29
 */
public interface WorkoutHWService extends DaoSupport<WorkoutHW> {

    /**
     * 获取该作业下的所有解题列表
     * @param hw
     * @return
     */
    List<WorkoutHW> getHomeworkSolveList(Homework hw);

    /**
     * 根据用户和作业获取对应的解题对象
     * @param user
     * @param hw
     * @return 
     */
    WorkoutHW getUserWorkoutHW(User user, Homework hw);

    /**
     * 获取该用户的所有作业对象
     * @param user
     * @return
     */
    List<WorkoutHW> getUserAllWorkoutHW(User user);

    /**
     * 删除该作业下面的所哦解题结果
     * @param hw
     */
    void deleteByHomework(Homework hw);

}
