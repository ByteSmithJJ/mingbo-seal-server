package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizProcessDefinition;

public interface BizProcessDefinitionMapper
{
    public BizProcessDefinition selectDefinitionById(Long definitionId);
    public List<BizProcessDefinition> selectDefinitionList(BizProcessDefinition definition);
    public int insertDefinition(BizProcessDefinition definition);
    public int updateDefinition(BizProcessDefinition definition);
    public int deleteDefinitionById(Long definitionId);
    public int deleteDefinitionByIds(Long[] definitionIds);
    public BizProcessDefinition checkDefinitionKeyUnique(BizProcessDefinition definition);
}
