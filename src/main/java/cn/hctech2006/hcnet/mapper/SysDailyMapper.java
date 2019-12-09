package cn.hctech2006.hcnet.mapper;
import cn.hctech2006.hcnet.bean.SysDaily;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDailyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDaily record);

    SysDaily selectByPrimaryKey(Integer id);

    Page<SysDaily> selectAll();

    int updateByPrimaryKey(SysDaily record);

}