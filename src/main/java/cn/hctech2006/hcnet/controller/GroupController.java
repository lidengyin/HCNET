package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysGroup;
import cn.hctech2006.hcnet.bean.SysImg;
import cn.hctech2006.hcnet.bean.SysMem;
import cn.hctech2006.hcnet.service.GroupService;
import cn.hctech2006.hcnet.service.ImgService;
import cn.hctech2006.hcnet.service.MemService;
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

@Api(tags = "组别查询接口")
@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private MemService memService;
    @ApiOperation(value = "获取所有组别",notes = "获取所有组别")
    @PostMapping("/all")
    public RespBean findAllGroup(){
        try{
            List<SysGroup> groups = groupService.findAllList();
            for(SysGroup sysGroup : groups){

                List<SysMem> sysMemList = memService.findMemByGroup(sysGroup.getId().toString());
                sysGroup.setMemList(sysMemList);
            }
            return RespBean.ok("获取所有组别成功",groups);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取组别失败");
        }
    }
    @ApiOperation(value = "团队组别保存",notes = "团队组别保存,groupIsenabled,1为显示,0为不显示")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "groupName",value = "组别名",required = true),
            @ApiImplicitParam(type = "query",name = "groupIntro",value = "组别介绍",required = true),
            //@ApiImplicitParam(type = "query",name = "file",value = "组别图片",required = true),
            @ApiImplicitParam(type = "query",name = "groupIsenabled",value = "是否显示",required = true)
    })
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public RespBean saveGroup(SysGroup sysGroup, @ApiParam(value = "组别图片",required = true) MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return RespBean.error("团队图片为空，请上传图片");
        }
        if(sysGroup.getGroupIntro() == null || sysGroup.getGroupName() == null || sysGroup.getGroupIsenabled() == null){
            return RespBean.error("组别介绍或组别名或是否显示为空,请填写完整");
        }

        try{
            sysGroup.setGroupHead(findImgUrl(file,request));
            sysGroup.setGroupIsenabled("1");
            groupService.saveGroup(sysGroup);
            return RespBean.ok("组别保存成功",sysGroup);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("组别保存失败");
        }
    }

    /**
     * 团队组别修改
     * @param newGroup
     * @param file
     * @param request
     * @return
     */
    @ApiOperation(value = "团队组别修改",notes = "团队组别修改,groupIsenabled=1为可用,为0是不可用")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "组别序号",required = true),
            @ApiImplicitParam(type = "query",name = "groupName",value = "组别名"),
            @ApiImplicitParam(type = "query",name = "groupIntro",value = "组别介绍"),
            //@ApiImplicitParam(type = "query",name = "file",value = "组别图片"),
            @ApiImplicitParam(type = "query",name = "groupIsenabled",value = "组别是否可用")
    })
    @PostMapping(value = "/update",headers = "content-type=multipart/form-data")
    public RespBean updateGroup(SysGroup newGroup, @ApiParam(value = "组别图片") MultipartFile file, HttpServletRequest request)throws Exception{
        if(newGroup.getId() == null){
            return RespBean.error("组别序号为空，请填写序号");
        }
        SysGroup sysGroup;
        try{
            sysGroup = groupService.findGroupById(newGroup.getId());

        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("无效的序号");
        }
        try{
            newGroup.setGroupIsenabled(sysGroup.getGroupIsenabled());
            if(file.isEmpty()){
                newGroup.setGroupHead(sysGroup.getGroupHead());
            }else{
                newGroup.setGroupHead(findImgUrl(file,request));
            }
            if(newGroup.getGroupName() == null){
                newGroup.setGroupName(sysGroup.getGroupName());
            }
            if(newGroup.getGroupIntro() == null){
                newGroup.setGroupIntro(sysGroup.getGroupIntro());
            }
            if(newGroup.getGroupIsenabled() == null){
                newGroup.setGroupIsenabled(sysGroup.getGroupIsenabled());
            }
            groupService.updateGroup(newGroup);
            return RespBean.ok("修改组别成功",newGroup);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("修改组别失败");
        }

    }

    /**
     * 修改组别状态
     * @param sysGroup
     * @return
     */
    @ApiOperation(value = "修改组别状态",notes = "groupIsenabled=1为显示,为0是不显示")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",value = "要关闭的组别ID",required = true),
            @ApiImplicitParam(type = "query",name = "groupIsenabled",value = "是否显示组别",required = true)
    })
    @PostMapping("/update/enable")
    public RespBean CloseGroupByIsEnabled(SysGroup sysGroup){
        if(sysGroup.getId() == null || sysGroup.getGroupIsenabled() == null){
            return RespBean.error("缺少参数,请填写完整");

        }
        try{

            groupService.updateIsEnabled(sysGroup);
            if(sysGroup.getGroupIsenabled() == "0" || sysGroup.getGroupIsenabled().equals("0")){
                System.out.println("不可用,清空组下成员:"+sysGroup.getId().toString());
                List<SysMem> sysMems = memService.findMemByGroup(sysGroup.getId()+"");
                for(SysMem sysMem : sysMems){
                    System.out.println(sysMem.getMemName());
                    sysMem.setMemGroup("0");

                    memService.updateMem(sysMem);
                }
            }
            return RespBean.ok("组别显示状态修改成功",sysGroup);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("组别显示状态修改失败");

        }

    }

    /**
     * 获取所有不可用状态组别
     * @return
     */
    @ApiOperation(value = "获取所有不可用组别状态",notes = "获取所有不可用组别状态,无参数")
    @PostMapping("/find/unable")
    public RespBean findAllUnabledGroup(){
        try{
            List<SysGroup> sysGroups =  groupService.findAllunabledGroup();
            return RespBean.ok("是否可用修改成功",sysGroups);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("是否可用修改失败");
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
