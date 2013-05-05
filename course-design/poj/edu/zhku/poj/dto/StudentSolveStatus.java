package edu.zhku.poj.dto;

import java.util.Date;

public class StudentSolveStatus {
    private Long hwId; // 题目编号
    private String problemName; // 题目名称
    private String problemDesc; // 题目描述
    private String state;
    private int attemptCount; // 解题尝试次数
    private Date workTime; // 最早解题时间

    public Long getHwId() {
        return hwId;
    }

    public void setHwId(Long hwId) {
        this.hwId = hwId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
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
