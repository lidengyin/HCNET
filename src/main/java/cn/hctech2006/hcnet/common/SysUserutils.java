package cn.hctech2006.hcnet.common;

import cn.hctech2006.hcnet.bean.SysPrivilege;
import cn.hctech2006.hcnet.bean.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 返回当前登录用户的账号信息
 */
public class SysUserutils {
    public static SysUser getCurrentUser(){
        return(SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
