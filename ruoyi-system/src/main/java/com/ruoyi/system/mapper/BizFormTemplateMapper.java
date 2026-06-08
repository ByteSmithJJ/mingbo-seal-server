package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizFormTemplate;

public interface BizFormTemplateMapper
{
    public BizFormTemplate selectTemplateById(Long templateId);
    public List<BizFormTemplate> selectTemplateList(BizFormTemplate template);
    public int insertTemplate(BizFormTemplate template);
    public int updateTemplate(BizFormTemplate template);
    public int deleteTemplateById(Long templateId);
    public int deleteTemplateByIds(Long[] templateIds);
    public BizFormTemplate checkTemplateKeyUnique(BizFormTemplate template);
}
