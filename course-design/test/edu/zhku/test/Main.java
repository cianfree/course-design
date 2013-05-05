package edu.zhku.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.zhku.fr.domain.User;
import edu.zhku.fr.service.UserService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = acx.getBean(UserService.class);
        User user = new User();
        user.setAccount("admin");
        user.setActive(User.ACTIVIED);
        user.setName("夏集球");
        user.setEmail("xiajiqiu1990@163.com");
        user.setRegTime(new Date());
        
        userService.save(user);
        User u = userService.getByAccount("admin");
        System.out.println(u);
    }
}
