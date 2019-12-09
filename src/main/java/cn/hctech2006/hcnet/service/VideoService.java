package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysVideo;
import cn.hctech2006.hcnet.mapper.SysVideoMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private SysVideoMapper sysVideoMapper;

    /**
     * 保存视频
     * @param sysVideo
     */
    public void saveVideo(SysVideo sysVideo){
        sysVideoMapper.saveVideo(sysVideo);
    }

    /**
     * 分页查看所有视频
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<SysVideo> selectAllByPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysVideo> sysVideoPage = sysVideoMapper.selectAll();
        return sysVideoPage;
    }

    /**
     * 通过主键查看视频
     * @param id
     * @return
     */
    public SysVideo findVideoById(Integer id){
        return sysVideoMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据年份分页查询视频
     * @param pageNum
     * @param pageSize
     * @param year
     * @return
     */
    public Page<SysVideo> findVideosByAge(Integer pageNum, Integer pageSize, String year){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysVideo> videoPage = sysVideoMapper.selectVideoByAge(year);
        return videoPage;
    }

    /**
     * 查询所有年份
     * @return
     */
    public List<String> findAllAges(){
        List<String> ages = sysVideoMapper.selectAllAges();
        return ages;
    }
    /**
     * 通过主键修改视频
     * @param sysVideo
     */
    public void UpdateVideoById(SysVideo sysVideo){
        sysVideoMapper.updateByPrimaryKey(sysVideo);
    }

    /**
     * 通过组别分类获取视频
     * @param pageNum
     * @param pageSize
     * @param group
     * @return
     */
    public Page<SysVideo> findVideoByGroup(Integer pageNum, Integer pageSize, String group){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysVideo> videoPage = sysVideoMapper.selectVideoByGroup(group);
        return videoPage;
    }
}
