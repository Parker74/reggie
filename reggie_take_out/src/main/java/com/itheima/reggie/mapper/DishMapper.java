package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/14 20:13
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
