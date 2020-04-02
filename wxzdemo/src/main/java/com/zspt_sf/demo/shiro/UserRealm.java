package com.zspt_sf.demo.shiro;


import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.entity.User;
import com.zspt_sf.demo.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
//自定义realm


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加授权资源字符串
        //info.addStringPermission("admin");

        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User)subject.getPrincipal();
//        User dbUser = userRepository.findById(user.getId()).get();
        //赋予用户权限
//        info.addStringPermission(dbUser.getRole());

        User user = (User) principalCollection.getPrimaryPrincipal();
        //User user = userRepository.findByNumber(number);
        Set<String> roles = new HashSet<String>();

        //简单处理   只有admin一个角色
        if (user.getRole().getName().equals("admin")) {
            System.out.println(user.getRole().getName());
            roles.add(Base.ROLE_ADMIN);
        }
        info.setRoles(roles);

        return info;
    }


    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //编写shiro判断逻辑
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        User user = userRepository.findByPhone(token.getUsername());
        String token = (String) authenticationToken.getPrincipal();
        User user = userRepository.findByNumber(token);
        //判断用户名
        if (user == null){
            //用户名不存在
            return null;//shiro底层会抛出UnKnownAccountException
        }
        //判断密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
