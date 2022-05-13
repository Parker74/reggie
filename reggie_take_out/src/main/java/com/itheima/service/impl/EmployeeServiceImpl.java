package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.Employee;
import com.itheima.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/11 19:13
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements com.itheima.service.EmployeeService {

}
