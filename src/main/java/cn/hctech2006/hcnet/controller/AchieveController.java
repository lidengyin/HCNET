package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysAchieve;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.service.AchieveService;
import cn.hctech2006.hcnet.service.ImgService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Api(tags = "团队成果接口")
@RestController
@RequestMapping("/achieve")
public class AchieveController {
    @Autowired
    private AchieveService achieveService;
    @Autowired
    private ImgService imgService;
    /**
     * 获取全部团队成果
     * @return
     */
    @ApiOperation(value="获取全部团队成果",notes = "获取团队所有成果")
    @PostMapping("/all")
    public RespBean findAllAchieve(){
        List<SysAchieve> achieves = achieveService.findAllAchieve();
        return RespBean.ok("获取所有团队成果",achieves);
    }

    /**
     * 根据ID获取团队成果
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获取团队成果",notes = "根据ID获取团队成果")
    @ApiImplicitParam(type = "query",name = "id",value = "ID",required = true)
    @PostMapping("/one")
    public RespBean findAchieveById(Integer id){
        if(id == null){
            return RespBean.error("成果序号为空,请重新填写");
        }
        try{
            SysAchieve sysAchieve = achieveService.findAchieveById(id);
            if(sysAchieve != null){
                return RespBean.ok("成果获取一个成果", sysAchieve);
            }else{
                return RespBean.error("获取成果失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            return RespBean.error("获取成果失败");
        }
    }
    /**
     * 团队成果上传
     * @param file
     * @param sysAchieve
     * @param request
     * @return
     */
    @ApiOperation(value = "团队成果上传",notes = "团队成果上传")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "achieveName",value = "成果名", required = true),
            @ApiImplicitParam(type = "query",name = "achieveDetail",value = "成果内容", required = true),
            //@ApiImplicitParam(type = "query",name = "file",value = "成果图片", required = true)
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadAchieve(@ApiParam(value = "团队成果图片",required = true) MultipartFile file, SysAchieve sysAchieve, HttpServletRequest request){
        if(file.isEmpty()){
            RespBean.error("上传图片为空,请重新上传");
        }
        if(sysAchieve.getAchieveName() == null || sysAchieve.getAchieveDetail() == null){
            RespBean.error("成果名或成果细节为空,请重新上传");
        }
        try{
            sysAchieve.setAchieveImg(findImgUrl(file,request));
            achieveService.saveAchieve(sysAchieve);
            return RespBean.ok("上传成果成功",sysAchieve);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("成果上传失败");
        }
    }

    /**
     * 更新团队成果
     * @param file
     * @param newAchieve
     * @param request
     * @return
     */
    @ApiOperation(value = "更新团队成果",notes = "根据序号更新团队成果")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id", value = "成果序号",required = true),
            @ApiImplicitParam(type = "query", name = "achieveName",value = "成果名"),
            @ApiImplicitParam(type = "query",name = "achieveDetail",value = "成果内容"),
            //@ApiImplicitParam(type = "query",name = "file",value = "成果图片")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean updateAchieve(@ApiParam(value = "成果图片")MultipartFile file, SysAchieve newAchieve, HttpServletRequest request) throws NullPointerException,Exception{
        if(newAchieve.getId() == null){
            RespBean.error("序号为空，请填写序号");
        }
        //获取修改以前的成果
        SysAchieve sysAchieve = achieveService.findAchieveById(newAchieve.getId());

        if(newAchieve.getAchieveName() == null){
            newAchieve.setAchieveName(sysAchieve.getAchieveName());
        }
        if(newAchieve.getAchieveDetail() == null){
            newAchieve.setAchieveDetail(sysAchieve.getAchieveDetail());
        }
        if(file == null){
            newAchieve.setAchieveImg(sysAchieve.getAchieveImg());
            try{
                achieveService.saveAchieve(newAchieve);
                return RespBean.ok("成果修修改成功",newAchieve);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("成果修改失败");
            }
        }else{
            try{
                newAchieve.setAchieveImg(findImgUrl(file,request));
                achieveService.updateAchieve(newAchieve);
                return RespBean.ok("成果修改成功",newAchieve);
            }catch (Exception e){
                e.printStackTrace();
                return RespBean.error("成果修改失败");
            }
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
