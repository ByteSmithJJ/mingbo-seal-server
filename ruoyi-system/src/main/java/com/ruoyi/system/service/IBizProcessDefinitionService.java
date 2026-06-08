package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizProcessDefinition;

public interface IBizProcessDefinitionService
{
    public BizProcessDefinition selectDefinitionById(Long definitionId);
    public List<BizProcessDefinition> selectDefinitionList(BizProcessDefinition definition);
    public int insertDefinition(BizProcessDefinition definition);
    public int updateDefinition(BizProcessDefinition definition);
    public int deleteDefinitionByIds(Long[] definitionIds);
    public boolean checkDefinitionKeyUnique(BizProcessDefinition definition);
    public int deployDefinition(Long definitionId);
    public int changeStatus(Long definitionId, String status);
}
