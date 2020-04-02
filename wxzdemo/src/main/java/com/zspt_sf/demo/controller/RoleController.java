package com.zspt_sf.demo.controller;



import com.zspt_sf.demo.common.annotation.LogAnnotation;
import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.common.constant.ResultCode;
import com.zspt_sf.demo.common.result.Result;
import com.zspt_sf.demo.entity.Role;
import com.zspt_sf.demo.repository.RoleRepository;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/findAll/{page}/{size}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "权限", operation = "分页获取权限角色")
    public Result findAllPAGE(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Result r = new Result();
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Role> roles = roleRepository.findAll(pageRequest);
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(roles);
        return r;
    }

    @PostMapping("/create")
    @LogAnnotation(module = "权限", operation = "添加权限")
    public Result saveRole(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        String name = (String) jsonObject.get("name");
        Result r = Result.success();
        Role role = new Role();
        role.setName(name);
        Role role1 = roleRepository.save(role);
        r.simple().put("role", role1);
        return r;
    }

    @PutMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "权限", operation = "修改权限")
    public Result update(@RequestBody Role role){
        Result r = new Result();
        if (null == role) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        Role role1 = roleRepository.save(role);
        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("role", role1);
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "权限", operation = "删除权限")
    public Result deleteRoleById(@PathVariable("id") Integer id) {
        Result r = new Result();
        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }
        roleRepository.deleteById(id);
        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }
}
