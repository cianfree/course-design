package edu.zhku.pj.core;

/**
 * 代码处理信息
 * 
 * @author XJQ
 * @since 2013-3-8
 */
public enum HandleStatus {
	Accpeted("Accepted"),	// 接受
	Compile_Error("Compilation Error"),	// 编译错误
	Output_Error("Output Error"),	// 表示编译通过，单数输出不正确
	Runntime_Error("Rumtime Error"),	// 程序运行时出错
	Evil_Code("Evil Code")
	;
	
	private String key;
	private String appendInfo = "";
	
	HandleStatus(String key) {
	    this.key = key;
	}

    @Override
    public String toString() {
        return this.key + ": " + this.appendInfo;
    }
    
    public void appendInfo(String info) {
        this.appendInfo += info;
    }
}
