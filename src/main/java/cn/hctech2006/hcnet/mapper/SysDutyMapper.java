package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysDuty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysDutyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDuty record);

    SysDuty selectByPrimaryKey(Long id);

    List<SysDuty> selectAll();

    int updateByPrimaryKey(SysDuty record);
}