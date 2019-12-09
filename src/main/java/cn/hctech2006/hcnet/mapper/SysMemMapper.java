package cn.hctech2006.hcnet.mapper;


import cn.hctech2006.hcnet.bean.SysMem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMemMapper {

    int insert(SysMem record);

    SysMem selectByPrimaryKey(Integer id);

    List<SysMem> selectAll();

    int updateByPrimaryKey(SysMem record);

    List<SysMem> findMemByGroup(String group);
    //选择未知组别成员列表
    Page<SysMem> selectAllUnknownGroupMem();
    //获取离开成员名单
    Page<SysMem> selectAllLeaveMem();
    //改变组员组别
    int updateMemGroup(SysMem sysMem);
    //改变组员状态
    int updateMemIsEnabled(SysMem sysMem);
    //设置组员离开时间
    int updateMemLeaveTime(SysMem sysMem);
    //分组获取离开组员名单
    List<SysMem> selectLeaveMemByGroup(String group);

    Page<SysMem> findAllMemNoCondition();
}