package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizFormTemplate;

public interface IBizFormTemplateService
{
    public BizFormTemplate selectTemplateById(Long templateId);
    public List<BizFormTemplate> selectTemplateList(BizFormTemplate template);
    public int insertTemplate(BizFormTemplate template);
    public int updateTemplate(BizFormTemplate template);
    public int deleteTemplateByIds(Long[] templateIds);
    public boolean checkTemplateKeyUnique(BizFormTemplate template);
}
