package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFile record);

    SysFile selectByPrimaryKey(Integer id);

    List<SysFile> selectAll();

    int updateByPrimaryKey(SysFile record);

    int saveFile(SysFile sysFile);
}