package cn.hctech2006.hcnet.bean;

import java.io.Serializable;

public class SysAchieve implements Serializable {
    private Integer id;

    private String achieveName;

    private String achieveDetail;

    private String achieveImg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAchieveName() {
        return achieveName;
    }

    public void setAchieveName(String achieveName) {
        this.achieveName = achieveName;
    }

    public String getAchieveDetail() {
        return achieveDetail;
    }

    public void setAchieveDetail(String achieveDetail) {
        this.achieveDetail = achieveDetail;
    }

    public String getAchieveImg() {
        return achieveImg;
    }

    public void setAchieveImg(String achieveImg) {
        this.achieveImg = achieveImg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", achieveName=").append(achieveName);
        sb.append(", achieveDetail=").append(achieveDetail);
        sb.append(", achieveImg=").append(achieveImg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}