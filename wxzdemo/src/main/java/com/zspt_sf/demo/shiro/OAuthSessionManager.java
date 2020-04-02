package com.zspt_sf.demo.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 从请求头获取token(临时令牌)
 *
 * @author zspt_sf
 */
public class OAuthSessionManager extends DefaultWebSessionManager {

    /**
     * 设置会话时间
     */
    public static final String OAUTH_TOKEN = "Oauth-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";// 请求sessionId

    public OAuthSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头信息
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String id = httpRequest.getHeader(OAUTH_TOKEN);
        //在哪里获取
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
        //id是什么
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
        //是否要验证
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        return id;
    }
}

