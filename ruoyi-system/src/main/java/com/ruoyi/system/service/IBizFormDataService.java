package com.ruoyi.system.service;

import com.ruoyi.system.domain.BizFormData;

public interface IBizFormDataService
{
    public BizFormData selectFormDataById(Long dataId);
    public BizFormData selectFormDataByInstanceId(Long instanceId);
    public int insertFormData(BizFormData formData);
    public int updateFormData(BizFormData formData);
}
