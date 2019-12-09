package cn.hctech2006.hcnet.mapper;

import cn.hctech2006.hcnet.bean.SysImg;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysImgMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysImg record);

    SysImg selectByPrimaryKey(Integer id);

    Page<SysImg> selectAll();

    int updateByPrimaryKey(SysImg record);

    int saveImg(SysImg sysImg);
}