package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectRoleByUserId(Integer userId);

    List<SysRole> selectRoleByPrivilegeId(Integer privilegeId);
}