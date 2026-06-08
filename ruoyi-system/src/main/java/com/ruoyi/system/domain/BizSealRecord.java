package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

public class BizSealRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long sealRecordId;
    private Long instanceId;
    private Long approvalRecordId;
    private Long sealId;
    private Long positionId;
    private String sealer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sealTime;

    private String sealName;
    private String sealImage;
    private Double posX;
    private Double posY;
    private Integer sealWidth;
    private Integer sealHeight;

    public Long getSealRecordId() { return sealRecordId; }
    public void setSealRecordId(Long sealRecordId) { this.sealRecordId = sealRecordId; }
    public Long getInstanceId() { return instanceId; }
    public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
    public Long getApprovalRecordId() { return approvalRecordId; }
    public void setApprovalRecordId(Long approvalRecordId) { this.approvalRecordId = approvalRecordId; }
    public Long getSealId() { return sealId; }
    public void setSealId(Long sealId) { this.sealId = sealId; }
    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }
    public String getSealer() { return sealer; }
    public void setSealer(String sealer) { this.sealer = sealer; }
    public Date getSealTime() { return sealTime; }
    public void setSealTime(Date sealTime) { this.sealTime = sealTime; }

    public String getSealName() { return sealName; }
    public void setSealName(String sealName) { this.sealName = sealName; }
    public String getSealImage() { return sealImage; }
    public void setSealImage(String sealImage) { this.sealImage = sealImage; }
    public Double getPosX() { return posX; }
    public void setPosX(Double posX) { this.posX = posX; }
    public Double getPosY() { return posY; }
    public void setPosY(Double posY) { this.posY = posY; }
    public Integer getSealWidth() { return sealWidth; }
    public void setSealWidth(Integer sealWidth) { this.sealWidth = sealWidth; }
    public Integer getSealHeight() { return sealHeight; }
    public void setSealHeight(Integer sealHeight) { this.sealHeight = sealHeight; }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE)
            .append("sealRecordId", getSealRecordId())
            .append("instanceId", getInstanceId())
            .append("sealId", getSealId())
            .toString();
    }
}
