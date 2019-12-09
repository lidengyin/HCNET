package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysPro;
import cn.hctech2006.hcnet.mapper.SysProMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProService {
    @Autowired
    private SysProMapper sysProMapper;
    public List<SysPro> findAllPro(){
        return sysProMapper.selectAll();
    }
    public List<SysPro> findProByGroup(String group){
        return sysProMapper.findProByGroup(group);
    }
    public SysPro findProByid(Integer id){
        return sysProMapper.selectByPrimaryKey(id);
    }
    public int savePro(SysPro sysPro){
        int result = sysProMapper.insert(sysPro);
        return result;
    }
    public int updatePro(SysPro sysPro){
        int result = sysProMapper.updateByPrimaryKey(sysPro);
        return result;
    }

}
