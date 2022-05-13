package com.itheima.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.commen.R;
import com.itheima.entity.Employee;
import com.itheima.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        return R.success(emp);
    }

    /**
     * 登出功能
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
     * @param employee 封装数据的对象
     * @param request  请求对象
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response) {
        log.info("新增员工,员工信息:{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page     当前查询页码
     * @param pageSize 每页展示记录数
     * @param name     员工姓名 - 可选参数
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(required = false) String name) {

        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        wrapper.orderByDesc(Employee::getCreateTime);
        employeeService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查找员工信息
     * @param id
     * @return+
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("未查询到任何信息");
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping("/{id}")
    public R<String> updateById(HttpServletRequest request,@RequestBody Employee employee){
        long id = Thread.currentThread().getId();
        log.info("线程id:{}",id);
        employeeService.updateById(employee);
        return R.success("修改员工信息成功");
    }
}