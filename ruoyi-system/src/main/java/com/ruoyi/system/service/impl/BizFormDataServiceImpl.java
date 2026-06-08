package com.ruoyi.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizFormData;
import com.ruoyi.system.mapper.BizFormDataMapper;
import com.ruoyi.system.service.IBizFormDataService;

@Service
public class BizFormDataServiceImpl implements IBizFormDataService
{
    @Autowired
    private BizFormDataMapper formDataMapper;

    @Override
    public BizFormData selectFormDataById(Long dataId) { return formDataMapper.selectFormDataById(dataId); }

    @Override
    public BizFormData selectFormDataByInstanceId(Long instanceId) { return formDataMapper.selectFormDataByInstanceId(instanceId); }

    @Override
    public int insertFormData(BizFormData formData) { return formDataMapper.insertFormData(formData); }

    @Override
    public int updateFormData(BizFormData formData) { return formDataMapper.updateFormData(formData); }
}
