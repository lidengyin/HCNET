package cn.hctech2006.hcnet.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysImg implements Serializable {
    private Integer id;

    private String imgName;

    private String imgUrl;

    private String imgCb;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date imgCt;

    private String imgUb;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date imgUt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgCb() {
        return imgCb;
    }

    public void setImgCb(String imgCb) {
        this.imgCb = imgCb;
    }

    public Date getImgCt() {
        return imgCt;
    }

    public void setImgCt(Date imgCt) {
        this.imgCt = imgCt;
    }

    public String getImgUb() {
        return imgUb;
    }

    public void setImgUb(String imgUb) {
        this.imgUb = imgUb;
    }

    public Date getImgUt() {
        return imgUt;
    }

    public void setImgUt(Date imgUt) {
        this.imgUt = imgUt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imgName=").append(imgName);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", imgCb=").append(imgCb);
        sb.append(", imgCt=").append(imgCt);
        sb.append(", imgUb=").append(imgUb);
        sb.append(", imgUt=").append(imgUt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}