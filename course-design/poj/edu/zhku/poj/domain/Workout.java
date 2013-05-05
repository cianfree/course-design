package edu.zhku.poj.domain;

import java.util.Date;

import edu.zhku.fr.domain.User;

/**
 * 做题结果，本类用来记录做题的结果
 * 
 * @author XJQ
 * @since 2013-2-6
 */
public class Workout {
    private Long id;
    private Date workTime; // 做题时间
    private String state; // 做题状态，状态在本类中定义
    private int attemptTimes = 0; // 尝试次数

    public int getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(int attemptTimes) {
        this.attemptTimes = attemptTimes;
    }

    private User worker; // 是谁做的这到题目，User(1)--Workout(0...1)
    private Problem problem; // 做的是哪道题，Problem(1)--Workout(0..*)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    // TODO 这里的toString方法在进行项目发布的时候要删除
    @Override
    public String toString() {
        return "Workout [id=" + id + ", state=" + state + ", worker=" + worker + ", problem=" + problem + "]";
    }

}
