package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysInsitu;
import cn.hctech2006.hcnet.mapper.SysInsituMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsituService {
    @Autowired
    private SysInsituMapper sysInsituMapper;

    public List<SysInsitu> findAllInsitu(){
        return sysInsituMapper.selectAll();
    }

    public SysInsitu findInsituById(Integer id){
        SysInsitu sysInsitu = sysInsituMapper.selectByPrimaryKey(id);
        return sysInsitu;
    }

    public  int saveInsitu(SysInsitu sysInsitu){
        int result = sysInsituMapper.insert(sysInsitu);
        return result;
    }

    public int updateInsitu(SysInsitu sysInsitu){
        int result = sysInsituMapper.updateByPrimaryKey(sysInsitu);
        return result;
    }
}
