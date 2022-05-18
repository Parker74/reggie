package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/14 20:03
 */
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void deleteWithDish(List<Long> ids);

    void updateStatus(Integer status,List<Long> ids);
}
