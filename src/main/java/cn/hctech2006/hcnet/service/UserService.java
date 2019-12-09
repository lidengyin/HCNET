package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysUser;
import cn.hctech2006.hcnet.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //HttpServletRequest request;
        String requestCode = request.getParameter("kaptcha");
        String savedCode =(String) request.getSession().getAttribute("kaptcha");
        System.out.println("userService:"+requestCode);
        System.out.println("userService:"+savedCode);
        System.out.println("SessionId:"+request.getSession().getId());
//       if(!requestCode.equals(savedCode)){
//           System.out.println("匹配失败");
//           return null;
//        }
        System.out.println("匹配成功");
        SysUser sysUser = sysUserMapper.loadUserByUserName(s);
        //加载全部延迟数据
        //sysUser.equals(null);
        if(sysUser == null){
            throw new UsernameNotFoundException(s);
        }
        return sysUser;
    }

}
