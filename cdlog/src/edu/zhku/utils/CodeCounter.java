package edu.zhku.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"static-access", "unused"})
public class CodeCounter {
	public static void main(String[] args) {
	    String java = "(.*\\.java$)";
        String js = "(.*\\.js$)";
	    String css = "(.*\\.css$)";
	    String jsp = "(.*\\.jsp$)";
	    //String pattern = java + "|" + js + "|" + css + "|" + jsp;
	    String pattern = java;
	    
		CodeCounter cc = new CodeCounter(pattern, //
		        "E:/git/cdesign/cdajax",//
		        "E:/git/cdesign/cdlog",//
		        "E:/git/cdesign/jsonlib",//
		        "E:/git/cdesign/cdprogramjudge",//
		        "E:/course/course-design"//
		        //"E:/git/cdesign/cdesign"//
		        //"E:/house"
		        );
		cc.startCounter();
		System.out.println("class number: " + cc.getClassCount());
		System.out.println("normalLine: " + cc.getNormalLine());
		System.out.println("commentLine: " + cc.getCommentLine());
		System.out.println("whiteLine: " + cc.getWhiteLine());
		System.out.println("totalLine: " + (cc.getNormalLine() + cc.getCommentLine() + cc.getWhiteLine()));
		
	}

	// ======================================================
	private String pattern = "((.*\\.java$)";
	private Set<File> folders = new HashSet<File>();
	
	private static long normalLine = 0;
	private static long commentLine = 0;
	private static long whiteLine = 0;
	private static long classCount = 0;
	
	CodeCounter(String pattern, String ... folders) {
	    this.pattern = pattern;
	    for(String path : folders) {
	        File file = new File(path);
	        if(file.exists()) {
	            this.folders.add(file);
	        }
	    }
	}
	
	public void startCounter() {
	    for(File file : this.folders) {
    		System.out.println("Project: " + file.getAbsolutePath());
    		tree(file);
	    }
	}
	
	public void tree(File f) {
		File[] chiled = f.listFiles();
		
		for(File file : chiled) {
			if(file.isDirectory()) {
				tree(file);
			} else if (file.getName().matches(this.pattern)){
			    setClassCount(getClassCount() + 1);
				parse(file);
			}
		}
	}
	
	private void parse(File f) {
		BufferedReader br = null;
		boolean comment = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(line.matches("^[\\s&&[^\\n]]*$")) {
					whiteLine ++;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					commentLine ++;
					comment = true;	
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					commentLine ++;
				} else if (true == comment) {
					commentLine ++;
					if(line.endsWith("*/")) {
						comment = false;
					}
				} else if (line.startsWith("//")) {
					commentLine ++;
				} else {
					normalLine ++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static long getNormalLine() {
		return normalLine;
	}

	public static long getCommentLine() {
		return commentLine;
	}

	public static long getWhiteLine() {
		return whiteLine;
	}

    public static long getClassCount() {
        return classCount;
    }

    public static void setClassCount(long classCount) {
        CodeCounter.classCount = classCount;
    }

}
