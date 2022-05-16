package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/17 19:05
 */

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;//套餐关联的菜品集合

    private String categoryName;//分类名称
}