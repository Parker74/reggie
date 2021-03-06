package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.commen.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/5/18 8:18
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {

        ValueOperations valueOperations = redisTemplate.opsForValue();

        String code = null;
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            code = ValidateCodeUtils.generateValidateCode(4).toString();
            valueOperations.set(phone, code, 5, TimeUnit.MINUTES);
            log.info(code);
            return R.success("发送信息成功");
        }



        //SMSUtils.sendMessage("瑞吉外卖","17777777777",phone,code);
        return R.error("发送信息失败");
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody Map map, HttpSession session) {
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        Object codeInSession = redisTemplate.opsForValue().get(phone);
        log.info("codeInSession : {}",codeInSession);

        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User user = userService.getOne(wrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            redisTemplate.delete(phone);
            return R.success("登录成功");
        }
        return R.error("登陆失败");
    }
}
