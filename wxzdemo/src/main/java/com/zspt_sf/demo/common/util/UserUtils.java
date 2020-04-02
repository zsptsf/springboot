package com.zspt_sf.demo.common.util;



import com.zspt_sf.demo.common.constant.Base;
import com.zspt_sf.demo.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * @author zspt_sf
 */
//获取当前登录用户
public class UserUtils {

    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Base.CURRENT_USER);
        return user;
    }
}
