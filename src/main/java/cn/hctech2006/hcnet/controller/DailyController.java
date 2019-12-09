package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.PageResult;
import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysDaily;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.service.DailyService;
import cn.hctech2006.hcnet.service.ImgService;
import cn.hctech2006.hcnet.util.HtmlSpirit;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = "日常传输接口")
@RestController
@RequestMapping("/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;
    @Autowired
    private ImgService imgService;
    /**
     * 分页获取所有日常活动
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value ="分页获取所有日常活动",notes = "分页获取所有日常接口")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum",value = "分页页码",required = true),
            @ApiImplicitParam(type = "query",name = "pageSize",value = "分页行数",required = true)
    })
    @PostMapping("/pages")
    public RespBean findAllDailyByPage(Integer pageNum, Integer pageSize){
        if(pageNum <=0 || pageSize <=0){
            RespBean.error("分页参数错误");
        }
        Page<SysDaily> dailyPage = dailyService.findAllDaily(pageNum,pageSize);
        List<SysDaily> dailyList = dailyPage.getResult();
        for(SysDaily sysDaily: dailyPage){
            String dailyIntro = HtmlSpirit.delHTMLTag(sysDaily.getDailyContent());
            sysDaily.setDailyIntro((dailyIntro.length() < 100)? dailyIntro:dailyIntro.substring(0,100));
        }
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPages(dailyPage.getPages());
        pageResult.setTotalSize(dailyPage.getTotal());
        pageResult.setContent(dailyList);
        return RespBean.ok("成功分页获取所有日常",pageResult);
    }
    /**
     * 根据ID获得日常信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获得日常信息",notes = "根据ID获得日常信息")
    @ApiImplicitParam(type = "query", name = "id",value = "日常序号",required = true)
    @PostMapping("/page")
    public RespBean findDailyById(Integer id){
        try{
            SysDaily sysDaily = dailyService.findDailyById(id);
            sysDaily.setDailyReadtime(sysDaily.getDailyReadtime()+1);
            return RespBean.ok("成功获取日常详细信息",sysDaily);

        }catch(Exception e){
            e.printStackTrace();
            return RespBean.error("获取日常信息失败");
        }
    }

    /**
     * 上传日常
     * @param sysDaily
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "上传日常活动",notes = "上传日常活动")
    @ApiImplicitParams({
        @ApiImplicitParam(type = "query", name = "dailyName",value = "日常名",required = true),
        @ApiImplicitParam(type = "query", name = "dailyContent",value = "日常内容",required = true),
        //@ApiImplicitParam(type = "query", name = "file",value = "日常图片",required = true)
})
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean SaveDaily(SysDaily sysDaily, @ApiParam(value = "日常图片",required = true) MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return RespBean.error("图片资源为空!请上传正确的资源");
        }
        if(sysDaily.getDailyName() == null || sysDaily.getDailyContent() == null){
            return RespBean.error("活动名或活动内容为空,请填写完整");
        }
        try{
            sysDaily.setDailyCt(new Date());
            sysDaily.setDailyReadtime(0);
            sysDaily.setDailyImage(findImgUrl(file,request));
            dailyService.saveDaily(sysDaily);
            return RespBean.ok("日常保存成功",sysDaily);

        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("日常保存失败");
        }
    }
    @ApiOperation(value = "日常修改",notes = "修改日常活动")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",value = "日常序号",required = true),
            @ApiImplicitParam(type = "query", name = "dailyName",value = "日常名"),
            @ApiImplicitParam(type = "query", name = "dailyContent",value = "日常内容"),
            //@ApiImplicitParam(type = "query", name = "file",value = "日常图片")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean uplateDaily(SysDaily newDaily, @ApiParam(value = "日常图片") MultipartFile file, HttpServletRequest request)throws Exception{
        if(newDaily.getId() == null){
            return RespBean.error("序号为空,请填写要修改的序号");
        }
        //获得修改之前的日常活动信息
        SysDaily sysDaily = dailyService.findDailyById(newDaily.getId());
        newDaily.setDailyCt(sysDaily.getDailyCt());
        newDaily.setDailyUt(new Date());
        if(newDaily.getDailyContent() == null){
            newDaily.setDailyContent(sysDaily.getDailyContent());
        }
        if(newDaily.getDailyName() == null){
            newDaily.setDailyName(sysDaily.getDailyName());
        }
        if(file.isEmpty()){
            newDaily.setDailyImage(sysDaily.getDailyImage());

            try{
                dailyService.uplateDaily(newDaily);
                return RespBean.ok("日常修改成功",newDaily);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("日常修改失败");
            }
        }else{

            try{
                newDaily.setDailyImage(findImgUrl(file,request));
                dailyService.uplateDaily(newDaily);
                return RespBean.ok("日常修改成功",newDaily);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("日常修改失败");
            }
        }
    }


SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    /**
     * 工具类:日常图片保存
     * @param multipartFile
     * @param request
     * @return
     */

    public String findImgUrl(MultipartFile multipartFile, HttpServletRequest request){
        //文件
        SysImg sysImg = new SysImg();

        //图片绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/uploadImg/");
        System.out.println();
        //System.out.println("realPath"+realPath);
        //格式化时间,以时间分类
        String format = sdf.format(new Date());
        //获得文件所在目录
        File folder = new File(realPath + format);
        //添加目录
        System.out.println();
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        System.out.println();
        //初始图片名
        System.out.println();
        String oldName = multipartFile.getOriginalFilename();
        //新的图片名
        System.out.println();
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{
            System.out.println();
            //转移加载文件
            multipartFile.transferTo(new File(folder, newName));
            //图片相对路jing
            String imgRelatePath = request.getScheme()+"://"+request.getServerName() +":"+request.getServerPort()+"/upload/uploadImg/"+format+newName;
            //设置文件名
            //System.out.println();
            sysImg.setImgName(oldName);
            //设置文件URL
            System.out.println();
            sysImg.setImgUrl(imgRelatePath);
            //设置文件上传时间
            System.out.println();
            sysImg.setImgCt(new Date());
            System.out.println();
            imgService.saveImg(sysImg);
            System.out.println();
            System.out.println();
            System.out.println("AbsolutePath"+folder.getAbsolutePath()+"/"+newName);
            System.out.println("Path"+folder.getPath()+"/"+newName);
            System.out.println("filePath"+imgRelatePath);
            return imgRelatePath;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
