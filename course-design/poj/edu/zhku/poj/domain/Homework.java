package edu.zhku.poj.domain;

import java.util.Date;

/**
 * 作业
 * 
 * @author XJQ date 2013-4-29
 */
public class Homework {
    private Long id; // 唯一标识符
    private Date postTime; // 创建时间

    private Course course; // 所属课程
    private Problem problem; // 题目
    
    // --------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

}
