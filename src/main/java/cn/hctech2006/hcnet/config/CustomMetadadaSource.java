package cn.hctech2006.hcnet.config;

import cn.hctech2006.hcnet.bean.SysPrivilege;
import cn.hctech2006.hcnet.bean.SysRole;
import cn.hctech2006.hcnet.service.PrivilegeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 判断当前URL所需用户角色
 */
@Component
public class CustomMetadadaSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PrivilegeService privilegeService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
       String requestUrl = ((FilterInvocation) o).getRequestUrl();
       List<SysPrivilege> privilegeList = privilegeService.getAllPrivilege();
       for(SysPrivilege p: privilegeList){
           if(antPathMatcher.match(requestUrl, p.getPrivilegeUrl()) && p.getRoleList().size() > 0){
               List<SysRole> roleList = p.getRoleList();
               int size = roleList.size();
               String []values = new String[size];
               for(int i = 0; i < size;  i++){
                   values[i] = roleList.get(i).getRoleName();
               }
               return SecurityConfig.createList(values);
           }
       }
       return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
