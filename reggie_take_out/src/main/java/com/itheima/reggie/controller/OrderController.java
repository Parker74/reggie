package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.commen.BaseContext;
import com.itheima.reggie.commen.R;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private OrderDetailService orderDetailService;
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
    public R<Page<Orders>> page(int page, int pageSize, String number, String beginTime, String endTime){

        Page<Orders> orderPage = new Page<>(page,pageSize);
        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(number != null,Orders::getNumber,number);
        wrapper.between(beginTime != null && endTime != null,Orders::getOrderTime,beginTime,endTime);
        wrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(orderPage,wrapper);

        return R.success(orderPage);
    }

    /**
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> page(Integer page,Integer pageSize){
        log.info("page : {}",page);
        log.info("pageSize : {}",pageSize);

        Long userId = BaseContext.getCurrentId();
        Page<Orders> ordersPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null,Orders::getUserId,userId);

        orderService.page(ordersPage);

        Page<OrdersDto> ordersDtoPage = new Page<>();
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");

        List<OrdersDto> list = new ArrayList<>();

        List<Orders> records = ordersPage.getRecords();
        for (Orders record : records) {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(record,ordersDto);
            String userName = record.getUserName();
            ordersDto.setUserName(userName);
            String consignee = record.getConsignee();
            ordersDto.setConsignee(consignee);
            String phone = record.getPhone();
            ordersDto.setPhone(phone);
            String address = record.getAddress();
            ordersDto.setAddress(address);

            LambdaQueryWrapper<OrderDetail> lqw = new LambdaQueryWrapper<>();
            lqw.eq(OrderDetail::getOrderId,record.getId());
            List<OrderDetail> orderDetails  = orderDetailService.list(lqw);
            ordersDto.setOrderDetails(orderDetails);
            list.add(ordersDto);

        }
        ordersDtoPage.setRecords(list);
        return R.success(ordersDtoPage);
    }
}