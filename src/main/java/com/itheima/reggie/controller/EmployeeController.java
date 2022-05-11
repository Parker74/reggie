package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


public class EmployeeController {



    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */

    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee) {

        //1、将页面提交的密码password进行md5加密处理

        //2、根据页面提交的用户名username查询数据库

        //3、如果没有查询到则返回登录失败结果


        //4、密码比对，如果不一致则返回登录失败结果


        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果


        //6、登录成功，将员工id存入Session并返回登录成功结果


        /**
         * 员工退出
         * @param request
         * @return
         */

        //清理Session中保存的当前登录员工的id


   return null; }}