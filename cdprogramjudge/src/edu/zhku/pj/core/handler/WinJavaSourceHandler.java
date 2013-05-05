package edu.zhku.pj.core.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import edu.zhku.pj.core.HandleStatus;
import edu.zhku.pj.utils.FileUtils;

public class WinJavaSourceHandler extends AbstractSourceHandler {
	private String mainClassName = "Main";
	
	public void setMainClassName(String mainClassName) {
		this.mainClassName = mainClassName;
	}
	
	public String getMainClassFileName() {
		return this.mainClassName + ".java";
	}
	
	@Override
	public HandleStatus executeHandle(String sourceStr, String input, String output) {
		// 1, 把那个源代码写入文件
		File sourceFile = FileUtils.writeToFile(this.getWorkFolder(), this.getMainClassFileName(), sourceStr);
		
		// 2, 编译源代码
		boolean success = this.compile(sourceFile);
		
		HandleStatus status = HandleStatus.Compile_Error;
		
		if(success) {
			// 3, 执行
			Process process = executeCmd("java Main" + this.getInput(input), null, sourceFile.getParentFile());
		
			// 4, 处理执行信息
			status = handlerProcess(process, output);
		}
		FileUtils.deleteFile(sourceFile.getParentFile());
		return status;
	}

	private String getInput(String input) {
		return input == null || "".equals(input) ? "" : " " + input;
	}

	protected HandleStatus handlerProcess(Process process, String output) {
		if(process == null || this.getString(process.getErrorStream()) != null) {	// 运行时出错
			return HandleStatus.Runntime_Error;
		}
		String out = this.getString(process.getInputStream());
		if(out == null && output == null) {
			return HandleStatus.Accpeted;
		} else if(out != null && out.equals(output)) {
			return HandleStatus.Accpeted;
		}
		return HandleStatus.Output_Error;
	}

	private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler() ;
	private static final StandardJavaFileManager fileMag = compiler.getStandardFileManager(null, null, null) ;//使用默认

	protected Process executeCmd(String cmd, String[] envp, File dir) {
		try {
			return Runtime.getRuntime().exec(cmd, envp, dir);
		} catch (Exception e) {
			// TODO Exception handle
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings(value= {"rawtypes", "unchecked"})
	private boolean compile(File sourceFile) {
		Iterable it = fileMag.getJavaFileObjects(sourceFile) ;
		CompilationTask ct = compiler.getTask(null, fileMag, null, null, null, it) ;
		boolean success = ct.call();
		try {
			fileMag.close() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 获取给定流的输出
	 * @param is
	 * @return
	 */
	public String getString(InputStream is) {
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
}
