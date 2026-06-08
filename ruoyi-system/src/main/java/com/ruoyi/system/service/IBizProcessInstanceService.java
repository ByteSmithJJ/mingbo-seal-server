package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizProcessInstance;

public interface IBizProcessInstanceService
{
    public BizProcessInstance selectInstanceById(Long instanceId);
    public BizProcessInstance selectInstanceByProcInstId(String procInstId);
    public List<BizProcessInstance> selectInstanceList(BizProcessInstance instance);
    public int insertInstance(BizProcessInstance instance);
    public int updateInstance(BizProcessInstance instance);
    public Long startProcess(Long definitionId, String title, String formData, String username);
    public Long withdrawInstance(Long instanceId);
    public List<Map<String, Object>> selectInstanceCountByDay(int days);
}
