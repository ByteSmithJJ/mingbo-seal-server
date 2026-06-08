package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 印章位置配置表 biz_seal_position
 *
 * @author ruoyi
 */
public class BizSealPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 位置ID */
    private Long positionId;

    /** 印章ID */
    private Long sealId;

    /** 表单模板ID */
    private Long formTemplateId;

    /** 位置名称 */
    private String positionName;

    /** X坐标（相对表单左上角，px） */
    private BigDecimal posX;

    /** Y坐标（相对表单左上角，px） */
    private BigDecimal posY;

    /** 印章显示宽度（px） */
    private Integer sealWidth;

    /** 印章显示高度（px） */
    private Integer sealHeight;

    /** 所在页码 */
    private Integer pageNo;

    /** 排序号 */
    private Integer sort;

    /** 印章名称（非表字段） */
    private String sealName;

    /** 表单模板名称（非表字段） */
    private String templateName;

    /** 绑定审批节点（BPMN activity ID），null 表示所有节点 */
    private String nodeCode;

    public Long getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }

    public Long getSealId()
    {
        return sealId;
    }

    public void setSealId(Long sealId)
    {
        this.sealId = sealId;
    }

    public Long getFormTemplateId()
    {
        return formTemplateId;
    }

    public void setFormTemplateId(Long formTemplateId)
    {
        this.formTemplateId = formTemplateId;
    }

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public BigDecimal getPosX()
    {
        return posX;
    }

    public void setPosX(BigDecimal posX)
    {
        this.posX = posX;
    }

    public BigDecimal getPosY()
    {
        return posY;
    }

    public void setPosY(BigDecimal posY)
    {
        this.posY = posY;
    }

    public Integer getSealWidth()
    {
        return sealWidth;
    }

    public void setSealWidth(Integer sealWidth)
    {
        this.sealWidth = sealWidth;
    }

    public Integer getSealHeight()
    {
        return sealHeight;
    }

    public void setSealHeight(Integer sealHeight)
    {
        this.sealHeight = sealHeight;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public String getNodeCode() { return nodeCode; }
    public void setNodeCode(String nodeCode) { this.nodeCode = nodeCode; }

    public String getSealName()
    {
        return sealName;
    }

    public void setSealName(String sealName)
    {
        this.sealName = sealName;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("positionId", getPositionId())
            .append("sealId", getSealId())
            .append("formTemplateId", getFormTemplateId())
            .append("positionName", getPositionName())
            .append("posX", getPosX())
            .append("posY", getPosY())
            .append("sealWidth", getSealWidth())
            .append("sealHeight", getSealHeight())
            .append("pageNo", getPageNo())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
