package edu.zhku.poj.dto;

import java.util.Date;

/**
 * 作业状态列表
 * 
 * @author XJQ date 2013-4-30
 */
public class HomeworkSolveStatus {
    private Long userId;

    private String userName;
    private String problemName;
    private String state;
    private int attemptCount; // 尝试解题次数
    private Date workTime; // 第一次开始解题时间

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

}
