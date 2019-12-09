package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysPrivilege;
import cn.hctech2006.hcnet.mapper.SysPrivilegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PrivilegeService {
    @Autowired
    @Resource
    private SysPrivilegeMapper sysPrivilegeMapper;
    public List<SysPrivilege> getAllPrivilege(){
        return sysPrivilegeMapper.selectPrivilegeRoleList();
    }
}
