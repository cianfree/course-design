package edu.zhku.fr;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.expections.DependenceModuleInactiveException;
import edu.zhku.fr.expections.DuplicateModuleException;
import edu.zhku.fr.expections.FrameException;
import edu.zhku.fr.expections.InstancingException;
import edu.zhku.fr.expections.NoSuchConfigException;
import edu.zhku.fr.expections.NullModuleNameException;
import edu.zhku.fr.expections.WritingXmlException;
import edu.zhku.fr.log.Log;
import edu.zhku.fr.service.PrivilegeService;
import edu.zhku.fr.service.RoleService;
import edu.zhku.utils.ResourceUtils;

/**
 * 模块服务管理
 * 
 * @author XJQ date 2013-4-28
 */
@SuppressWarnings("unchecked")
public class ModuleService {
    private static ServletContext context = null;
    private static ServletContextEvent event = null;
    private static Set<Module> modules = new HashSet<Module>();
    private static Set<Role> deployRoles = new HashSet<Role>();
    private static Set<Role> undeloyRoles = new HashSet<Role>();
    private static Set<ServletContextListener> listeners = new HashSet<ServletContextListener>();
    private static boolean isDeploy = false;

    // ---------------------------------------------------------

    public static void init(ServletContextEvent sce) throws FrameException {
        context = sce.getServletContext();
        event = sce;
        parseModule(); // 读取模块配置文件
        registerModule(); // 进行模块的注册工作
        isDeploy = modules.size() > 0;
    }
    
    /**
     * 初始化模块上下文环境
     */
    public static void initModuleContext() {
        for (ServletContextListener listener : listeners) {
            listener.contextInitialized(event);
        }
    }

    /**
     * 清除相关资源数据
     */
    public static void clear() {
        modules.clear();
        context = null;
        event = null;
    }

    public static void contextDestory(ServletContextEvent sce) {
        for (ServletContextListener listener : listeners) {
            listener.contextDestroyed(sce);
        }
    }
    
    /**
     * 是否进行了部署
     * @return
     */
    public static boolean hasDeploy() {
        return isDeploy;
    }

    public static void freshSystem() {
        freshPrivilege();
        freshRole();
    }
    
    /**
     * 刷新角色信息
     */
    private static void freshRole() {
        ApplicationContext acx = WebApplicationContextUtils.getWebApplicationContext(context);
        RoleService roleService = acx.getBean(RoleService.class);
        for (Role role : undeloyRoles) {
            roleService.deleteByName(role.getName());
        }
        for(Role role : deployRoles) {
            roleService.save(role);
        }
    }

    /**
     * 刷新权限
     */
    private static void freshPrivilege() {
        ApplicationContext acx = WebApplicationContextUtils.getWebApplicationContext(context);
        PrivilegeService privilegeService = null;
        privilegeService = acx.getBean(PrivilegeService.class);
        if(privilegeService != null) {
            privilegeService.setNotActive();
            for (Module module : modules) {
                for (Privilege topPrivilege : module.getTopPrivileges()) {
                    Privilege priv = privilegeService.getTopPrivilegeByNameAndAction(topPrivilege.getName(), topPrivilege.getAction());
                    if(priv == null) {
                        privilegeService.save(topPrivilege);
                    } else {
                        privilegeService.activePrivilege(priv);
                    }
                }
            }
        }
    }

    // ===================================================================

    /**
     * 模块注册，如果模块配置了就进行注册，否则就不用了
     */
    private static void registerModule() throws FrameException {
        Log.info("Registering System Modules......");
        try {
            // 1, 修改spring的配置文件
            updateSpringConfiguration(false);
            // 2, 修改hibernate的配置文件
            updateHibernateConfiguration(false);
        } catch (DependenceModuleInactiveException e) {
            Log.error(e.getMessage(), e);
            throw e;
        } catch (WritingXmlException e) {
            Log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            Log.error("Register Modules Error.\n" + e.getMessage(), e);
            throw new FrameException("Register Modules Error.", e);
        }
    }

    private static void updateSpringConfiguration(boolean recover) {
        // 获取配置文件
        String config = getInitParameter(//
                Key.FRAME_SPRING_CONFIG.keys(),//
                Key.FRAME_SPRING_CONFIG.values());
        Document doc = getDocument(config);

        for (Module module : modules) {
            if (module.isDeploy()) {
                fixedDependsModule(module);
                if (recover) {
                    Log.info("Remove module[" + module.getName() + "]");
                    recoverSpringConfig(doc, module);
                } else {
                    Log.info("Register module[" + module.getName() + "].");
                    addSpringConfig(doc, module);
                }
            } else {
                Log.info("Remove module[" + module.getName() + "]");
                recoverSpringConfig(doc, module);
            }
        }
        config = ResourceUtils.getResourceRealPath(config, getClassLoader());
        writeToXml(doc, config);
    }

    private static void writeToXml(Document doc, String config) throws WritingXmlException {
        try {
            XMLWriter writer = new XMLWriter(new FileOutputStream(config), OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.close();
        } catch (Exception e) {
            throw new WritingXmlException(e.getMessage(), e);
        }
    }

    private static void addSpringConfig(Document doc, Module module) {
        String path = ResourceUtils.fixedPath(module.getSpringConf());
        if (getElementByResourceAttr(doc, path) == null) {
            doc.getRootElement().addElement("import")//
                    .addAttribute("resource", path);
        }
    }

    private static Object getElementByResourceAttr(Document doc, String path) {
        if (path != null) {
            List<Element> list = doc.getRootElement().elements("import");
            for (Element elt : list) {
                if (path.equals(ResourceUtils.fixedPath(elt.attributeValue("resource")))) {
                    return elt;
                }
            }
        }
        return null;
    }

    private static void recoverSpringConfig(Document doc, Module module) {
        List<Element> list = doc.getRootElement().elements("import");
        for (int i = 0; i < list.size(); ++i) {
            Element elt = list.get(i);
            if (ResourceUtils.fixedPath(module.getSpringConf()).equals(ResourceUtils.fixedPath(elt.attributeValue("resource")))) {
                doc.getRootElement().remove(elt);
                return;
            }
        }
    }

    private static void fixedDependsModule(Module module) {
        if (getModuleNames().containsAll(module.getDepends())) {
            // 如果所依赖的模块都在配置文件中，那么就进一步检测是否依赖的模块要部署
            for (Module m : modules) {
                if (module.getDepends().contains(m.getName()) && !m.isDeploy()) {
                    Log.error("Sorry, Dependence module is Inactive, Reasons: Module configed but deploy is false");
                    throw new DependenceModuleInactiveException("Sorry, Dependence module is Inactive, Reasons: Module configed but deploy is false");
                }
            }
        } else {
            Log.error("Sorry, Dependence module is Inactive, Reasons: Module No configed");
            throw new DependenceModuleInactiveException("Sorry, Dependence module is Inactive, Reasons: Module No configed");
        }
    }

    private static Collection<String> getModuleNames() {
        Collection<String> col = new HashSet<String>();
        for (Module module : modules) {
            col.add(module.getName());
        }
        return col;
    }

    private static void updateHibernateConfiguration(boolean recover) {
        String config = getInitParameter(//
                Key.FRAME_HBM_CONFIG.keys(),//
                Key.FRAME_HBM_CONFIG.values());
        Document doc = getDocument(config);
        for (Module module : modules) {
            if (module.isDeploy()) {
                fixedDependsModule(module);
                if (recover) {
                    Log.info("remove " + module.getName() + "'s hibernate configuration");
                    recoverHibernateConfig(doc, module);
                } else {
                    Log.info("deploy " + module.getName() + "'s hibernate configuration");
                    addHibernateConfig(doc, module);
                }
            } else {
                Log.info("remove " + module.getName() + "'s hibernate configuration");
                recoverHibernateConfig(doc, module);
            }
        }
        config = ResourceUtils.getResourceRealPath(config, getClassLoader());
        writeToXml(doc, config);
    }

    private static void addHibernateConfig(Document doc, Module module) {
        Element arrayElt = getSessionFactoryConfigLocationsArrayElement(doc);
        String path = ResourceUtils.fixedPath(module.getHibernateConf());
        if (getElementByValueTag(arrayElt, path) == null) {
            arrayElt.addElement("value")//
                    .addText(ResourceUtils.fixedPathWithClasspath(module.getHibernateConf()));
        }
    }

    private static Element getElementByValueTag(Element arrayElt, String path) {
        if (path != null) {
            List<Element> list = arrayElt.elements("value");
            for (Element elt : list) {
                if (path.equals(ResourceUtils.fixedPath(elt.getTextTrim()))) {
                    return elt;
                }
            }
        }
        return null;
    }

    private static void recoverHibernateConfig(Document doc, Module module) {
        Element configArray = getSessionFactoryConfigLocationsArrayElement(doc);
        List<Element> list = configArray.elements("value");
        for (int i = 0; i < list.size(); ++i) {
            Element elt = list.get(i);
            if (ResourceUtils.fixedPath(module.getHibernateConf()).equals(ResourceUtils.fixedPath(elt.getTextTrim()))) {
                configArray.remove(elt);
                return;
            }
        }
    }

    private static Element getSessionFactoryConfigLocationsArrayElement(Document doc) {
        Element elt = null;
        List<Element> list = doc.getRootElement().elements("bean");

        for (Element element : list) {
            if ("sessionFactory".equals(element.attributeValue("id"))) {
                elt = element;
                break;
            }
        }

        list = elt.elements("property");
        for (Element element : list) {
            if ("configLocations".equals(element.attributeValue("name"))) {
                elt = element;
                break;
            }
        }

        elt = elt.element("array");
        return elt;
    }

    /**
     * 解析系统模块配置文件，把配置文件中的配置转化成Java对象模型
     */
    private static void parseModule() throws FrameException {
        Log.info("Parsing System Modules......");
        Document doc = getDocument(//
        getInitParameter(//
                Key.FRAME_MODULE_CONFIG.keys(),//
                Key.FRAME_MODULE_CONFIG.values()));
        if (doc != null) {
            List<Element> mudoleList = doc.getRootElement().elements("module");
            for (Element elt : mudoleList) {
                try {
                    Module module = elementToModule(elt);
                    if (module != null) {
                        if (module.isDeploy()) {
                            listeners.addAll(module.getListeners());
                        }
                        modules.add(module);
                    }
                } catch (DuplicateModuleException e) {
                    Log.warn("Duplicate Module have deployed\n" + e.getMessage(), e);
                } catch (Exception e) {
                    Log.error("Parsing System Module Error.\n" + e.getMessage(), e);
                    throw new FrameException(e);
                }
            }
            if(modules.size() == 0) {
                Log.info("Sorry, you have no config modules......");
            }
            Log.info("Finished Parsing System Modules......");
        } else {
            Log.info("Sorry, you have no config modules......");
        }
    }

    private static Module elementToModule(Element elt) throws FrameException {
        try {
            Set<Role> roles = getModuleRoles(elt);
            if ("false".equals(elt.attributeValue("deploy", "false"))) {
                undeloyRoles.addAll(roles);
                return null;
            }
            deployRoles.addAll(roles);
            Module module = new Module();
            String name = elt.attributeValue("name");
            if (isConfigedModule(name)) {
                Log.warn("Duplicate config module[" + name + "], The system will use the first one!");
                return null;
            }
            module.setName(name);
            module.setDeploy(new Boolean(elt.attributeValue("deploy", "false")));
            // 解决模块依赖
            fixedDepends(elt.attributeValue("depends"), module);
            module.setHibernateConf(validateConfigFile(elt.elementText("hibernate-cfg"), module.isDeploy()));
            module.setSpringConf(validateConfigFile(elt.elementText("spring-cfg"), module.isDeploy()));
            module.setPrivilegeConf(validateConfigFile(elt.elementText("privilege-cfg"), module.isDeploy()));

            // 初始化Listener
            if (module.isDeploy()) {
                module.setListeners(elementToListeners(elt));
            }

            // 初始化权限数据
            return initModulePrivilege(module);
        } catch (NullModuleNameException ex) {
            Log.error(ex.getMessage(), ex);
            throw ex;
        } catch (NoSuchConfigException ex) {
            Log.error(ex.getMessage(), ex);
            throw ex;
        } catch (InstancingException ex) {
            Log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * 获取该模块的固有角色
     * @param elt
     * @return
     */
    private static Set<Role> getModuleRoles(Element elt) {
        Set<Role> roles = new HashSet<Role>();
        Element relt = elt.element("roles");
        if(relt != null) {
            List<Element> roleElts = relt.elements("role");
            for (Element roleElt : roleElts) {
                roles.add(new Role(roleElt.attributeValue("name"), roleElt.attributeValue("description"), Role.TYPE_NOT_DEL));
            }
        }
        return roles;
    }

    private static Module initModulePrivilege(Module module) {
        Log.info("Parsing module[" + module.getName() + "] privileges");
        Document doc = getDocument(ResourceUtils.fixedPath(module.getPrivilegeConf()));
        Privilege topPrivilege = null;
        List<Element> topPrivElts = doc.getRootElement().elements("top-privilege");
        for (Element topPrivElt : topPrivElts) {
            // 构造本模块的顶级权限对象
            topPrivilege = new Privilege(topPrivElt.attributeValue("name"), //
                    topPrivElt.attributeValue("action"),//
                    null);
            module.getTopPrivileges().add(topPrivilege);

            List<Element> secondLevelElts = topPrivElt.elements("priv");

            for (Element elt : secondLevelElts) {
                Privilege level2Priv = new Privilege(elt.attributeValue("name"), elt.attributeValue("action"), topPrivilege);
                topPrivilege.getChildren().add(level2Priv);
                List<Element> thridLevelElts = elt.elements("sub-priv");
                for (Element element : thridLevelElts) {
                    Privilege level3Priv = new Privilege(element.attributeValue("name"), element.attributeValue("action"), level2Priv);
                    level2Priv.getChildren().add(level3Priv);
                }
            }
        }
        Log.info("Finished Parsing module[" + module.getName() + "] privileges");
        return module;
    }

    private static Set<ServletContextListener> elementToListeners(Element moduleElt) throws InstancingException {
        Element listenerElt = moduleElt.element("listeners");
        List<Element> elements = null;
        if (listenerElt != null) {
            elements = listenerElt.elements("listener");
        }
        Set<ServletContextListener> listeners = new HashSet<ServletContextListener>();
        if (elements == null) {
            return listeners;
        }
        for (Element elt : elements) {
            try {
                listeners.add((ServletContextListener) Class.forName(elt.attributeValue("class")).newInstance());
            } catch (Exception e) {
                throw new InstancingException("Can not instanced class[" + elt.attributeValue("class") + "]", e);
            }
        }
        return listeners;
    }

    private static String validateConfigFile(String path, boolean deploy) throws NoSuchConfigException {
        if (ResourceUtils.getResourceRealPath(path, getClassLoader()) == null) {
            if (deploy)
                throw new NoSuchConfigException("Config file [" + path + "] is not exists......");
        }
        return ResourceUtils.fixedPath(path);
    }

    private static void fixedDepends(String depends, Module module) {
        if (depends == null || "".equals(depends)) {
            return;
        }
        String[] dependArray = depends.split("[ ;:,-]+");
        module.getDepends().addAll(Arrays.asList(dependArray));
    }

    private static boolean isConfigedModule(String moduleName) throws NullModuleNameException {
        if (moduleName == null || "".equals(moduleName)) {
            throw new NullModuleNameException("NULL Module name exception, you have to provider a name for module");
        }
        for (Module module : modules) {
            if (module.getName().trim().toLowerCase().equals(moduleName.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static Document getDocument(String xmlPath) throws FrameException {
        SAXReader reader = new SAXReader();
        InputStream is = ResourceUtils.getResourceAsStream(xmlPath, getClassLoader());
        if (is != null) {
            try {
                return reader.read(is);
            } catch (DocumentException e) {
                throw new FrameException("Can not parse xml file[" + xmlPath + "]", e);
            }
        }
        return null;
    }

    private static ClassLoader getClassLoader() {
        return ModuleService.class.getClassLoader();
    }

    private static String getInitParameter(String name, String defaultValue) {
        String val = context.getInitParameter(name);
        return val == null ? defaultValue : val;
    }

}
