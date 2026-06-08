package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class BizFormData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long dataId;
    private Long instanceId;
    private Long templateId;
    private String formData;

    public Long getDataId() { return dataId; }
    public void setDataId(Long dataId) { this.dataId = dataId; }
    public Long getInstanceId() { return instanceId; }
    public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }
    public String getFormData() { return formData; }
    public void setFormData(String formData) { this.formData = formData; }
}
