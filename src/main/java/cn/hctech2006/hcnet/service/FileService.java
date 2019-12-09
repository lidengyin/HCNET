package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysFile;
import cn.hctech2006.hcnet.mapper.SysFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private SysFileMapper sysFileMapper;
    public void  saveFile(SysFile sysFile){
        sysFileMapper.saveFile(sysFile);
    }
    public SysFile getFileById(Integer id){
        return sysFileMapper.selectByPrimaryKey(id);
    }

}
