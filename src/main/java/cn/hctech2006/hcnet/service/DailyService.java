package cn.hctech2006.hcnet.service;


import cn.hctech2006.hcnet.bean.SysDaily;
import cn.hctech2006.hcnet.mapper.SysDailyMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyService {
    @Autowired
    private SysDailyMapper sysDailyMapper;
    /**
     * 分页查找日常
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<SysDaily> findAllDaily(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysDaily> dailies = sysDailyMapper.selectAll();
        return dailies;
    }
    /**
     * 查看日常明细
     * @param id
     * @return
     */
    public SysDaily findDailyById(Integer id){
        SysDaily sysDaily = sysDailyMapper.selectByPrimaryKey(id);
        return sysDaily;
    }
    /**
     * 修改日常记录
     * @param sysDaily
     * @return
     */
    public int saveDaily(SysDaily sysDaily){
        int result = sysDailyMapper.insert(sysDaily);
        return result;
    }
    /**
     * 更改日常记录
     * @param sysDaily
     * @return
     */
    public int uplateDaily(SysDaily sysDaily){
        int result = sysDailyMapper.updateByPrimaryKey(sysDaily);
        return result;
    }
}
