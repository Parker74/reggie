package com.itheima.commen;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/14 20:32
 */

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException{
    public CustomException (String message){
        super(message);
    }
}
