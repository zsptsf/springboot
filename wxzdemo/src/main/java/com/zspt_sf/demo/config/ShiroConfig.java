package com.zspt_sf.demo.config;


import com.zspt_sf.demo.common.cache.RedisManager;
import com.zspt_sf.demo.shiro.OAuthSessionManager;
import com.zspt_sf.demo.shiro.SessionDAO;
import com.zspt_sf.demo.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //创建ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*
        Shiro内置过滤器,可以实现权限相关的拦截器
        常用过滤器：
            1、anon：无需认证（登录）可以访问
            2、authc：必须认证才可以访问
            3、user：如果使用rememberMe的功能可以直接访问
            4、perms：该资源必须得到资源权限才可以访问
            5、role：该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        //("拦截路径", "过滤器");"/**"通配路径
        //放行
        //filterMap.put("/login", "anon");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        //filterMap.put("/bake/**", "perms[admin]");

        //拦截
        //filterMap.put("/role/**", "authc");

        //修改跳转页面
        //返回json数据，由前端跳转
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权提示页面
        //shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    //创建DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        //关联SessionManager
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    //创建Realm
    @Bean
    public UserRealm getRealm(){
        return new UserRealm();
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        OAuthSessionManager oAuthSessionManager = new OAuthSessionManager();
        oAuthSessionManager.setSessionDAO(sessionDAO);
        return oAuthSessionManager;
    }

    @Bean
    public SessionDAO SessionDAO(RedisManager redisManager) {
        SessionDAO authSessionDAO = new SessionDAO();
        authSessionDAO.setRedisManager(redisManager);
        return authSessionDAO;
    }

    //生命周期
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
