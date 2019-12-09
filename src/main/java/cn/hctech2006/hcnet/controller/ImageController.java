package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.service.ImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Api(tags = "图片传输接口")
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImgService imgService;
    /**
     * 上传图片
     */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @ApiOperation(value = "上传图片", notes = "上传图片")
    //@ApiImplicitParam(type = "query", name = "uploadFile",value = "图片文件",required = true)
    @PostMapping(value = "/upload",headers="content-type=multipart/form-data")
    public RespBean uploadImg(@ApiParam(value = "上传图片",required = true) MultipartFile uploadFile, HttpServletRequest request){
        //文件
        SysImg sysImg = new SysImg();
        //图片绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/uploadImg/");
        //
        System.out.println("realPath"+realPath);
        //格式化时间,以时间分类
        String format = sdf.format(new Date());
        //获得文件所在目录
        File folder = new File(realPath + format);
        //添加目录
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        //初始图片名
        String oldName = uploadFile.getOriginalFilename();
        //新的图片名
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try{
            //转移加载文件
            uploadFile.transferTo(new File(folder, newName));
            //图片相对路径
            String imgRelatePath = request.getScheme()+"://"+request.getServerName() +":"+request.getServerPort()+"/upload/uploadImg/"+format+newName;
            //设置文件名
            sysImg.setImgName(oldName);
            //设置文件URL
            sysImg.setImgUrl(imgRelatePath);
            //设置文件上传时间
            sysImg.setImgCt(new Date());
            imgService.saveImg(sysImg);
            System.out.println("AbsolutePath"+folder.getAbsolutePath()+"/"+newName);
            System.out.println("Path"+folder.getPath()+"/"+newName);
            System.out.println("filePath"+imgRelatePath);

            return RespBean.ok("图片上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("图片上传失败");
        }

    }

}
