package cn.hctech2006.hcnet.service;

import cn.hctech2006.hcnet.bean.SysTeam;
import cn.hctech2006.hcnet.mapper.SysTeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private SysTeamMapper sysTeamMapper;
    public List<SysTeam> findAllTeam(){
        return sysTeamMapper.selectAll();
    }

    public SysTeam findTeamById(Integer id){
        SysTeam sysTeam = sysTeamMapper.selectByPrimaryKey(id);
        return sysTeam;
    }

    public int saveTeam(SysTeam sysTeam){
        int result = sysTeamMapper.insert(sysTeam);
        return result;
    }

    public int updateTeam(SysTeam sysTeam){
        int result = sysTeamMapper.updateByPrimaryKey(sysTeam);
        return result;
    }
}
