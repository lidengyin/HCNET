package cn.hctech2006.hcnet.bean;

import java.io.Serializable;

public class SysDirect implements Serializable {
    private Integer  id;

    private String directName;

    private String directGroup;

    private String directEnable;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirectName() {
        return directName;
    }

    public void setDirectName(String directName) {
        this.directName = directName;
    }

    public String getDirectGroup() {
        return directGroup;
    }

    public void setDirectGroup(String directGroup) {
        this.directGroup = directGroup;
    }

    public String getDirectEnable() {
        return directEnable;
    }

    public void setDirectEnable(String directEnable) {
        this.directEnable = directEnable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", directName=").append(directName);
        sb.append(", directGroup=").append(directGroup);
        sb.append(", directEnable=").append(directEnable);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}