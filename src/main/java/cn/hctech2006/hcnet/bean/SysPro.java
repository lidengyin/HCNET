package cn.hctech2006.hcnet.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;


public class SysPro implements Serializable {
    private Integer id;

    private String proName;

    private String proImg;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date proDate;

    private String proGroup;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date proEnddate;

    private String proDetail;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProImg() {
        return proImg;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public String getProGroup() {
        return proGroup;
    }

    public void setProGroup(String proGroup) {
        this.proGroup = proGroup;
    }

    public Date getProEnddate() {
        return proEnddate;
    }

    public void setProEnddate(Date proEnddate) {
        this.proEnddate = proEnddate;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", proName=").append(proName);
        sb.append(", proImg=").append(proImg);
        sb.append(", proDate=").append(proDate);
        sb.append(", proGroup=").append(proGroup);
        sb.append(", proEnddate=").append(proEnddate);
        sb.append(", proDetail=").append(proDetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}