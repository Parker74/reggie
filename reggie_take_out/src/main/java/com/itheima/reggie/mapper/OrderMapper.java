package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/19 11:15
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}