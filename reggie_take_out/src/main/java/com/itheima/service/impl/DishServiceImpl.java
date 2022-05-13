package com.itheima.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.Dish;
import com.itheima.mapper.DishMapper;
import com.itheima.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/14 20:04
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}
