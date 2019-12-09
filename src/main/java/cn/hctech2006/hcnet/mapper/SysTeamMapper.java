package cn.hctech2006.hcnet.mapper;


import cn.hctech2006.hcnet.bean.SysTeam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysTeam record);

    SysTeam selectByPrimaryKey(Integer id);

    List<SysTeam> selectAll();

    int updateByPrimaryKey(SysTeam record);
}