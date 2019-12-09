package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysAchieve;
import cn.hctech2006.hcnet.mapper.SysAchieveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchieveService {
    @Autowired
    private SysAchieveMapper sysAchieveMapper;
    /**
     * 获取所有成果
     * @return
     */
    public List<SysAchieve> findAllAchieve(){
        return sysAchieveMapper.selectAll();
    }

    /**
     * 上传成果
     * @param sysAchieve
     * @return
     */
    public int saveAchieve(SysAchieve sysAchieve){
        int result = sysAchieveMapper.insert(sysAchieve);
        return result;
    }

    /**
     * 更新成果
     * @param sysAchieve
     * @return
     */
    public int updateAchieve(SysAchieve sysAchieve){
        int result = sysAchieveMapper.updateByPrimaryKey(sysAchieve);
        return result;
    }

    public SysAchieve findAchieveById(Integer id){
        SysAchieve sysAchieve = sysAchieveMapper.selectByPrimaryKey(id);
        return sysAchieve;
    }
}
