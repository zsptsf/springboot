package com.zspt_sf.demo.controller;


import com.zspt_sf.demo.common.annotation.LogAnnotation;
import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.common.constant.ResultCode;
import com.zspt_sf.demo.common.result.Result;
import com.zspt_sf.demo.entity.Role;
import com.zspt_sf.demo.entity.User;
import com.zspt_sf.demo.repository.RoleRepository;
import com.zspt_sf.demo.repository.UserRepository;
import com.zspt_sf.demo.shiro.OAuthSessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    @LogAnnotation(module = "登录", operation = "登录")
    public Result login(@RequestBody User user) {//@RequestBody这个标签在post 、put 方法中用于接收json格式的数据
        Result r = new Result();
        executeLogin(user.getNumber(),user.getPassword(),r);
        return r;
    }

    //登录
    private void executeLogin(String number, String password, Result r) {
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(number, password);
        //执行登录方法
        try {
            //登录成功
            subject.login(token);

            User currentUser = userRepository.findByNumber(number);
            subject.getSession().setAttribute(Base.CURRENT_USER, currentUser);
            r.setResultCode(ResultCode.SUCCESS);
            r.simple().put(OAuthSessionManager.OAUTH_TOKEN, subject.getSession().getId());
        } catch (UnknownAccountException e) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
        } catch (LockedAccountException e) {
            r.setResultCode(ResultCode.USER_ACCOUNT_FORBIDDEN);
        } catch (AuthenticationException e) {
            r.setResultCode(ResultCode.USER_LOGIN_ERROR);
        } catch (Exception e) {
            r.setResultCode(ResultCode.ERROR);
        }

    }

    @PostMapping("/register")
    @LogAnnotation(module = "注册", operation = "注册")
    public Result register(@RequestBody User user) {

        Result r = new Result();
        //判断用户名是否重复
        User temp = userRepository.findByNumber(user.getNumber());
        if (null != temp) {
            r.setResultCode(ResultCode.USER_HAS_EXISTED);
            return r;
        }


        String number = user.getNumber();
        String password = user.getPassword();
        Role role = roleRepository.findByName("guest");
        user.setRole(role);
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        User user1 = userRepository.save(user);

        if (user1 != null) {
            executeLogin(number, password, r);//登录
        } else {
            r.setResultCode(ResultCode.USER_Register_ERROR);
        }
        return r;
    }

//    @PostMapping("/login")
//    public String login(String username, String password) {
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            return "redirect:admin";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:index";
//        }
//    }
//
//
    @GetMapping("/logout")
    @LogAnnotation(module = "退出", operation = "退出")
    public Result logout() {

        Result r = new Result();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

}
