package edu.zhku.poj.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.fr.domain.User;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.domain.Workout;
import edu.zhku.poj.service.WorkoutService;

/**
 * 用户相关业务处理类
 *
 * @author XJQ
 * @since 2013-1-10
 */
@Service
@Transactional
public class WorkoutServiceImpl extends HibernateDaoSupport<Workout> implements WorkoutService {

	@Override
	public Workout queryUserWorkout(User user, Problem problem) {
		return (Workout) this.getSession().createQuery(//
				"FROM " + this.getEntityName() + " t WHERE t.worker=:user AND t.problem=:problem")//
				.setParameter("user", user)//
				.setParameter("problem", problem)//
				.uniqueResult();
	}
	
}
