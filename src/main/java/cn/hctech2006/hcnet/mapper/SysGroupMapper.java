package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysGroup record);

    SysGroup selectByPrimaryKey(Integer id);

    List<SysGroup> selectAll();

    int updateByPrimaryKey(SysGroup record);
    //更改是否可用
    int updateByIsEnabled(SysGroup sysGroup);
    //获取全部不可用组别
    List<SysGroup> selectAllunabled();


}