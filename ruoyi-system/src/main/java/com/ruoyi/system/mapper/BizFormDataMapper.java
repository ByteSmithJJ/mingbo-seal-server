package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BizFormData;

public interface BizFormDataMapper
{
    public BizFormData selectFormDataById(Long dataId);
    public BizFormData selectFormDataByInstanceId(Long instanceId);
    public int insertFormData(BizFormData formData);
    public int updateFormData(BizFormData formData);
}
