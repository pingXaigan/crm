package com.aigan.crm.web.listener;

import com.aigan.crm.settings.domain.DicValue;
import com.aigan.crm.settings.service.DicService;
import com.aigan.crm.settings.service.impl.DicServiceImpl;
import com.aigan.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.List;
import java.util.Set;

/**
 * @author aigan
 * @date 2021/11/9 16:52
 */
public class SysInitListener implements ServletContextListener {

    /**
     * 该方法是监听上下文域对象的方法，服务器启动，上下文域对象创建完毕后，马上执行
     * @param sce
     *              该参数能取得监听的对象
     *              监听的是什么对象，就可以通过该参数取得什么对象
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("-----------上下文域对象已创建！------------");
        System.out.println("服务器开始处理数据字典……");

        ServletContext application = sce.getServletContext();

        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

        // 业务层保存数据
        Map<String, List<DicValue>> map = dicService.getAll();

        // 将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();

        for(String key : set) {
            application.setAttribute(key,map.get(key));
        }
        System.out.println("数据字典已写入全局作用域对象！");
    }
}
