package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysAward;
import cn.hctech2006.hcnet.mapper.SysAwardMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwardService {
    @Autowired
    private SysAwardMapper sysAwardMapper;
    public Page<SysAward> findAwardByGroup(Integer pageNum, Integer pageSize, String group){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysAward> awards = sysAwardMapper.findAwardByGroup(group);
        return awards;
    }
    public int saveAward(SysAward sysAward){
        int result = sysAwardMapper.insert(sysAward);
        return result;
    }
    public int updateAward(SysAward sysAward){
        int result = sysAwardMapper.updateByPrimaryKey(sysAward);
        return result;
    }
    public SysAward findAwardById(Integer id){
        return sysAwardMapper.selectByPrimaryKey(id);
    }

}

