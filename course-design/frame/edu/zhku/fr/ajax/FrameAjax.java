package edu.zhku.fr.ajax;

import java.util.Date;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.module.AjaxMethod;
import edu.zhku.module.AjaxModule;

/**
 * Frame Ajax Module
 * 
 * @author XJQ
 * date 2013-4-28
 */
@AjaxModule("fr")
public class FrameAjax extends BaseAjax {
    
    @AjaxMethod("serverTime")
    public static Date getServerTime(KVList kvList) {
        return new Date();
    }
}
