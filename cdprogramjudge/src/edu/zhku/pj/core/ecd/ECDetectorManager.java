package edu.zhku.pj.core.ecd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 恶意代码检测器管理
 * 
 * @author XJQ
 * @date 2013-5-5
 */
public class ECDetectorManager {

    /**
     * 存储结果，每种语言的验证机制是不同的
     */
    private static final Map<String, List<ECDetector>> detecters = new HashMap<String, List<ECDetector>>();
    
    private static boolean isInit = false;
    
    /**
     * 单例
     */
    private ECDetectorManager(){}
    
    /**
     * 初始化，并注册检测器
     * @param config
     */
    public static void init(String config) {
        detecters.clear();
        config = config==null ? "detectors.properties" : config;
        Properties pro = new Properties();
        try {
            pro.load(ECDetectorManager.class.getResourceAsStream("/" + config));
        } catch (IOException e) {
            try {
                pro.load(ECDetectorManager.class.getResourceAsStream("/detectors.properties"));
            } catch (IOException ex) {
                throw new RuntimeException("You have not config any evil code detector, there is some dangerous when in code judge.");
            }
        }
        try {
            Set<Entry<Object, Object>> sets = pro.entrySet();
            for (Entry<Object, Object> item : sets) {
                String[] classes = item.getValue().toString().split(",");
                List<ECDetector> ecds = new ArrayList<ECDetector>();
                for(String path : classes) {
                    ecds.add(ECDetector.class.cast(Class.forName(path).newInstance()));
                }
                detecters.put(item.getKey().toString(), ecds);
            }
        } catch (Exception e) {
            throw new RuntimeException("Init ECDectorManager Error, You have not config any evil code detector, there is some dangerous when in code judge.");
        }
        isInit = true;
    }
    
    /**
     * 代码检测
     * @param code
     * @param lan
     * @return 通过检测是null，没有通过是返回ECDetector的描述
     */
    public static String detect(String code, String lan) {
        if(!isInit) init(null);
        // 获取该语言的代码检测器
        List<ECDetector> ecds = detecters.get(lan);
        if(ecds == null) return null;
        for (ECDetector ecDetector : ecds) {
            if(ecDetector.detect(code)) {
                continue ;
            } else {
                return ecDetector.getDescription();
            }
        }
        return null;
    }
    
    /**
     * 只进行检测，不返回描述信息
     * @param code
     * @return 如果合法就返回true，不合法就返回false
     */
    public static boolean isValid(String code, String lan) {
        return detect(code, lan) == null ? true : false;
    }
    
    /**
     * 注册detector
     * @param detector
     * @param lan
     * @return 如果lan是空就返回false, 如果原来没有这个语言就新建一个，如果detector是空就返回false
     */
    public static boolean registerDetector(String lan, ECDetector ... ecds) {
        if(ecds == null || lan == null || lan.length( ) == 0) return false;
        List<ECDetector> ecdsList = detecters.get(lan);
        if(ecdsList == null) {
            ecdsList = new ArrayList<ECDetector>();
        }
        for(ECDetector ecd : ecds) {
            ecdsList.add(ecd);
        }
        return true;
    }
}
