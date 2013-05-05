package edu.zhku.pj.core.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.zhku.pj.core.HandleStatus;
import edu.zhku.pj.utils.FileUtils;

public class WinCppSourceHandler extends AbstractSourceHandler {
	private String compiler = "g++";
	
	public String getCompiler() {
		return compiler;
	}

	public void setCompiler(String compiler) {
		this.compiler = compiler;
	}

	@Override
	public HandleStatus executeHandle(String sourceStr, String input, String output) {
		// 1, 写入文件
		File sourceFile = FileUtils.writeToFile(this.getWorkFolder(), "main.cpp", sourceStr);
		
		// 2, 编译
		boolean success = this.compile(sourceFile);
		
		HandleStatus status = HandleStatus.Compile_Error;
		System.out.println("success: " + success);
		
		if(success) {
			// 3, 执行
			Process process = executeCmd(this.getWorkFolder() + File.separator + "main" + this.getInput(input), null, sourceFile.getParentFile());
		
			// 4, 处理执行信息
			status = this.handlerProcess(process, output);
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
	
	protected Process executeCmd(String cmd, String[] envp, File dir) {
		try {
			return Runtime.getRuntime().exec(cmd, envp, dir);
		} catch (Exception e) {
			// TODO Exception handle
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private boolean compile(File sourceFile) {
		String cmd = this.compiler + " -o main main.cpp";
		Process process = this.executeCmd(cmd, null, sourceFile.getParentFile());
		String error = this.getString(process.getErrorStream());
		if(error != null && error.indexOf("error") > -1) {
			return false;
		}
		return true;
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
