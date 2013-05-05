package edu.zhku.base.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.zhku.base.BaseKey;
import edu.zhku.base.mail.Account;
import edu.zhku.base.mail.ServerConfig;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.expections.ConfigLoadingException;
import edu.zhku.fr.log.Log;
import edu.zhku.utils.ResourceUtils;

/**
 * 加载配置文件，配置包含的内容是（属于系统最小配置）：<br>
 * 1、语言配置 <language></language><br>
 * 2、默认分页显示条数 <default-paging-size></default-paging-size> <br>
 * 3、用户初始化密码 <user-init-pwd></user-init-pwd> <br>
 * 4、服务邮箱配置<mail-services></mail-services><br>
 * 5、指定激活邮箱模版<activate-mail-template></activate-mail-template>
 * 
 * @author XJQ
 * @since 2013-3-13
 */
@SuppressWarnings("unchecked")
public class ConfigLoader {

    private static final Map<Key, Object> container = ConfigCenter.getContainer();

    /**
     * 私有化构造函数，防止用户创建对象
     */
    private ConfigLoader() {
    }

    /**
     * 设置本模块的配置文件位置
     * 
     * @param configLocation
     */
    private static final void setConfigLocation(String configLocation) {
        try {
            configLocation = ResourceUtils.fixedPath(configLocation);
            if (configLocation != null) {
                BaseKey.BASE_CONFIG.value(configLocation);
            } else {
                throw new FileNotFoundException(
                        "web.xml中配置的系统配置文件不存在，使用默认的配置文件["
                                + BaseKey.BASE_CONFIG.values() + "]");
            }
        } catch (FileNotFoundException e) {
            System.err
                    .println("[Error: config file is not found, use the default config file: base-config.xml]");
        }
    }

    /**
     * 初始化系统配置
     */
    public static final void init(String config) throws ConfigLoadingException {
        setConfigLocation(config);
        initConfig();
    }
    
    /**
     * 初始化系统配置
     */
    private static final void initConfig() throws ConfigLoadingException {
        Document doc = null;
        try {
            doc = new SAXReader().read(//
                    ResourceUtils.getResourceAsStream(//
                            BaseKey.BASE_CONFIG.values(),//
                            ConfigLoader.class.getClassLoader()));
            initLanguage(doc);
            initDefaultPagingSize(doc);
            initUserDefaultPwd(doc);
            initMailServices(doc);
            initActivateMailTemplate(doc);
            initSuperAdmins(doc);
            initDefaultRole(doc);
            initInherentRoles(doc); // 固有角色初始化
        } catch (ConfigLoadingException e) {
            throw e;
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ConfigLoadingException("Init module[base] failed", e);
        }
    }

    /**
     * 初始化系统固有角色
     * 
     * @param doc
     */
    private static void initInherentRoles(Document doc) {
        Element elt = doc.getRootElement().element("inherent-roles");
        List<Role> roles = BaseKey.INHERENT_ROLES.value(List.class);
        if (roles == null) {
            roles = new ArrayList<Role>();
            BaseKey.INHERENT_ROLES.value(roles);
        }
        if (elt == null) {
            roles.add(BaseKey.DEFAULT_ROLE.value(Role.class));
            container.put(BaseKey.INHERENT_ROLES, roles);
            return;
        }
        List<Element> elts = elt.elements("inherent-role");
        for (Element element : elts) {
            roles.add(new Role(element.attributeValue("name"), element
                    .attributeValue("description"), Role.TYPE_NOT_DEL));
        }
        container.put(BaseKey.INHERENT_ROLES, roles);
    }

    /**
     * 初始化默认系统的角色
     * 
     * @param doc
     */
    private static void initDefaultRole(Document doc) {
        Element elt = doc.getRootElement().element("default-role");
        String name = elt.attributeValue("name");
        String description = elt.attributeValue("description");
        Role role = BaseKey.DEFAULT_ROLE.value(Role.class);
        if (role == null || role.getName().equals(name)) {
            BaseKey.DEFAULT_ROLE.value(new Role(name, description,
                    Role.TYPE_NOT_DEL));
        }
        container.put(BaseKey.DEFAULT_ROLE,
                BaseKey.DEFAULT_ROLE.value(Role.class));
    }

    /**
     * 初始化超级管理员
     * 
     * @param doc
     */
    private static void initSuperAdmins(Document doc) {
        Element elt = doc.getRootElement().element("super-admins");
        List<User> admins = BaseKey.SUPER_ADMINS.value(List.class);
        if (admins == null) {
            admins = new ArrayList<User>();
            BaseKey.SUPER_ADMINS.value(admins);
        }
        if (elt == null) {
            container.put(BaseKey.SUPER_ADMINS, admins);
            return;
        }
        admins.clear();
        List<Element> adminsEltList = elt.elements("super-admin");
        for (Element element : adminsEltList) {
            User user = new User(element.attributeValue("account"),
                    element.attributeValue("name"),
                    element.attributeValue("password"), User.ACTIVIED,
                    element.attributeValue("email"));
            admins.add(user);
        }
        container.put(BaseKey.SUPER_ADMINS, admins);
    }

    /**
     * 初始化激活邮件模版
     * 
     * @param rootElt
     */
    private static void initActivateMailTemplate(Document doc)
            throws ConfigLoadingException {
        // 直接读取给定文件并将文件名保存下来
        BufferedReader br = null;
        try {
            // 1, 获取文件名
            Element e = doc.getRootElement().element("activate-mail-template");
            String path = e.attributeValue("tempfile");
            String subject = e.attributeValue("subject");

            br = new BufferedReader(//
                    new InputStreamReader(//
                            ResourceUtils.getResourceAsStream(//
                                    path,//
                                    ConfigLoader.class.getClassLoader())));

            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            // 保存设置
            put(BaseKey.ACTIVE_MAIL_CONTENT, sb.toString(), "");
            put(BaseKey.ACTIVE_MAIL_SUBJECT, subject,
                    BaseKey.ACTIVE_MAIL_SUBJECT.values());
        } catch (Exception e) {
            Log.error("Loading activate mail template error\n" + e.getMessage(), e);
            throw new ConfigLoadingException("Loading activate mail template error", e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 初始化邮箱
     * 
     * @param rootElt
     */
    private static void initMailServices(Document doc)
            throws ConfigLoadingException {
        // 初始化之后把相关的信息放到一个MailServerConfig中去
        Set<Account> serverAccounts = new HashSet<Account>();
        List<Element> serverElts = doc.getRootElement().element("mail-servers")
                .elements("server");
        ServerConfig serverConfig = null;
        for (Element serverElt : serverElts) {
            serverConfig = elementToServerConfig(serverElt);
            serverAccounts.addAll(elementsToAccounts(
                    serverElt.elements("account"), serverConfig));
        }
        // 最后把这个放入配置容器中去
        container.put(BaseKey.SERVER_MAIL_ACCOUNT, serverAccounts);
    }

    /**
     * 将elements转换成Account对象集合
     * 
     * @param elements
     * @param serverConfig
     * @return
     */
    private static Set<Account> elementsToAccounts(List<Element> elements,
            ServerConfig serverConfig) {
        Set<Account> accounts = new HashSet<Account>();
        for (Element elt : elements) {
            accounts.add(//
            new Account(//
                    elt.attributeValue("id"),//
                    elt.attributeValue("password"),//
                    serverConfig));
        }
        return accounts;
    }

    /**
     * 将Element对象转换成ServerConfig对象
     * 
     * @param serverElt
     * @return
     */
    private static ServerConfig elementToServerConfig(Element serverElt) {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setType(serverElt.attributeValue("type"));
        serverConfig.setAuth(serverElt.attributeValue("auth"));
        serverConfig.setDebug(serverElt.attributeValue("debug"));
        serverConfig.setDomain(serverElt.attributeValue("domain"));
        serverConfig.setServerHost(serverElt.attributeValue("host"));
        serverConfig.setTransportProtocol(serverElt.attributeValue("tp"));
        return serverConfig;
    }

    /**
     * 初始化用户的初始密码, 默认如果没有配置的话是六个1
     * 
     * @param rootElt
     */
    private static void initUserDefaultPwd(Document doc) {
        String value = doc.getRootElement().elementText("user-init-pwd");
        put(BaseKey.USER_INIT_PWD,//
                value,//
                BaseKey.USER_INIT_PWD.values());
    }

    /**
     * 初始化默认分页的大小，默认是10
     * 
     * @param rootElt
     */
    private static void initDefaultPagingSize(Document doc)
            throws ConfigLoadingException {
        try {
            String value = doc.getRootElement().elementText(
                    "default-paging-size");
            Integer pageSize = Integer.parseInt(value);
            put(BaseKey.DEFAULT_PAGE_SIZE,//
                    pageSize,//
                    BaseKey.DEFAULT_PAGE_SIZE.value(Integer.class));
        } catch (NumberFormatException e) {
            throw new ConfigLoadingException("Init paging size error.", e);
        }
    }

    /**
     * 初始化语言类型，默认是cn
     * 
     * @param rootElt
     */
    private static void initLanguage(Document doc) {
        String value = doc.getRootElement().elementText("language");
        put(BaseKey.LANGUAGE, value, BaseKey.LANGUAGE.values());
    }

    /**
     * 将对象放入容器中
     * 
     * @param key
     * @param value
     * @param defaultValue
     */
    private static void put(Key key, Object value, Object defaultValue) {
        if (value == null || "".equals(value)) {
            container.put(key, defaultValue);
            return;
        }
        container.put(key, value);
    }

    /**
     * 获取指定配置项
     * 
     * @param config
     * @return
     */
    public static final Object getConfig(Key key) {
        return container.get(key);
    }

    public static <T> T getConfig(Key key, Class<T> clazz) {
        return (T) container.get(key);
    }

    public static void setConfig(Key key, Object obj) {
        container.put(key, obj);
    }

    public static Map<Key, Object> getContainer() {
        return container;
    }

}
