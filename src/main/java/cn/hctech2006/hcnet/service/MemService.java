package cn.hctech2006.hcnet.service;


import cn.hctech2006.hcnet.bean.SysMem;
import cn.hctech2006.hcnet.mapper.SysMemMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemService {
    @Autowired
    private SysMemMapper sysMemMapper;
    //分组获得在职状态成员
    public List<SysMem> findMemByGroup(String group){
        return sysMemMapper.findMemByGroup(group);
    }
    public SysMem findMemById(Integer id){
        return sysMemMapper.selectByPrimaryKey(id);
    }
    public int saveMem(SysMem sysMem){
        int result = sysMemMapper.insert(sysMem);
        return result;
    }
    public int updateMem(SysMem sysMem){
        int result = sysMemMapper.updateByPrimaryKey(sysMem);
        return result;
    }
    //获取所有无组别人员的列表
    public Page<SysMem> findAllUnknownGroupMemByPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysMem> sysMems = sysMemMapper.selectAllUnknownGroupMem();
        return sysMems;
    }
    //获取所有离开状态的成员列表
    public Page<SysMem> findAllLeaveMem(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysMem> sysMems = sysMemMapper.selectAllLeaveMem();
        return sysMems;
    }
    //分组获得离开成员的列表
    public List<SysMem> findLeaveMemByGroup(String group){
        List<SysMem> sysMems = sysMemMapper.selectLeaveMemByGroup(group);
        return sysMems;
    }
    //更新成员组别
    public int updateMemGroup(SysMem sysMem){
        int result = sysMemMapper.updateMemGroup(sysMem);
        return result;
    }
    //更新成员状态
    public int updateMemIsEnabled(SysMem sysMem){
        int result = sysMemMapper.updateMemIsEnabled(sysMem);
        return result;
    }
    //更新成员离职时间
    public int updateMemLeaveTime(SysMem sysMem){
        int result = sysMemMapper.updateMemLeaveTime(sysMem);
        return result;
    }
    //无条件获取所有成员
    public Page<SysMem> findAllMem(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Page<SysMem> sysMems = sysMemMapper.findAllMemNoCondition();
        return sysMems;
    }
    //根据ID获得成员
//    public SysMem findMemById(Integer id){
//        SysMem sysMem = sysMemMapper.selectByPrimaryKey(id);
//        return sysMem;
//    }




}
