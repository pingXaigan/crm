package com.aigan.settings.test;

import com.aigan.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author aigan
 * @date 2021/10/19 23:26
 */
public class Test1 {
    public static void main(String[] args) {
        System.out.println("hello");

        // String expireTime = "2021-1-29";
        // Date date = new Date();
        // String  pattern = "yyyy-MM-dd HH:mm:ss";
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        // String str = simpleDateFormat.format(date);
        // System.out.println(str);
        //
        // int count = expireTime.compareTo(str);
        // System.out.println(count);

        // String lockState = "0";
        // if("0".equals(lockState)) System.out.println("zhanghaosuodingle");

        // String ip = "111.11.91.1";
        // String allowIps = "333.333.33.3,111.11.1.1";
        // if(allowIps.contains(ip)){
        //     System.out.println("youxiao");
        // }else {
        //     System.out.println("wuxiao");
        // }

        String pwd = "123";

        System.out.println(MD5Util.getMD5(pwd));
    }
}
