package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.bean.SysRes;
import cn.hctech2006.hcnet.service.ImgService;
import cn.hctech2006.hcnet.service.ResService;
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

@Api(tags = "团队资源接口")
@RestController
@RequestMapping("/res")
public class ResController {
    @Autowired
    private ResService resService;
    @Autowired
    private ImgService imgService;

    /**
     * 获取全部资源
     * @return
     */
    @ApiOperation(value = "获取全部资源",notes = "获取全部资源")
    @PostMapping("/all")
    public List<SysRes> getAllRes(){
        return resService.findAllRes();
    }

    /**
     * 团队资源上传
     * @param sysRes
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "团队资源上传")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "resName",value = "资源名",required = true),
            @ApiImplicitParam(type = "query",name = "resDetail",value = "资源细节",required = true),
           //@ApiImplicitParam(type = "query",name = "file",value = "资源图片",required = true)
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadRes(SysRes sysRes, @ApiParam(value = "资源图片",required = true) MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return RespBean.error("图片资源为空,请添加图片资源!");
        }
        if(sysRes.getResName() == null || sysRes.getResDetail() == null){
            return RespBean.error("资源名或资源简介为空,请填写完整!");
        }
        try{
            sysRes.setResImg(findImgUrl(file,request));
            resService.saveRes(sysRes);
            return RespBean.ok("上传团队资源成功",sysRes);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("团队资源上传失败");
        }

    }

    /**
     * 团队资源更新
     * @param newRes
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "团队资源更新")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "团队资源序号",required = true),
            @ApiImplicitParam(type = "query",name = "resName",value = "团队资源名"),
            @ApiImplicitParam(type = "query",name = "resDetail",value = "团队资源细节"),
            //@ApiImplicitParam(type = "query",name = "file",value = "团队资源图片")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean updateRes(SysRes newRes, @ApiParam(value = "资源图片") MultipartFile file, HttpServletRequest request)throws Exception{
        if(newRes.getId() == null){
            return RespBean.error("序号为空，请填写完整序号");
        }
        try{
           SysRes sysRes = resService.findResById(newRes.getId());
           if(file == null){
               newRes.setResImg(sysRes.getResImg());
           }
           if(newRes.getResDetail() == null){
               newRes.setResDetail(sysRes.getResDetail());
           }
           if(newRes.getResName() == null){
               newRes.setResName(sysRes.getResName());
           }
           resService.updateRes(newRes);
           return RespBean.ok("团队资源更新成功",newRes);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("团队资源更新失败");
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
        System.out.println();
        String format = sdf.format(new Date());
        //获得文件所在目录
        File folder = new File(realPath + format);
        //添加目录
        System.out.println();
        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        //初始图片名

        String oldName = multipartFile.getOriginalFilename();
        //新的图片名
        System.out.println();
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{

            //转移加载文件
            multipartFile.transferTo(new File(folder, newName));
            //图片相对路jing
            System.out.println();
            String imgRelatePath = request.getScheme()+"://"+request.getServerName() +":"+request.getServerPort()+"/upload/uploadImg/"+format+newName;
            //设置文件名
            sysImg.setImgName(oldName);
            //设置文件URL
            sysImg.setImgUrl(imgRelatePath);
            //设置文件上传时间
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
