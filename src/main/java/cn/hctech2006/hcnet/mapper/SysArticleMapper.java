package cn.hctech2006.hcnet.mapper;


import cn.hctech2006.hcnet.bean.SysArticle;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysArticle record);

    SysArticle selectByPrimaryKey(Integer id);

    List<SysArticle> selectAll();

    int updateByPrimaryKey(SysArticle record);

    Page<SysArticle> findArticlesByAge(String year);

    List<String> selectAllAges();
}