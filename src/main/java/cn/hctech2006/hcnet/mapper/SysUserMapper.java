package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

    SysUser loadUserByUserName(String userName);
}