package cn.hctech2006.hcnet.bean;

import java.io.Serializable;
import java.util.Date;

public class SysMem implements Serializable {
    private Integer id;

    private String memName;

    private String memHead;

    private Date memBegin;

    private Date memEnd;

    private String memDetail;

    private String memIsenable;

    private String memGroup;

    private String memDirect;

    private String memMajor;

    private String memGroupZh;
    private String memDirectZh;

    private String memMajorZh;
    private String memBeginZh;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemHead() {
        return memHead;
    }

    public void setMemHead(String memHead) {
        this.memHead = memHead;
    }

    public Date getMemBegin() {
        return memBegin;
    }

    public void setMemBegin(Date memBegin) {
        this.memBegin = memBegin;
    }

    public Date getMemEnd() {
        return memEnd;
    }

    public void setMemEnd(Date memEnd) {
        this.memEnd = memEnd;
    }

    public String getMemDetail() {
        return memDetail;
    }

    public void setMemDetail(String memDetail) {
        this.memDetail = memDetail;
    }

    public String getMemIsenable() {
        return memIsenable;
    }

    public void setMemIsenable(String memIsenable) {
        this.memIsenable = memIsenable;
    }

    public String getMemGroup() {
        return memGroup;
    }

    public void setMemGroup(String memGroup) {
        this.memGroup = memGroup;
    }

    public String getMemDirect() {
        return memDirect;
    }

    public void setMemDirect(String memDirect) {
        this.memDirect = memDirect;
    }

    public String getMemMajor() {
        return memMajor;
    }

    public void setMemMajor(String memMajor) {
        this.memMajor = memMajor;
    }

    public String getMemGroupZh() {
        return memGroupZh;
    }

    public void setMemGroupZh(String memGroupZh) {
        this.memGroupZh = memGroupZh;
    }

    public String getMemDirectZh() {
        return memDirectZh;
    }

    public void setMemDirectZh(String memDirectZh) {
        this.memDirectZh = memDirectZh;
    }

    public String getMemMajorZh() {
        return memMajorZh;
    }

    public void setMemMajorZh(String memMajorZh) {
        this.memMajorZh = memMajorZh;
    }

    public String getMemBeginZh() {
        return memBeginZh;
    }

    public void setMemBeginZh(String memBeginZh) {
        this.memBeginZh = memBeginZh;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memName=").append(memName);
        sb.append(", memHead=").append(memHead);
        sb.append(", memBegin=").append(memBegin);
        sb.append(", memEnd=").append(memEnd);
        sb.append(", memDetail=").append(memDetail);
        sb.append(", memIsenable=").append(memIsenable);
        sb.append(", memGroup=").append(memGroup);
        sb.append(", memDirect=").append(memDirect);
        sb.append(", memMajor=").append(memMajor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}