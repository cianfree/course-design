package edu.zhku.pj.core.ecd.dector.java;

import edu.zhku.pj.core.ecd.ECDetector;

/**
 * 线程代码检测
 * 
 * @author XJQ
 * @date 2013-5-5
 */
public class ThreadDetector implements ECDetector {

    @Override
    public boolean detect(String code) {
        return false;
    }

    @Override
    public String getDescription() {
        return "Thread code are not allow in this system.";
    }

}
