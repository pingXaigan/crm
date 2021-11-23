package com.aigan.crm.web.listener;

import com.aigan.crm.settings.domain.DicValue;
import com.aigan.crm.settings.service.DicService;
import com.aigan.crm.settings.service.impl.DicServiceImpl;
import com.aigan.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.ResourceBundle;
import java.util.Enumeration;


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


        // --------- Stage2Possibility.properties ----------
        // 解析 properties 中键值对的关系，处理成java中键值对关系（map）
        Map<String,String> pMap = new HashMap<>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();

        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);

            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);
        System.out.println("stage2---写入");
    }
}
