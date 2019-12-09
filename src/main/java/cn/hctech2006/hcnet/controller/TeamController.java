package cn.hctech2006.hcnet.controller;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysTeam;
import cn.hctech2006.hcnet.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "团队信息接口")
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * 获取所有团队信息
     * @return
     */
    @ApiOperation(value = "获取所有团队信息",notes = "获取所有团队信息")
    @PostMapping("/all")
    public List<SysTeam> findAllTeam(){
        return teamService.findAllTeam();
    }

    /**
     * 团队信息上传
     * @param sysTeam
     * @return
     */
    @ApiOperation(value = "上传团队信息",notes = "上传团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "teamName",value = "团队名",required = true),
            @ApiImplicitParam(type = "query",name = "teamDetail",value = "团队简介",required = true)
    })

    @PostMapping("/upload")
    public RespBean uploadTeam(SysTeam sysTeam){
        if(sysTeam.getTeamName() == null || sysTeam.getTeamDetail() == null){
            return RespBean.error("团队信息名或团队信息内容为空，请填写完整");
        }
        try{
            teamService.saveTeam(sysTeam);
            return RespBean.ok("团队信息上传成功",sysTeam);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("团队信息上传失败");
        }
    }

    /**
     * 团队信息更新
     * @param newTeam
     * @return
     */
    @ApiOperation(value = "更新团队信息",notes = "更新团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query",name = "id",value = "团队序号",required = true),
            @ApiImplicitParam(type = "query",name = "teamName",value = "团队名"),
            @ApiImplicitParam(type = "query",name = "teamDetail",value = "团队简介")
    })
    @PostMapping("/update")
    public RespBean updateTeam(SysTeam newTeam)throws Exception{
        if(newTeam.getId() == null){
            return RespBean.error("序号为空，请重新填写");
        }
        try{
            //获取修改前团队信息
            SysTeam sysTeam = teamService.findTeamById(newTeam.getId());
            if(newTeam.getTeamDetail() == null){
                newTeam.setTeamDetail(sysTeam.getTeamDetail());
            }
            if(newTeam.getTeamName() == null){
                newTeam.setTeamName(sysTeam.getTeamName());
            }
            teamService.updateTeam(newTeam);
            return RespBean.ok("团队信息修改成功",newTeam);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("团队信息修改失败");
        }
    }
}
