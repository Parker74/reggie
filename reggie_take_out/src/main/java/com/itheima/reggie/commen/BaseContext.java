package com.itheima.reggie.commen;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/14 18:55
 */

/**
 * 基于Threadlocal封装工具类,用于保存和获取当前登录用户的id(作用范围仅在一条线程之内)
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
