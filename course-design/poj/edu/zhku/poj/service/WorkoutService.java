package edu.zhku.poj.service;

import edu.zhku.fr.dao.DaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.domain.Workout;

/**
 * 用户业务逻辑处理
 *
 * @author XJQ
 * @since 2013-1-10
 */
public interface WorkoutService extends DaoSupport<Workout> {

	/**
	 * 查询解题，因为再一次解题中，可能重做
	 * @param user
	 * @param problem
	 * @return
	 */
	Workout queryUserWorkout(User user, Problem problem);

}
