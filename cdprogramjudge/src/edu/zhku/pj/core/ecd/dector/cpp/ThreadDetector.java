package edu.zhku.pj.core.ecd.dector.cpp;

import edu.zhku.pj.core.ecd.ECDetector;

/**
 * C++语言的线程代码检测
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
