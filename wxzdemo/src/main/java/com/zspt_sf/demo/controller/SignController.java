package com.zspt_sf.demo.controller;

import com.zspt_sf.demo.common.constant.Base;
import net.sf.json.JSONObject;
import com.zspt_sf.demo.common.annotation.LogAnnotation;
import com.zspt_sf.demo.common.constant.ResultCode;
import com.zspt_sf.demo.common.result.Result;
import com.zspt_sf.demo.entity.Sign;
import com.zspt_sf.demo.entity.User;
import com.zspt_sf.demo.repository.SignRepository;
import com.zspt_sf.demo.repository.UserRepository;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignRepository signRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "签到", operation = "获取所有用户签到记录")
    public Result listSigns() {
        List<Sign> signs = signRepository.findAll();
        return Result.success(signs);
    }

    @GetMapping("/findAll/{page}/{size}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "签到", operation = "分页获取所有签到记录")
    public Result findAllPAGE(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Result r = new Result();
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Sign> signs = signRepository.findAll(pageRequest);
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(signs);
        return r;
    }

    @GetMapping("/find/{id}")
    @LogAnnotation(module = "签到", operation = "获取当前用户签到记录")
    public Result listSign(@PathVariable("id") Integer id) {
        Result r = new Result();
        User user = userRepository.findById(id).get();
        if (null == user) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        List<Sign> sign = signRepository.findByUser(user);
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(sign);
        return r;
    }

    @PostMapping("/create")
    @LogAnnotation(module = "用户", operation = "添加当前用户签到记录")
    public Result saveSign(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        String usernumber = (String) jsonObject.get("usernumber");
        String remark = (String) jsonObject.get("remark");
        Result r = Result.success();
        Sign sign = new Sign();
        User user = userRepository.findByNumber(usernumber);
        if (null == user) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        sign.setUser(user);
        //纠正时间，使用TimeZone 设置时区
        //TimeZone time = TimeZone.getTimeZone("ETC/GMT-8");
        //TimeZone.setDefault(time);

        Date date = new Date();
        sign.setSigntime(date);
        sign.setRemark(remark);
        Sign sign1 = signRepository.save(sign);
        r.simple().put("sign", sign1);
        return r;
    }


}
