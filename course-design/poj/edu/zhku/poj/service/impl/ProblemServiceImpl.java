package edu.zhku.poj.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zhku.fr.dao.impl.HibernateDaoSupport;
import edu.zhku.poj.domain.Problem;
import edu.zhku.poj.service.ProblemService;

/**
 * 题目操作组件默认实现类
 *
 * @author XJQ
 * @since 2013-2-6
 */
@Service
@Transactional
public class ProblemServiceImpl extends HibernateDaoSupport<Problem> implements ProblemService {

}
