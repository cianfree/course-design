package edu.zhku.poj.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.zhku.fr.domain.User;

/**
 * 课程类
 * 
 * @author XJQ date 2013-4-29
 */
public class Course {
    private Long id; // 唯一标识
    private String name; // 课程名称
    private String description; // 课程描述
    private Date buildTime; // 课程建立时间

    private User owner; // 所有者也是创建者
    private Set<User> students = new HashSet<User>(); // 该课程下面的学生，具有学生角色的用户

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

}
