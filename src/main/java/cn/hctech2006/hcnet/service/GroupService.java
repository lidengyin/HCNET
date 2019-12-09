package cn.hctech2006.hcnet.service;


import cn.hctech2006.hcnet.bean.SysGroup;
import cn.hctech2006.hcnet.mapper.SysGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private SysGroupMapper sysGroupMapper;
    //全部
    public List<SysGroup> findAllList(){
        List<SysGroup> sysGroups = sysGroupMapper.selectAll();
        return sysGroups;
    }
    //根据结点
    public SysGroup findGroupById(Integer id){
        SysGroup sysGroup = sysGroupMapper.selectByPrimaryKey(id);
        return sysGroup;
    }
    //修改
    public int updateGroup(SysGroup sysGroup){
        int result = sysGroupMapper.updateByPrimaryKey(sysGroup);
        return  result;
    }
    //保存
    public int saveGroup(SysGroup sysGroup){
        int result = sysGroupMapper.insert(sysGroup);
        return result;
    }
    //修改组别是否可用
    public int updateIsEnabled(SysGroup sysGroup){
        int result = sysGroupMapper.updateByIsEnabled(sysGroup);
        return result;
    }
    //查看所有不显示组别
    public List<SysGroup> findAllunabledGroup(){
        List<SysGroup> sysGroups = sysGroupMapper.selectAllunabled();
        return sysGroups;
    }


}
