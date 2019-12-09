package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysAchieve;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAchieveMapper {

    int insert(SysAchieve record);

    SysAchieve selectByPrimaryKey(Integer id);

    List<SysAchieve> selectAll();

    int updateByPrimaryKey(SysAchieve record);
}