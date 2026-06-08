package com.ruoyi.system.service.impl;

import java.util.List;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BizProcessDefinition;
import com.ruoyi.system.mapper.BizProcessDefinitionMapper;
import com.ruoyi.system.service.IBizProcessDefinitionService;

@Service
public class BizProcessDefinitionServiceImpl implements IBizProcessDefinitionService
{
    @Autowired
    private BizProcessDefinitionMapper definitionMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public BizProcessDefinition selectDefinitionById(Long definitionId)
    {
        return definitionMapper.selectDefinitionById(definitionId);
    }

    @Override
    public List<BizProcessDefinition> selectDefinitionList(BizProcessDefinition definition)
    {
        return definitionMapper.selectDefinitionList(definition);
    }

    @Override
    public int insertDefinition(BizProcessDefinition definition)
    {
        return definitionMapper.insertDefinition(definition);
    }

    @Override
    public int updateDefinition(BizProcessDefinition definition)
    {
        return definitionMapper.updateDefinition(definition);
    }

    @Override
    public int deleteDefinitionByIds(Long[] definitionIds)
    {
        return definitionMapper.deleteDefinitionByIds(definitionIds);
    }

    @Override
    public boolean checkDefinitionKeyUnique(BizProcessDefinition definition)
    {
        Long definitionId = definition.getDefinitionId() == null ? -1L : definition.getDefinitionId();
        BizProcessDefinition info = definitionMapper.checkDefinitionKeyUnique(definition);
        if (info != null && info.getDefinitionId().longValue() != definitionId.longValue())
        {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public int deployDefinition(Long definitionId)
    {
        BizProcessDefinition definition = definitionMapper.selectDefinitionById(definitionId);
        if (definition == null)
        {
            throw new RuntimeException("流程定义不存在");
        }
        if (!"0".equals(definition.getStatus()))
        {
            throw new RuntimeException("只有草稿状态的流程才能部署");
        }

        String bpmnXml = definition.getBpmnXml();
        if (StringUtils.isEmpty(bpmnXml))
        {
            throw new RuntimeException("BPMN XML 为空，无法部署");
        }

        // 部署到 Flowable
        Deployment deployment = repositoryService.createDeployment()
            .key(definition.getDefinitionKey())
            .name(definition.getDefinitionName())
            .addString(definition.getDefinitionKey() + ".bpmn20.xml", bpmnXml)
            .deploy();

        // 更新 biz 表状态 + deployment_id
        BizProcessDefinition update = new BizProcessDefinition();
        update.setDefinitionId(definitionId);
        update.setStatus("1");
        update.setDeploymentId(deployment.getId());
        return definitionMapper.updateDefinition(update);
    }

    @Override
    public int changeStatus(Long definitionId, String status)
    {
        BizProcessDefinition definition = new BizProcessDefinition();
        definition.setDefinitionId(definitionId);
        definition.setStatus(status);
        return definitionMapper.updateDefinition(definition);
    }

}
