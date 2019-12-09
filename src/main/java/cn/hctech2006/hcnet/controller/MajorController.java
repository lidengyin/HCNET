package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysMajor;
import cn.hctech2006.hcnet.service.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "专业信息接口")
@RestController
@RequestMapping("/major")
public class MajorController {
    @Autowired
    private MajorService majorService;
    @ApiOperation(value = "上传专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "major",value = "专业名",required = true)
    })
    @PostMapping("/upload")
    public RespBean uploadMajor(SysMajor sysMajor){
        if(sysMajor.getMajor() == null){
            return RespBean.error("参数不全");
        }
        try{
            majorService.saveMajor(sysMajor);
            return RespBean.ok("专业信息上传成功",sysMajor);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("专业信息上传失败");
        }
    }
    @ApiOperation(value = "修改专业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "修改专业信息的ID",required = true),
            @ApiImplicitParam(type = "query",name = "major",value = "专业名",required = true)
    })
    @PostMapping("/update")
    public RespBean updateMajor(SysMajor sysMajor){
        if(sysMajor.getMajor() == null || sysMajor.getId() == null){
            return RespBean.error("参数不全");
        }
        try{
            majorService.updateMajor(sysMajor);
            return RespBean.ok("专业修改成功",sysMajor);

        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("专业修改失败");
        }
    }
    @ApiOperation(value = "根据ID查找专业")
    @ApiImplicitParam(type = "query",name = "id",value = "专业ID",required = true)
    @PostMapping("/find")
    public RespBean findMajorById(Integer id){
        if(id == null){
            return RespBean.error("缺少参数");
        }
        try{
            SysMajor sysMajor = majorService.findMajorById(id);
            return RespBean.ok("查找专业成功",sysMajor);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("查找参数失败");
        }
    }
}
