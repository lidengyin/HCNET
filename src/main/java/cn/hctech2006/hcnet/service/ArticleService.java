package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysArticle;
import cn.hctech2006.hcnet.mapper.SysArticleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ArticleService {
    @Autowired
    private SysArticleMapper sysArticleMapper;

//    /**
//     * 分页获取所有的文章
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    public Page<SysArticle> findByPaging(Integer pageNum, Integer pageSize){
//        PageHelper.startPage(pageNum, pageSize);
//        Page<SysArticle> articleList = sysArticleMapper.findAllByPaging();
//        return articleList;
//    }

    /**
     * 根据年份分页查询
     * @param pageNum
     * @param pageSize
     * @param year
     * @return
     */
    public Page<SysArticle> findArticleByAge(Integer pageNum, Integer pageSize, String year){
        PageHelper.startPage(pageNum,pageSize);
        Page<SysArticle> articles = sysArticleMapper.findArticlesByAge(year);
        return articles;
    }

    /**
     * 获取所有年份
     * @return
     */
    public List<String> findAllAges(){
        List<String> ages= sysArticleMapper.selectAllAges();
        return ages;
    }

    /**
     * 根据ID获得文章内容
     * @param id
     * @return
     */
    public SysArticle findById(Integer id){
        return sysArticleMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存文章
     * @param sysArticle
     */
    public void saveArticle(SysArticle sysArticle){
        sysArticleMapper.insert(sysArticle);
    }

    /**
     * 修改风采
     * @param sysArticle
     */
    public void uploadArticleById(SysArticle sysArticle){
        sysArticleMapper.updateByPrimaryKey(sysArticle);
    }
}
