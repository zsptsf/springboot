package com.zspt_sf.demo.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.zspt_sf.demo.common.annotation.LogAnnotation;
import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.common.constant.ResultCode;
import com.zspt_sf.demo.common.result.Result;
import com.zspt_sf.demo.common.util.UserUtils;
import com.zspt_sf.demo.entity.User;
import com.zspt_sf.demo.repository.UserRepository;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @LogAnnotation(module = "用户", operation = "获取所有用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result listUsers() {
        List<User> users = userRepository.findAll();
        return Result.success(users);
    }

    @GetMapping("/findByNameLike")
    @LogAnnotation(module = "用户", operation = "获取所有用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result Userlike(@RequestBody String name) {
        List<User> users = userRepository.findByNameLike("%"+name+"%");
        return Result.success(users);
    }


    @GetMapping("/findAll/{page}/{size}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "分页获取所有用户")
    public Result findAllPAGE(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Result r = new Result();
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<User> users = userRepository.findAll(pageRequest);
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(users);
        return r;
    }

    @GetMapping("/{id}")
    @LogAnnotation(module = "用户", operation = "根据id获取用户")
//    @RequiresRoles(Base.ROLE_ADMIN)
    public Result getUserById(@PathVariable("id") Integer id) {
        Result r = new Result();
        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        User user = userRepository.findById(id).get();
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(user);
        return r;
    }

    @GetMapping("/currentUser")
    //clazz -- 需要进行过滤的POJO class
    //includes -- json输出中希望保存的字段
    @FastJsonView(
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "number","name", "avatar"})})
    @LogAnnotation(module = "用户", operation = "获取当前登录用户")
    public Result getCurrentUser(HttpServletRequest request) {
        Result r = new Result();
        User currentUser = UserUtils.getCurrentUser();
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(currentUser);
        return r;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "添加用户")
    public Result saveUser(@Validated @RequestBody User user) {
        Result r = Result.success();
        r.simple().put("user", userRepository.save(user));
        return r;
    }

    @PostMapping("/update")
//    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "修改用户")
    public Result updateUser(@RequestBody User user) {
        Result r = new Result();
        if (null == user.getId()) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
            return r;
        }
        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("user", userRepository.save(user));
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "删除用户")
    public Result deleteUserById(@PathVariable("id") Integer id) {
        Result r = new Result();
        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        userRepository.deleteById(id);
        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }
}
