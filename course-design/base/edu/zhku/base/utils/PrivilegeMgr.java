package edu.zhku.base.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.zhku.fr.domain.Privilege;
import edu.zhku.fr.service.PrivilegeService;

/**
 * 权限管理工具类<br>
 * 输入参数(key=value)：<br> 
 * a	add/remove	required	动作，添加/移除	<br>
 * f	权限的配置文件，必须
 * 
 * 只需要输入一个参数，即权限文件的路径
 * a=?
 * f=?
 *
 * @author XJQ
 * @since 2013-3-13
 */
@Component("privilegeMgr")
public class PrivilegeMgr {
	private static String action = "add";
	private static String privFile = "privileges.xml";
	
	@Resource
	private PrivilegeService privilegeService;
	
	public static void main(final String[] args) {
		// 1, 检查输入参数合法性
		if(checkArguments(args)) {
			System.out.println("Starting........");
			ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml");
			PrivilegeMgr privilegeMgr = acx.getBean(PrivilegeMgr.class);
			
			List<Privilege> privs = privilegeMgr.readToList();
			
			if("add".equals(action)) {
				privilegeMgr.addPrivilege(privs);
			} else if ("remove".equals(action)) {
				privilegeMgr.removePrivilege(privs);
			}
			System.out.println("Finished..............");
		}
	}
	
	/**
	 * 将xml读入内存，用List存储
	 */
	private List<Privilege> readToList() {
		DocumentBuilder builder = null;
		List<Privilege> list = new ArrayList<Privilege>();
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File(privFile));
			Element rootElt = doc.getDocumentElement();
			
			NodeList moduleList = rootElt.getElementsByTagName("module");
			for (int i=0; i<moduleList.getLength(); ++i) {
				Element module = (Element) moduleList.item(i);
				Privilege topPriv = new Privilege(module.getAttribute("name"), module.getAttribute("action"), null);
				// 获取第二级权限
				NodeList level2List = module.getElementsByTagName("priv");
				for(int j=0; j<level2List.getLength(); ++j) {
					Element level2Elt = (Element) level2List.item(j);
					Privilege level2Priv = new Privilege(level2Elt.getAttribute("name"), level2Elt.getAttribute("action"), topPriv);
					topPriv.getChildren().add(level2Priv);
					// 获取第三级别权限
					NodeList level3List = level2Elt.getElementsByTagName("sub-priv");
					for(int k=0; k<level3List.getLength(); ++k) {
						Element level3Elt = (Element) level3List.item(k);
						Privilege level3Priv = new Privilege(level3Elt.getAttribute("name"), level3Elt.getAttribute("action"), level2Priv);
						level2Priv.getChildren().add(level3Priv);
					}
				}
				list.add(topPriv);
			}
			return list;
		} catch (Exception e) {
			System.err.println("[Error: Invalid xml......Message: " + e.getMessage() + "]");
		}
		return list;
	}

	/**
	 * 添加权限模块
	 */
	@Transactional
	public void addPrivilege(List<Privilege> privs) {
		for(Privilege priv : privs) {
			if(privilegeService.getByName(priv.getName()) == null) {
				privilegeService.save(priv);
			}
		}
	}
	
	/**
	 * 删除权限模块
	 */
	@Transactional
	public void removePrivilege(List<Privilege> privs) {
		Privilege delPriv = null;
		for (Privilege priv : privs) {
			delPriv = this.privilegeService.getByName(priv.getName());
			this.privilegeService.delete(delPriv);
		}
	}

	/**
	 * 检查输入参数
	 * @param args
	 * @return
	 */
	private static boolean checkArguments(final String[] args) {
		for (String arg : args) {
			int index = arg.indexOf("=");
			if(index > -1) {
				String cmd = arg.substring(0, index);
				String val = arg.substring(index + 1, arg.length());
				if("a".equals(cmd)) {
					if(!"add".equals(val) && !"remove".equals(val)) {
						System.err.println("[Error: arguments 'a' must be 'add' or 'remove'.......]");
						return false;
					}
					action = val;
				} else if ("f".equals(cmd)) {
					privFile = val;
				} else {
					System.err.println("[Error: Invalid Arguments......]");
					return false;
				}
			}
		}
		File file = new File(privFile);
		if(! file.exists()) {
			System.err.println("[Error: The privilege file \"" + privFile + "\" is not exists......]");
			return false;
		}
		return true;
	}
}
