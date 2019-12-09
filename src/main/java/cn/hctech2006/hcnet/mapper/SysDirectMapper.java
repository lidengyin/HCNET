package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysDirect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysDirectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDirect record);

    SysDirect selectByPrimaryKey(Integer id);

    List<SysDirect> selectAll();

    int updateByPrimaryKey(SysDirect record);

    List<SysDirect> selectAllByGroup(String group);

    int updateEnabled(SysDirect sysDirect);

    int updateGroup(SysDirect sysDirect);

}