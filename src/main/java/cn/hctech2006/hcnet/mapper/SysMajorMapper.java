package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysMajor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMajorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMajor record);

    SysMajor selectByPrimaryKey(Integer id);

    List<SysMajor> selectAll();

    int updateByPrimaryKey(SysMajor record);
}