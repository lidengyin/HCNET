package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.PageResult;
import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysAward;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.service.AwardService;
import cn.hctech2006.hcnet.service.ImgService;
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

@Api(tags = "获奖查询接口")
@RestController
@RequestMapping("/award")
public class AwardController {
    @Autowired
    private AwardService awardService;
    @Autowired
    private ImgService imgService;
    @ApiOperation(value = "根据组别分页获取获奖情况")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum",value = "分页页码",required = true),
            @ApiImplicitParam(type = "query", name = "pageSize",value = "分页行数",required = true),
            @ApiImplicitParam(type = "query", name = "group",value = "查询组别",required = true)
    })
    @PostMapping("/group")
    public RespBean findAwardByGroup(Integer pageNum, Integer pageSize, String group){
        if(pageNum == null || pageSize == null || group == null || pageNum <= 0 || pageSize <= 0){
            return RespBean.error("参数有误");
        }
       try{
           Page<SysAward> sysAwards = awardService.findAwardByGroup(pageNum,pageSize,group);
           List<SysAward> awards = sysAwards.getResult();
           PageResult pageResult = new PageResult();
           pageResult.setPageNum(pageNum);
           pageResult.setPageSize(pageSize);
           pageResult.setContent(awards);
           pageResult.setTotalSize(sysAwards.getTotal());
           pageResult.setTotalPages(sysAwards.getPages());
           return RespBean.ok("成功分组分页获取获奖情况",pageResult);
       }catch (Exception e){
           e.printStackTrace();
           return RespBean.error("获取获奖情况失败");
       }
    }
    @ApiOperation(value = "获奖情况上传",notes = "获奖情况上传")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "awardName",value = "获奖名",required = true),
            @ApiImplicitParam(type = "query", name = "awardDate",value = "获奖日期",required = true),
            @ApiImplicitParam(type = "query",name = "awardDetail",value = "获奖细节",required = true),
            @ApiImplicitParam(type = "query",name = "awardGroup",value = "获奖组别,枚举类型",required = true),
            //@ApiImplicitParam(type = "query",name = "file",value = "获奖图片",required = true)
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadAward(SysAward sysAward, @ApiParam(value = "获奖照片",required = true) MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return RespBean.error("图片资源为空,请上传图片");
        }
        if(sysAward.getAwardDate() == null || sysAward.getAwardDetail() == null || sysAward.getAwardName() == null|| sysAward.getAwardGroup() == null){
            return RespBean.error("参数不完整,请重新填写");
        }
        try{
            sysAward.setAwardImg(findImgUrl(file,request));
            awardService.saveAward(sysAward);
            return RespBean.ok("获奖情况上传成功",sysAward);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获奖情况上传失败");
        }
    }
    @ApiOperation(value = "获奖情况更新",notes = "获奖情况更新")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "获奖序号",required = true),
            @ApiImplicitParam(type = "query",name = "awardName",value = "获奖名"),
            @ApiImplicitParam(type = "query", name = "awardDate",value = "获奖日期"),
            @ApiImplicitParam(type = "query",name = "awardDetail",value = "获奖细节"),
            @ApiImplicitParam(type = "query",name = "awardGroup",value = "获奖组别,枚举类型"),
            //@ApiImplicitParam(type = "query",name = "file",value = "获奖图片")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/data-form")
    public RespBean updateAward(SysAward newAward, @ApiParam(value = "获奖图片") MultipartFile file, HttpServletRequest request)throws Exception{
        if(newAward.getId() == null){
            return RespBean.error("请填写序号!!");
        }
        try{
        //获取修改以前获奖记录
            SysAward sysAward = awardService.findAwardById(newAward.getId());
            if(file.isEmpty()){
                newAward.setAwardImg(sysAward.getAwardImg());
            }else{
                newAward.setAwardImg(findImgUrl(file,request));
            }
            if(newAward.getAwardGroup() == null){
                newAward.setAwardGroup(sysAward.getAwardGroup());
            }
            if(newAward.getAwardDate() == null){
                newAward.setAwardDate(sysAward.getAwardDate());
            }
            if(newAward.getAwardDetail() == null){
                newAward.setAwardDetail(sysAward.getAwardDetail());
            }
            if(newAward.getAwardName() == null){
                newAward.setAwardName(sysAward.getAwardName());
            }
            awardService.updateAward(newAward);
            return RespBean.ok("获奖情况求改成功",newAward);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获奖情况修改失败");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    /**
     * 工具类:风采图片保存
     * @param multipartFile
     * @param request
     * @return
     */
    public String findImgUrl(MultipartFile multipartFile, HttpServletRequest request){
        //文件
        SysImg sysImg = new SysImg();

        //图片绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/uploadImg/");

        //System.out.println("realPath"+realPath);
        //格式化时间,以时间分类
        String format = sdf.format(new Date());
        //获得文件所在目录
        File folder = new File(realPath + format);
        //添加目录
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        System.out.println();
        //初始图片名
        String oldName = multipartFile.getOriginalFilename();
        //新的图片名
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{
            //转移加载文件
            multipartFile.transferTo(new File(folder, newName));
            //图片相对路jing
            String imgRelatePath = request.getScheme()+"://"+request.getServerName() +":"+request.getServerPort()+"/upload/uploadImg/"+format+newName;
            //设置文件名
            //System.out.println();
            sysImg.setImgName(oldName);
            //设置文件URL
            sysImg.setImgUrl(imgRelatePath);
            //设置文件上传时间
            System.out.println();
            sysImg.setImgCt(new Date());
            imgService.saveImg(sysImg);
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
