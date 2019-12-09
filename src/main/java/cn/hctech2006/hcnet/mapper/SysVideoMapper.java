package cn.hctech2006.hcnet.mapper;


import cn.hctech2006.hcnet.bean.SysVideo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysVideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysVideo record);

    //用得到的

    SysVideo selectByPrimaryKey(Integer id);

    Page<SysVideo> selectAll();

    int updateByPrimaryKey(SysVideo record);

    int saveVideo(SysVideo sysVideo);

    Page<SysVideo> selectVideoByAge(String age);

    List<String> selectAllAges();

    Page<SysVideo> selectVideoByGroup(String group);
}