package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysPro;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysProMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPro record);

    SysPro selectByPrimaryKey(Integer id);

    List<SysPro> selectAll();

    int updateByPrimaryKey(SysPro record);

    List<SysPro> findProByGroup(String group);
}