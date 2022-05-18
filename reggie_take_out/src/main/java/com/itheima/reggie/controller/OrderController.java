package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.commen.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/19 11:16
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 后端订单明细
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(Integer page, Integer pageSize, Long number, String beginTime, String endTime){
        Page<Orders> orderPage = new Page<>(page,pageSize);

        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Orders::getNumber,number);
        wrapper.between(Orders::getOrderTime,beginTime,endTime);
        wrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(orderPage,wrapper);

        return R.success(orderPage);
    }
}