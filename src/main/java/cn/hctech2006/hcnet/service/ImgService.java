package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.mapper.SysImgMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgService {

    @Autowired
    private SysImgMapper sysImgMapper;
    public void saveImg(SysImg sysImg){
        sysImgMapper.saveImg(sysImg);
    }
    public SysImg getImgById(Integer id){
        return sysImgMapper.selectByPrimaryKey(id);
    }
    public Page<SysImg> getImgByPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return sysImgMapper.selectAll();
    }
}
