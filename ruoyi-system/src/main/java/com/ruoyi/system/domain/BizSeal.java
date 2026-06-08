package com.ruoyi.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 印章信息表 biz_seal
 *
 * @author ruoyi
 */
public class BizSeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 印章ID */
    private Long sealId;

    /** 印章名称 */
    private String sealName;

    /** 印章编号（唯一） */
    private String sealCode;

    /** 印章类型（字典: seal_type） */
    private String sealType;

    /** 印章图片路径 */
    private String sealImage;

    /** 状态（0启用 1停用） */
    private String status;

    public Long getSealId()
    {
        return sealId;
    }

    public void setSealId(Long sealId)
    {
        this.sealId = sealId;
    }

    @NotBlank(message = "印章名称不能为空")
    @Size(min = 0, max = 64, message = "印章名称不能超过64个字符")
    public String getSealName()
    {
        return sealName;
    }

    public void setSealName(String sealName)
    {
        this.sealName = sealName;
    }

    @NotBlank(message = "印章编号不能为空")
    @Size(min = 0, max = 64, message = "印章编号不能超过64个字符")
    public String getSealCode()
    {
        return sealCode;
    }

    public void setSealCode(String sealCode)
    {
        this.sealCode = sealCode;
    }

    public String getSealType()
    {
        return sealType;
    }

    public void setSealType(String sealType)
    {
        this.sealType = sealType;
    }

    public String getSealImage()
    {
        return sealImage;
    }

    public void setSealImage(String sealImage)
    {
        this.sealImage = sealImage;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("sealId", getSealId())
            .append("sealName", getSealName())
            .append("sealCode", getSealCode())
            .append("sealType", getSealType())
            .append("sealImage", getSealImage())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
