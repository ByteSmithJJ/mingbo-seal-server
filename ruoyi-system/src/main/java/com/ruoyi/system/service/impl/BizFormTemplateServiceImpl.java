package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizFormTemplate;
import com.ruoyi.system.mapper.BizFormTemplateMapper;
import com.ruoyi.system.service.IBizFormTemplateService;

@Service
public class BizFormTemplateServiceImpl implements IBizFormTemplateService
{
    @Autowired
    private BizFormTemplateMapper templateMapper;

    @Override
    public BizFormTemplate selectTemplateById(Long templateId)
    {
        return templateMapper.selectTemplateById(templateId);
    }

    @Override
    public List<BizFormTemplate> selectTemplateList(BizFormTemplate template)
    {
        return templateMapper.selectTemplateList(template);
    }

    @Override
    public int insertTemplate(BizFormTemplate template)
    {
        return templateMapper.insertTemplate(template);
    }

    @Override
    public int updateTemplate(BizFormTemplate template)
    {
        return templateMapper.updateTemplate(template);
    }

    @Override
    public int deleteTemplateByIds(Long[] templateIds)
    {
        return templateMapper.deleteTemplateByIds(templateIds);
    }

    @Override
    public boolean checkTemplateKeyUnique(BizFormTemplate template)
    {
        Long templateId = template.getTemplateId() == null ? -1L : template.getTemplateId();
        BizFormTemplate info = templateMapper.checkTemplateKeyUnique(template);
        if (info != null && info.getTemplateId().longValue() != templateId.longValue())
        {
            return false;
        }
        return true;
    }
}
