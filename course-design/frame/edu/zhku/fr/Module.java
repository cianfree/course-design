package edu.zhku.fr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextListener;

import edu.zhku.fr.domain.Privilege;

/**
 * 代表一个模块，从module-config.xml中读取并转换成对象
 * 
 * @author XJQ date 2013-4-24
 */
public class Module {
    private String name;
    private boolean deploy = false;
    private List<String> depends = new ArrayList<String>(); // 所依赖模块

    private String hibernateConf;
    private String springConf;
    private String privilegeConf;

    private Set<ServletContextListener> listeners;

    private List<Privilege> topPrivileges = new ArrayList<Privilege>(); // 本模块顶级权限

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeploy() {
        return deploy;
    }

    public void setDeploy(boolean deploy) {
        this.deploy = deploy;
    }

    public List<String> getDepends() {
        return depends;
    }

    public void setDepends(List<String> depends) {
        this.depends = depends;
    }

    public String getHibernateConf() {
        return hibernateConf;
    }

    public void setHibernateConf(String hibernateConf) {
        this.hibernateConf = hibernateConf;
    }

    public String getSpringConf() {
        return springConf;
    }

    public void setSpringConf(String springConf) {
        this.springConf = springConf;
    }

    public String getPrivilegeConf() {
        return privilegeConf;
    }

    public void setPrivilegeConf(String privilegeConf) {
        this.privilegeConf = privilegeConf;
    }

    public List<Privilege> getTopPrivileges() {
        return topPrivileges;
    }

    public void setTopPrivileges(List<Privilege> topPrivileges) {
        this.topPrivileges = topPrivileges;
    }

    public Set<ServletContextListener> getListeners() {
        return listeners;
    }

    public void setListeners(Set<ServletContextListener> listeners) {
        this.listeners = listeners;
    }

}
