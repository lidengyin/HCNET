package cn.hctech2006.hcnet.bean;

import java.io.Serializable;
import java.util.List;

public class SysPrivilege implements Serializable {
    private Long id;

    private String privilegeName;

    private String privilegeUrl;

    private Long privilegeParentid;

    private List<SysPrivilege> privilegeList;

    private List<SysRole> roleList;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeUrl() {
        return privilegeUrl;
    }

    public void setPrivilegeUrl(String privilegeUrl) {
        this.privilegeUrl = privilegeUrl;
    }

    public Long getPrivilegeParentid() {
        return privilegeParentid;
    }

    public void setPrivilegeParentid(Long privilegeParentid) {
        this.privilegeParentid = privilegeParentid;
    }

    public List<SysPrivilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<SysPrivilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}