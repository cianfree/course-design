package edu.zhku.poj.dto;

/**
 * 课程相关查询条件DTO
 * 
 * @author XJQ date 2013-4-29
 */
public class CourseQueryCondition {
    private String courseName;
    private String userName;
    private String orderRule; // 排序规则

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(String orderRule) {
        this.orderRule = orderRule;
    }

}
