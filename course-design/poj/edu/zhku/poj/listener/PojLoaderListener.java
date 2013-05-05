package edu.zhku.poj.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.log.Log;
import edu.zhku.fr.service.RoleService;
import edu.zhku.json.Json;
import edu.zhku.pj.core.SourceHandlerManager;
import edu.zhku.poj.POJKey;
import edu.zhku.poj.ajax.CourseReader;
import edu.zhku.poj.ajax.HomeworkReader;
import edu.zhku.poj.ajax.ProblemReader;
import edu.zhku.poj.ajax.UserReader;
import edu.zhku.poj.domain.Course;
import edu.zhku.poj.domain.Homework;
import edu.zhku.poj.domain.Problem;

public class PojLoaderListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

    @Override
    public void contextInitialized(ServletContextEvent sc) {
        Log.info("Initializing POJ module......");
        // 获取Spring的容器
        ApplicationContext acx = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
        // 语言类型放在ServletContext
        SourceHandlerManager shm = acx.getBean(SourceHandlerManager.class);
        sc.getServletContext().setAttribute("languages", shm.getLanguages());

        // 初始化角色
        this.initRoles(sc.getServletContext(), acx);

        // 注册course reader
        Json.registerJsonReader(new CourseReader(), Course.class);
        Json.registerJsonReader(new ProblemReader(), Problem.class);
        Json.registerJsonReader(new UserReader(), User.class);
        Json.registerJsonReader(new HomeworkReader(), Homework.class);

        Log.info("Finished initializing POJ module......");
    }

    private void initRoles(ServletContext sc, ApplicationContext acx) {
        String studentRoleName = sc.getInitParameter("studentRoleName");
        String teacherRoleName = sc.getInitParameter("teacherRoleName");
        studentRoleName = studentRoleName == null ? "学生用户" : studentRoleName;
        teacherRoleName = teacherRoleName == null ? "教师用户" : teacherRoleName;
        POJKey.ROLE_STUDENT_NAME.value(studentRoleName);
        POJKey.ROLE_TEACHER_NAME.value(teacherRoleName);

        ConfigCenter.setConfig(POJKey.ROLE_STUDENT_NAME, studentRoleName);
        ConfigCenter.setConfig(POJKey.ROLE_TEACHER_NAME, teacherRoleName);

        RoleService roleService = acx.getBean(RoleService.class);
        POJKey.ROLE_STUDENT.value(roleService.getByName(studentRoleName));
        POJKey.ROLE_TEACHER.value(roleService.getByName(teacherRoleName));
        ConfigCenter.setConfig(POJKey.ROLE_STUDENT, POJKey.ROLE_STUDENT.value());
        ConfigCenter.setConfig(POJKey.ROLE_TEACHER, POJKey.ROLE_TEACHER.value());
    }
}
