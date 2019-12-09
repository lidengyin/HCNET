package cn.hctech2006.hcnet.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;


public class SysAward implements Serializable {
    private Integer id;

    private String awardName;

    private String awardDetail;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date awardDate;

    private String awardImg;

    private String awardGroup;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardDetail() {
        return awardDetail;
    }

    public void setAwardDetail(String awardDetail) {
        this.awardDetail = awardDetail;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public String getAwardImg() {
        return awardImg;
    }

    public void setAwardImg(String awardImg) {
        this.awardImg = awardImg;
    }

    public String getAwardGroup() {
        return awardGroup;
    }

    public void setAwardGroup(String awardGroup) {
        this.awardGroup = awardGroup;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", awardName=").append(awardName);
        sb.append(", awardDetail=").append(awardDetail);
        sb.append(", awardDate=").append(awardDate);
        sb.append(", awardImg=").append(awardImg);
        sb.append(", awardGroup=").append(awardGroup);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}