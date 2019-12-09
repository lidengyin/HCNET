package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysAward;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAwardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysAward record);

    SysAward selectByPrimaryKey(Integer id);

    List<SysAward> selectAll();

    int updateByPrimaryKey(SysAward record);

    Page<SysAward> findAwardByGroup(String group);
}