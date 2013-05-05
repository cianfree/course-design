package edu.zhku.poj.client;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.zhku.pj.core.HandleStatus;
import edu.zhku.pj.core.SourceHandler;
import edu.zhku.pj.core.SourceHandlerManager;

/**
 * 客户端测试
 * 
 * @author XJQ
 * @since 2013-3-8
 */
@SuppressWarnings("unused")
public class Client {
	private ApplicationContext acx = null;

	@Before
	public void ready() {
		// Ready
		acx = new ClassPathXmlApplicationContext("spring-languages.xml");
		Assert.assertNotNull(acx);
	}

	@Test
	public void testExecute() {
		SourceHandlerManager shm = acx.getBean(SourceHandlerManager.class);
		
		String sourceStr = getJavaSource();
		//String sourceStr = shm.getLanguages()[0].getTemplate();
		String language = "Java";
		String input = "";
		String output = "HelloWorld";

		Assert.assertNotNull(shm);

		/*
		for(Language lan : shm.getLanguages()) {
			System.out.println(lan.getName() + ": \n" + lan.getTemplate());
			System.out.println("--------------------------------------------------");
		}
		*/
		
		SourceHandler handler = shm.getSourceHandler(language);
		Assert.assertNotNull(handler);
		
		// 设置个人工作目录
		String path = handler.getWorkFolder() + File.separator + "cian";
		handler.setWorkFolder(path);

		HandleStatus info = handler.handle(sourceStr, input, output);
		
		System.out.println(info);

	}

	private String getJavaSource() {
		return "public class Main {\n" + //
				"	public static void main(String[] args) {\n" + //
				"		System.out.println(\"HelloWorld\");\n" + //
				"	}\n" + //
				"}\n";
	}

	private String getCPPSource() {
		return "#include <iostream>\n" +
				"using namespace std;\n" +
				"int main(int argc, char* argv[]) {\n" +
				"	cout<<\"HelloWorld\"<<endl;\n" +
				"	return 0;\n" +
				"}";
	}

	@After
	public void release() {
		// Release resource
	}

}
