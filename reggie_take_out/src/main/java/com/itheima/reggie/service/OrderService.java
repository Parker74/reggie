package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/19 11:16
 */
public interface OrderService extends IService<Orders> {

    void submit(Orders orders);

}