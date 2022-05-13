package com.itheima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.commen.R;
import com.itheima.entity.Employee;
import com.itheima.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/11 16:57
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录功能
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //拿到用户数据
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //判断用户名是否存在
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        if (emp == null) {
            return R.error("用户名不存在");
        }

        //判断用户名和密码是否正确
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        if (emp.getStatus() == 0) {
            return R.error("您的账号已被禁用");
        }

        HttpSession session = request.getSession();
        session.setAttribute("employee", emp.getId());
        Object employee1 = session.getAttribute("employee");
        System.out.println(employee1);
        return R.success(emp);
    }

    /**
     * 登出功能
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("employee");
        return R.success("登出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @param request
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("新增员工,员工信息:{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}