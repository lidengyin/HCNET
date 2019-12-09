package cn.hctech2006.hcnet.service;


import cn.hctech2006.hcnet.bean.SysMajor;
import cn.hctech2006.hcnet.mapper.SysMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorService {
    @Autowired
    private SysMajorMapper sysMajorMapper;
    public SysMajor findMajorById(Integer id){
        return sysMajorMapper.selectByPrimaryKey(id);
    }
    public int saveMajor(SysMajor sysMajor){
        int result = sysMajorMapper.insert(sysMajor);
        return result;
    }
    public int updateMajor(SysMajor sysMajor){
        int result = sysMajorMapper.updateByPrimaryKey(sysMajor);
        return result;
    }
}
