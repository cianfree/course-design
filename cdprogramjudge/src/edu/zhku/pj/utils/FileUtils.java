package edu.zhku.pj.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具类，用于处理文件，比如文件的删除，文件夹的删除等等
 *
 * @author XJQ
 * @since 2012-12-24
 */
public class FileUtils {
	/**
	 * 删除文件夹，递归删除文件夹下面的所有文件
	 * @param folder
	 */
	public static void deleteFile(File file) {
		if(file.isFile()) {	// 如果是文件就直接进行删除
			file.delete();
			return;
		}
		if(file.isDirectory()) {
			// 如果文件是文件夹，那么就递归进行删除文件
			File[] files = file.listFiles();
			for (File _file : files) {
				deleteFile(_file);
			}
			file.delete();
		}
	}
	
	/**
	 * 将源代码写入指定文件
	 * @param workFolder
	 * @param fileName
	 * @param sourceStr
	 * @return 如果出现异常就返回null
	 */
	public static File writeToFile(String workFolder, String fileName, String sourceStr) {
		File sourceFile = new File(workFolder, fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(sourceFile);
			writer.write(sourceStr);
			writer.flush();
			return sourceFile;
		} catch (IOException e) {
			// TODO 
			System.out.println(e.getMessage());
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO
					System.out.println(e.getMessage());
				}
			}
		}
		return null;
	}
	
	/**
	 * 创建工作文件夹
	 * @param basePath	基路径
	 * @param workFolder	子路径，工作路径
	 * @return
	 */
	public static String createWorkFolder(String basePath, String workFolder) {
		File folder = new File(basePath + File.separator + workFolder);
		if(folder.exists()) {
			deleteFile(folder);
		}
		folder.mkdirs();
		return folder.getAbsolutePath();
	}

	/**
	 * 删除文件夹
	 * @param workFolder
	 */
	public static void deleteFolder(String workFolder) {
		deleteFile(new File(workFolder));
	}
}
