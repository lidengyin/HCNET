package cn.hctech2006.hcnet.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysVideo implements Serializable {
    private Integer id;

    private String videoImgUrl;

    private String videoName;

    private String videoUrl;

    private String videoDetail;

    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String delIt;

    private String year;

    private String videoGroup;

    private String videoGroupZh;

    public String videoIntro;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }
    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(String videoDetail) {
        this.videoDetail = videoDetail;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelIt() {
        return delIt;
    }

    public void setDelIt(String delIt) {
        this.delIt = delIt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVideoGroup() {
        return videoGroup;
    }

    public void setVideoGroup(String videoGroup) {
        this.videoGroup = videoGroup;
    }

    public String getVideoGroupZh() {
        return videoGroupZh;
    }

    public void setVideoGroupZh(String videoGroupZh) {
        this.videoGroupZh = videoGroupZh;
    }

    public String getVideoIntro() {
        return videoIntro;
    }

    public void setVideoIntro(String videoIntro) {
        this.videoIntro = videoIntro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoImgUrl=").append(videoImgUrl);
        sb.append(", videoName=").append(videoName);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", videoDetail=").append(videoDetail);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delIt=").append(delIt);
        sb.append(", year=").append(year);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}