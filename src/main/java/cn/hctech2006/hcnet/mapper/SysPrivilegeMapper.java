package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysPrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysPrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPrivilege record);

    SysPrivilege selectByPrimaryKey(Long id);

    List<SysPrivilege> selectAll();

    int updateByPrimaryKey(SysPrivilege record);

    List<SysPrivilege> selectPrivilegeRoleList();

    List<SysPrivilege> privilegeTree();

    SysPrivilege selectPrivilegeByParentId(Integer parentId);
}