package edu.zhku.poj.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Test;

public class CompilerTest {

	/*
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<JavaFileObject>();
		
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.print(
				"public class HelloWorld {" +
				"	public static void main(String[] args) {" +
				"		System.out.println(\"This is in another file\");" +
				"	}" +
				"}");
		out.close();
//		String code = "public class HelloWorld {" +
//				"	public static void main(String[] args) {" +
//				"		System.out.println(\"This is in another file\");" +
//				"	}" +
//				"}";
		String code = writer.toString();
		
		JavaFileObject file = new JavaSourceFromString("HelloWorld", code);
		
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
		CompilationTask task = compiler.getTask(null, null, diagnosticCollector, null, null, compilationUnits);
		
		boolean success = task.call();
		for (Diagnostic diagnostic : diagnosticCollector.getDiagnostics()) {
			System.out.println(diagnostic.getCode());
			System.out.println(diagnostic.getKind());
			System.out.println(diagnostic.getPosition());
			System.out.println(diagnostic.getStartPosition());
			System.out.println(diagnostic.getEndPosition());
			System.out.println(diagnostic.getSource());
			System.out.println(diagnostic.getMessage(null));

		}
		System.out.println("Success: " + success);
		if(success) {
			Class.forName("HelloWorld")//
				.getDeclaredMethod("main", new Class[] { String[].class })//
				.invoke(null, new Object[] { null });
		}
		
		
	}
	
	public static class JavaSourceFromString extends SimpleJavaFileObject {
		final String code;

		public JavaSourceFromString(String name, String code) {
			super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.code = code;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return code;
		}
	}
	*/
	
	public static void main(String[] args) throws Exception {
		String source = "public class Main { " + "public static void main(String[] args) {"
						+ "System.out.println(\"Hello World!: \" + args.length + \": \" + args[0]);" + "} " + "}";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		StringSourceJavaObject sourceObject = new CompilerTest.StringSourceJavaObject("Main",
						source);
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
		
		 // 获取编译类根路径，不然会报找不到类的错误
		String path = Class.class.getClass().getResource("/").getPath();
		Iterable< String> options = Arrays.asList("-d", path); 
		
		// 增加options参数
		// CompilationTask task = compiler.getTask(null, fileManager, null, null, null, fileObjects);
		CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);

		boolean result = task.call();

		if (result) {
			System.out.println("编译成功。");

			ClassLoader loader = CompilerTest.class.getClassLoader();
			try {
				Class<?> clazz = loader.loadClass("Main");
				//Process process = executeCmd("java Main", null, new File("."));
				//System.out.println(getString(process.getErrorStream()));
				
				Method method = clazz.getMethod("main", String[].class);
				// 修改调用参数，不然会报参数个数不对
				// Object value = method.invoke(null, new Object[] {});
				Object value = method.invoke(null, new Object[]{new String[]{"cian asd"}});
				System.out.println(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class StringSourceJavaObject extends SimpleJavaFileObject {

		private String content = null;

		public StringSourceJavaObject(String name, String content) throws URISyntaxException {
			super(URI.create("file:///" + name.replace('.', '/') + Kind.SOURCE.extension),
							Kind.SOURCE);
			
			this.content = content;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
			return content;
		}
	}
	
	/**
	 * 执行一个命令行下面的命令
	 * @param cmd	要执行的命令
	 * @param envp	环境变量，name=value格式
	 * @param dir	工作目录
	 * @return	如果抛出异常就返回null，否则返回一个Process对象
	 */
	public static Process executeCmd(String cmd, String[] envp, File dir) {
		try {
			return Runtime.getRuntime().exec(cmd, envp, dir);
		} catch (IOException e) {
			// TODO Exception handle
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取给定流的输出
	 * @param is
	 * @return
	 */
	public static String getString(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Exception handle
			System.out.println(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Exception handle
				System.out.println(e.getMessage());
			}
		}
		return "".equals(sb.toString()) ? null : sb.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testCompile() {
		File file = new File("D:/Hello.java");
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler() ;
		StandardJavaFileManager fileMag = jc.getStandardFileManager(null, null, null) ;//使用默认
		Iterable it = fileMag.getJavaFileObjects(file) ;
		CompilationTask ct = jc.getTask(null, fileMag, null, null, null, it) ;
		ct.call() ;
		try {
			fileMag.close() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
