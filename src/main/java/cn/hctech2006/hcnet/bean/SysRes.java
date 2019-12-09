package cn.hctech2006.hcnet.bean;

import java.io.Serializable;

public class SysRes implements Serializable {
    private Integer id;

    private String resName;

    private String resImg;

    private String resDetail;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResImg() {
        return resImg;
    }

    public void setResImg(String resImg) {
        this.resImg = resImg;
    }

    public String getResDetail() {
        return resDetail;
    }

    public void setResDetail(String resDetail) {
        this.resDetail = resDetail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resName=").append(resName);
        sb.append(", resImg=").append(resImg);
        sb.append(", resDetail=").append(resDetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}