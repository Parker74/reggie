package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/11 16:58
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
