package cn.hctech2006.hcnet.mapper;


import cn.hctech2006.hcnet.bean.SysInsitu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysInsituMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysInsitu record);

    SysInsitu selectByPrimaryKey(Integer id);

    List<SysInsitu> selectAll();

    int updateByPrimaryKey(SysInsitu record);
}