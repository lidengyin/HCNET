package cn.hctech2006.hcnet.bean;

import java.io.Serializable;
import java.util.List;

public class SysGroup implements Serializable {
    private Integer id;

    private String groupName;

    private String groupIntro;

    private String groupHead;

    private String groupIsenabled;

    private List<SysMem> memList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }

    public String getGroupIsenabled() {
        return groupIsenabled;
    }

    public void setGroupIsenabled(String groupIsenabled) {
        this.groupIsenabled = groupIsenabled;
    }

    public List<SysMem> getMemList() {
        return memList;
    }

    public void setMemList(List<SysMem> memList) {
        this.memList = memList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupName=").append(groupName);
        sb.append(", groupIntro=").append(groupIntro);
        sb.append(", groupHead=").append(groupHead);
        sb.append(", groupIsenabled=").append(groupIsenabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}