package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysRes;
import cn.hctech2006.hcnet.mapper.SysResMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResService {
    @Autowired
    private SysResMapper sysResMapper;
    public List<SysRes> findAllRes(){
        return sysResMapper.selectAll();
    }
    public int saveRes(SysRes sysRes){
        int result = sysResMapper.insert(sysRes);
        return result;
    }
    public int updateRes(SysRes sysRes){
        int result = sysResMapper.updateByPrimaryKey(sysRes);
        return result;
    }
    public SysRes findResById(Integer id){
        return sysResMapper.selectByPrimaryKey(id);
    }
}
