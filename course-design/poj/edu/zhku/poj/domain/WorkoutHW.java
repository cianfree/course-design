package edu.zhku.poj.domain;

import java.util.Date;

import edu.zhku.fr.domain.User;

/**
 * 学生做题
 * 
 * @author XJQ date 2013-4-29
 */
public class WorkoutHW {
    private Long id; // 唯一标识
    private Date workTime; // 做题时间，应是最小修改时间
    private Integer attemptTimes = 0; // 尝试解题次数
    private String state = State.NOTSOLVE; // 解题状态， 有Workout类中的五种状态

    private User worker; // 是谁去做的
    private Homework homework; // 对应于哪道题目

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

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

    public Integer getAttemptTimes() {
        return attemptTimes;
    }

    public void setAttemptTimes(Integer attemptTimes) {
        this.attemptTimes = attemptTimes;
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

}
