package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysInsitu;
import cn.hctech2006.hcnet.service.InsituService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "团队制度接口")
@RestController
@RequestMapping("/insitu")
public class InsituController {
    @Autowired
    private InsituService insituService;

    /**
     * 获取所有制度
     * @return
     */
    @ApiOperation(value = "获取团队全部制度",notes = "获取团队所有制度")
    @PostMapping("/all")
    public RespBean findAllInsitu(){
        try{
            List<SysInsitu> insitus =  insituService.findAllInsitu();
            return RespBean.ok("成功获取所有制度",insitus);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("获取制度失败");
        }
    }

    /**
     * 团队制度上传
     * @param sysInsitu
     * @return
     */
    @ApiOperation(value = "团队制度上传",notes = "团队制度上传")
    @ApiImplicitParam(type = "query", name = "insituContent",value = "制度内容",required = true)
    @PostMapping("/upload")
    public RespBean uploadInsitu(SysInsitu sysInsitu){
        if(sysInsitu.getInsituContent() == null || sysInsitu.getInsituContent().equals("")){
            return RespBean.error("团队制度内容为空，请重新填写");
        }
        try{
            insituService.saveInsitu(sysInsitu);
            return RespBean.ok("制度上传成功",sysInsitu);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("制度上传失败");
        }
    }

    /**
     * 团队制度更新
     * @param newInsitu
     * @return
     */
    @ApiOperation(value = "团队制度更新",notes = "团队制度更新")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",value = "制度序号",required = true),
            @ApiImplicitParam(type = "query", name = "insituContent",value = "制度内容")
    })
    @PostMapping("/update")
    public RespBean updateInsitu(SysInsitu newInsitu)throws Exception{
        if(newInsitu.getId() == null){
            return RespBean.error("序号为空，请填写完整");
        }
        //获取修改前制度
        SysInsitu sysInsitu = insituService.findInsituById(newInsitu.getId());
        if(sysInsitu == null){
            return RespBean.error("序号不存在，请输入正确序号");
        }
        if(newInsitu.getInsituContent() == null){
            newInsitu.setInsituContent(sysInsitu.getInsituContent());
        }
        try{
            insituService.updateInsitu(newInsitu);
            return RespBean.ok("团队制度蟹盖成功",newInsitu);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("团队制度修改失败");
        }
    }

}
