package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysDirect;
import cn.hctech2006.hcnet.mapper.SysDirectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectService {
    @Autowired
    private SysDirectMapper sysDirectMapper;
    public SysDirect findDirectById(Integer id){
        return sysDirectMapper.selectByPrimaryKey(id);
    }
    public List<SysDirect> findAllDirect(){
        return sysDirectMapper.selectAll();
    }
    public List<SysDirect> findAllDirectByGroup(String group){
        return sysDirectMapper.selectAllByGroup(group);
    }
    public int updateDirectGroup(SysDirect sysDirect){
        return sysDirectMapper.updateGroup(sysDirect);
    }
    public int updateDirectEnable(SysDirect sysDirect){
        return sysDirectMapper.updateEnabled(sysDirect);
    }
    public int saveDirect(SysDirect sysDirect){
        return sysDirectMapper.insert(sysDirect);
    }
}
