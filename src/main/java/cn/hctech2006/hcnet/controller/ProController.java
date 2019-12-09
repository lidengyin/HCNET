package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.bean.SysPro;
import cn.hctech2006.hcnet.service.ImgService;
import cn.hctech2006.hcnet.service.ProService;
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

@Api(tags = "项目信息接口")
@RestController
@RequestMapping("/pro")
public class ProController {
    @Autowired
    private ProService proService;
    @Autowired
    private ImgService imgService;
    @ApiOperation(value = "获取全部项目信息",notes = "获取全部项目信息")
    @PostMapping("/all")
    public RespBean findAllPro(){
        try{
            List<SysPro> pros = proService.findAllPro();
            return RespBean.ok("获取全部项目信息",pros);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取项目信息失败");
        }

    }
    @ApiOperation(value = "分组获取项目",notes = "分组获取项目")
    @ApiImplicitParam(type = "query",name = "group",value = "组别,枚举类型")
    @PostMapping("/group")
    public RespBean findProByGroup(String group){
        if(group == null){
            return RespBean.error("分组序号为空,请重新填写");
        }
        try{
            List<SysPro> pros = proService.findProByGroup(group);
            return RespBean.ok("分组获取全部项目",pros);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("分组获取项目失败");
        }
    }
    @ApiOperation(value = "项目上传",notes = "项目上传")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "proName",value = "项目名", required = true),
            @ApiImplicitParam(type = "query", name = "proDetail",value = "项目细节", required = true),
            @ApiImplicitParam(type = "query",name = "proGroup",value = "项目组别", required = true),
            @ApiImplicitParam(type = "query",name = "proDate",value = "项目日期", required = true),
           // @ApiImplicitParam(type = "query",name = "file",value = "项目图片", required = true)
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean uploadPro(SysPro sysPro, @ApiParam(value = "项目图片",required = true) MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return RespBean.error("文件为空,请重新上传资源");
        }
        if(sysPro.getProDetail() == null || sysPro.getProName() == null|| sysPro.getProDate() == null || sysPro.getProGroup() == null){
            return RespBean.error("项目名或项目内容为空,请填写完整");
        }
        try{
            sysPro.setProImg(findImgUrl(file,request));
            proService.savePro(sysPro);
            return RespBean.ok("项目上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("项目上传失败");
        }

    }
    @ApiOperation(value = "项目修改",notes = "项目修改")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",value = "项目序号", required = true),
            @ApiImplicitParam(type = "query",name = "proName",value = "项目名"),
            @ApiImplicitParam(type = "query", name = "proDetail",value = "项目细节"),
            @ApiImplicitParam(type = "query",name = "proGroup",value = "项目组别"),
            @ApiImplicitParam(type = "query",name = "proDate",value = "项目日期"),
            //@ApiImplicitParam(type = "query",name = "file",value = "项目图片")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean updatePro(SysPro newPro, HttpServletRequest request, @ApiParam(value = "项目图片") MultipartFile file)throws Exception{
        if(newPro.getId() == null){
            return RespBean.error("序号为空，请重新上传");
        }
        try{
            SysPro sysPro = proService.findProByid(newPro.getId());

            if(newPro.getProName() == null){
                newPro.setProName(sysPro.getProName());
            }
            if(newPro.getProDetail() == null){
                newPro.setProDetail(sysPro.getProDetail());
            }
            if(newPro.getProName() == null){
                newPro.setProName(sysPro.getProName());
            }
            if(newPro.getProGroup() == null){
                newPro.setProGroup(sysPro.getProGroup());
            }
            if(newPro.getProDate() == null){
                newPro.setProDate(sysPro.getProDate());
            }
            if(file.isEmpty()){
                newPro.setProImg(sysPro.getProImg());
            }else{
                newPro.setProImg(findImgUrl(file,request));
            }
            proService.updatePro(newPro);
            return RespBean.ok("项目信息修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("项目信息修改失败");
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
