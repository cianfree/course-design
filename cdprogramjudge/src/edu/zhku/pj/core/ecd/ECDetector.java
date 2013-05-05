package edu.zhku.pj.core.ecd;


/**
 * 而已代码检测器
 * 
 * @author XJQ
 * @date 2013-5-5
 */
public interface ECDetector {
    /**
     * 恶意代码检测
     * @param code
     * @return 如果被认为是恶意代码，那么就返回false，否则返回true
     */
    public boolean detect(String code);
    
    /**
     * 获取本异常代码的描述
     * @return
     */
    public String getDescription();
}
