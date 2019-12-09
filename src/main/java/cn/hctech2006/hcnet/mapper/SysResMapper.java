package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysResMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRes record);

    SysRes selectByPrimaryKey(Integer id);

    List<SysRes> selectAll();

    int updateByPrimaryKey(SysRes record);
}