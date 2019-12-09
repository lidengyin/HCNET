package cn.hctech2006.hcnet.bean;

import java.io.Serializable;

public class SysInsitu implements Serializable {
    private Integer id;

    private String insituContent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInsituContent() {
        return insituContent;
    }

    public void setInsituContent(String insituContent) {
        this.insituContent = insituContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", insituContent=").append(insituContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}