package cn.hctech2006.hcnet.controller;
import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysDirect;
import cn.hctech2006.hcnet.service.DirectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Api(tags = "方向信息接口")
@RestController
@RequestMapping("/direct")
public class DirectController {
    @Autowired
    private DirectService directService;
    @ApiOperation(value = "根据序号获取方向信息",notes = "根据序号获取方向信息")
    @ApiImplicitParam(type = "query",name = "id",value = "方向序号")
    @PostMapping("/one")
    public RespBean findDirectById(Integer id){
        if(id == null){
            return RespBean.error("序号为空，请重新填写");
        }
        try{
            SysDirect sysDirect = directService.findDirectById(id);
            return RespBean.ok("成功获取方向信息",sysDirect);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取方向信息失败");
        }
    }
    @ApiOperation(value = "获取全部方向",notes = "获取全部方向")
    @PostMapping("/all")
    public RespBean findAllDirect(){
        try{
            List<SysDirect> sysDirects = directService.findAllDirect();
            return RespBean.ok("获取全部方向",sysDirects);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取全部方向失败");
        }
    }
    @ApiOperation(value = "分组获取方向",notes = "分组获取方向,输入数字")
    @ApiImplicitParam(type = "query",name = "group",value = "组别名成",required = true)
    @PostMapping("/group/one")
    public RespBean findDirectByGroup(String group){
        try{
            List<SysDirect> directs = directService.findAllDirectByGroup(group);
            return RespBean.ok("分组获取方向成功",directs);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("分组获取方向失败");
        }
    }
    @ApiOperation(value = "方向可用性",notes = "方向可用性")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "方向ID",required = true),
            @ApiImplicitParam(type = "query",name = "directEnable",value = "方向可用性",required = true)
    })
    @PostMapping("/enable")
    public RespBean updateDirectEnable(SysDirect sysDirect){
        if(sysDirect.getId() == null || sysDirect.getDirectEnable() == null){
            return RespBean.error("参数不全,请补全参数");
        }
        try{
            directService.updateDirectEnable(sysDirect);
            return RespBean.ok("成功改变可用性",sysDirect);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("改变可用性失败");
        }
    }
    @ApiOperation(value = "方向改变分组",notes = "方向改变分组")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "方向ID",required = true),
            @ApiImplicitParam(type = "query",name = "directgroup",value = "方向分组",required = true)
    })
    @PostMapping("/group/update")
    public RespBean updateDirectGroup(SysDirect sysDirect){
        if(sysDirect.getDirectGroup() == null || sysDirect.getId() == null){
            return RespBean.error("参数不全,请补全参数");
        }
        try{
            directService.updateDirectGroup(sysDirect);
            return RespBean.ok("成功改变方向分组",sysDirect);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("改变方向分组失败");
        }
    }
    @ApiOperation(value = "上传方向",notes = "上传方向")
    @ApiImplicitParams({

            @ApiImplicitParam(type = "query",name = "directName",value = "方向名称",required = true),
            @ApiImplicitParam(type = "query",name = "directGroup",value = "方向组别",required = true)
    })
    @PostMapping("/upload")
    public RespBean uploadDirect(SysDirect sysDirect){
        if( sysDirect.getDirectGroup() == null || sysDirect.getDirectName() == null){
            return RespBean.error("参数不全,请填写完整");
        }
        try{
            sysDirect.setDirectEnable("1");
            directService.saveDirect(sysDirect);
            return RespBean.ok("方向上传成功",sysDirect);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("方向上传失败");
        }
    }


}
