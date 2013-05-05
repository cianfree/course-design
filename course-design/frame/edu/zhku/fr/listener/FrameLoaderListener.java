package edu.zhku.fr.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.ModuleService;
import edu.zhku.fr.expections.FrameException;
import edu.zhku.fr.expections.NoServletContextException;
import edu.zhku.fr.expections.NoSuchKeyException;
import edu.zhku.fr.log.Log;

/**
 * 配置文件加载监听器，用户读取所有的配置，主要是为了模块的扩展
 * 
 * @author XJQ
 * date 2013-4-28
 */
public class FrameLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Log.info("Initializing system module");
        try {
            ModuleService.init(sce);
            super.contextInitialized(sce);
            ModuleService.freshSystem();
            ModuleService.initModuleContext();
            this.initConfigCenter(sce);
            Log.info("Completed initialized system.");
        } catch (FrameException e) {
            Log.info("Initialized falied", e);
            throw e;
        } finally {
            sce.getServletContext().setAttribute(Key.APP_DEPLOY.keys(), ModuleService.hasDeploy());
            ModuleService.clear();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Log.info("Release resource ");
        super.contextDestroyed(sce);
        ModuleService.contextDestory(sce);
    }

    /**
     * 初始化配置
     */
    protected void initConfigCenter(ServletContextEvent context) {
        try {
            Log.info("Initializing system configuration loader......");
            if (context != null) {
                ConfigCenter.init(context);
            }
            Log.info("Finished initializing system configuration loader......");
        } catch (NoServletContextException e) {
            Log.error("Initializing Config center error: " + e.getMessage());
            throw e;
        } catch (NoSuchKeyException e) {
            Log.error("Initializing Config center error: " + e.getMessage());
            throw e;
        } catch (IOException e) {
            Log.error("Initializing Config center error: " + e.getMessage());
            throw new FrameException(e);
        }
    }
}
