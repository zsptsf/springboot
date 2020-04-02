package com.zspt_sf.demo.controller;


import com.zspt_sf.demo.common.annotation.LogAnnotation;
import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.common.constant.ResultCode;
import com.zspt_sf.demo.common.result.Result;
import com.zspt_sf.demo.entity.Clbum;
import com.zspt_sf.demo.repository.ClbumRepository;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clbum")
public class ClbumController {

    @Autowired
    private ClbumRepository clbumRepository;

    @GetMapping
    @LogAnnotation(module = "班级", operation = "获取所有班级")
    public Result listClbums() {
        List<Clbum> clbums = clbumRepository.findAll();
        return Result.success(clbums);
    }

    @GetMapping("/findAll/{page}/{size}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "班级", operation = "分页获取所有班级")
    public Result findAllPAGE(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Result r = new Result();
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Clbum> clbums = clbumRepository.findAll(pageRequest);
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(clbums);
        return r;
    }

    @PostMapping("/findByNameLike")
    @LogAnnotation(module = "班级", operation = "获取特定班级")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result Clbumlike(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        String name = (String) jsonObject.get("name");
        List<Clbum> clbums = clbumRepository.findByNameLike("%"+name+"%");
        return Result.success(clbums);
    }

    @PostMapping("/create")
    @LogAnnotation(module = "班级", operation = "添加班级")
    public Result saveClbum(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        String name = (String) jsonObject.get("name");
        Result r = Result.success();
        Clbum clbum = new Clbum();
        clbum.setName(name);
        Clbum clbum1 = clbumRepository.save(clbum);
        r.simple().put("clbum", clbum1);
        return r;
    }

    @PutMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "班级", operation = "修改班级")
    public Result update(@RequestBody Clbum clbum){
        Result r = new Result();
        if (null == clbum) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        Clbum clbum1 = clbumRepository.save(clbum);
        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("clbum", clbum1);
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "班级", operation = "删除班级")
    public Result deleteClbumById(@PathVariable("id") Integer id) {
        Result r = new Result();
        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        clbumRepository.deleteById(id);
        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }
}
