package com.ruoyi.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程定义表 biz_process_definition
 *
 * @author ruoyi
 */
public class BizProcessDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long definitionId;
    private String definitionName;
    private String definitionKey;
    private String bpmnXml;
    private Long formTemplateId;
    private String deploymentId;
    private Integer version;
    private String status;
    private String delFlag;

    public Long getDefinitionId() { return definitionId; }
    public void setDefinitionId(Long definitionId) { this.definitionId = definitionId; }

    @NotBlank(message = "流程名称不能为空")
    @Size(min = 0, max = 100, message = "流程名称不能超过100个字符")
    public String getDefinitionName() { return definitionName; }
    public void setDefinitionName(String definitionName) { this.definitionName = definitionName; }

    @NotBlank(message = "流程标识不能为空")
    @Size(min = 0, max = 64, message = "流程标识不能超过64个字符")
    public String getDefinitionKey() { return definitionKey; }
    public void setDefinitionKey(String definitionKey) { this.definitionKey = definitionKey; }

    public String getBpmnXml() { return bpmnXml; }
    public void setBpmnXml(String bpmnXml) { this.bpmnXml = bpmnXml; }

    public Long getFormTemplateId() { return formTemplateId; }
    public void setFormTemplateId(Long formTemplateId) { this.formTemplateId = formTemplateId; }

    public String getDeploymentId() { return deploymentId; }
    public void setDeploymentId(String deploymentId) { this.deploymentId = deploymentId; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("definitionId", getDefinitionId())
            .append("definitionName", getDefinitionName())
            .append("definitionKey", getDefinitionKey())
            .append("formTemplateId", getFormTemplateId())
            .append("deploymentId", getDeploymentId())
            .append("version", getVersion())
            .append("status", getStatus())
            .toString();
    }
}
