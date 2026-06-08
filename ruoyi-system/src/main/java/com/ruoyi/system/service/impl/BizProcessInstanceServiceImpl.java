package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.BizApprovalRecord;
import com.ruoyi.system.domain.BizFormData;
import com.ruoyi.system.domain.BizProcessDefinition;
import com.ruoyi.system.domain.BizProcessInstance;
import com.ruoyi.system.mapper.BizProcessInstanceMapper;
import com.ruoyi.system.service.IBizApprovalRecordService;
import com.ruoyi.system.service.IBizFormDataService;
import com.ruoyi.system.service.IBizProcessDefinitionService;
import com.ruoyi.system.service.IBizProcessInstanceService;

@Service
public class BizProcessInstanceServiceImpl implements IBizProcessInstanceService
{
    @Autowired
    private BizProcessInstanceMapper instanceMapper;

    @Autowired
    private IBizProcessDefinitionService definitionService;

    @Autowired
    private IBizFormDataService formDataService;

    @Autowired
    private IBizApprovalRecordService approvalRecordService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public BizProcessInstance selectInstanceById(Long instanceId)
    {
        return instanceMapper.selectInstanceById(instanceId);
    }

    @Override
    public BizProcessInstance selectInstanceByProcInstId(String procInstId)
    {
        return instanceMapper.selectInstanceByProcInstId(procInstId);
    }

    @Override
    public List<BizProcessInstance> selectInstanceList(BizProcessInstance instance)
    {
        return instanceMapper.selectInstanceList(instance);
    }

    @Override
    public int insertInstance(BizProcessInstance instance)
    {
        return instanceMapper.insertInstance(instance);
    }

    @Override
    public int updateInstance(BizProcessInstance instance)
    {
        return instanceMapper.updateInstance(instance);
    }

    @Override
    @Transactional
    public Long startProcess(Long definitionId, String title, String formData, String username)
    {
        BizProcessDefinition definition = definitionService.selectDefinitionById(definitionId);
        if (definition == null || !"1".equals(definition.getStatus()))
        {
            throw new RuntimeException("流程定义不可用");
        }

        String businessNo = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        BizProcessInstance instance = new BizProcessInstance();
        instance.setDefinitionId(definitionId);
        instance.setDefinitionName(definition.getDefinitionName());
        instance.setFormTemplateId(definition.getFormTemplateId());
        instance.setBusinessNo(businessNo);
        instance.setTitle(title);
        instance.setStatus("0");
        instance.setApplicant(username);
        instance.setApplyTime(new Date());
        instance.setCreateBy(username);
        instanceMapper.insertInstance(instance);

        org.flowable.engine.repository.ProcessDefinition flowableDef =
            repositoryService.createProcessDefinitionQuery()
                .deploymentId(definition.getDeploymentId())
                .singleResult();
        if (flowableDef == null)
        {
            throw new RuntimeException("Flowable 未找到已部署的流程定义");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("initiator", username);
        variables.put("bizInstanceId", instance.getInstanceId().toString());
        org.flowable.engine.runtime.ProcessInstance flowableInstance =
            runtimeService.startProcessInstanceById(flowableDef.getId(), instance.getInstanceId().toString(), variables);

        BizProcessInstance update = new BizProcessInstance();
        update.setInstanceId(instance.getInstanceId());
        update.setProcInstId(flowableInstance.getId());
        instanceMapper.updateInstance(update);

        BizFormData fd = new BizFormData();
        fd.setInstanceId(instance.getInstanceId());
        fd.setTemplateId(definition.getFormTemplateId());
        fd.setFormData(formData);
        fd.setCreateBy(username);
        formDataService.insertFormData(fd);

        // 插入发起申请记录
        BizApprovalRecord startRecord = new BizApprovalRecord();
        startRecord.setInstanceId(instance.getInstanceId());
        startRecord.setNodeCode("start");
        startRecord.setNodeName("提交申请");
        startRecord.setApprover(username);
        startRecord.setResult("3");
        startRecord.setComment("");
        startRecord.setApproveTime(new Date());
        startRecord.setCostDuration(0L);
        approvalRecordService.insertRecord(startRecord);

        // 自动完成发起人自身的任务（兜底方案：即使 BPMN 监听器未注入也能生效）
        try {
            autoCompleteInitiatorTasks(instance.getInstanceId(), flowableInstance.getId(), username);
        } catch (Exception e) {
            System.out.println("[发起人自动通过] 异常: " + e.getMessage());
            e.printStackTrace();
        }

        return instance.getInstanceId();
    }

    /**
     * 循环自动完成流程发起人自己的任务
     */
    private void autoCompleteInitiatorTasks(Long instanceId, String procInstId, String username)
    {
        int maxLoop = 20;
        while (maxLoop-- > 0)
        {
            List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(procInstId)
                .taskAssignee(username)
                .list();
            if (tasks.isEmpty()) break;

            for (Task task : tasks)
            {
                System.out.println("[发起人自动通过] taskId=" + task.getId() + " name=" + task.getName());
                taskService.setVariables(task.getId(), Map.of(
                    "approvalResult", "0",
                    "approvalComment", "发起人自动通过",
                    "approver", username
                ));
                taskService.complete(task.getId());

                // 更新实例当前节点
                BizProcessInstance nodeUpdate = new BizProcessInstance();
                nodeUpdate.setInstanceId(instanceId);
                nodeUpdate.setCurrentNodeCode(task.getTaskDefinitionKey());
                nodeUpdate.setCurrentNodeName(task.getName());
                instanceMapper.updateInstance(nodeUpdate);

                // 手动创建审批记录（避免监听器未注入时记录丢失）
                BizApprovalRecord record = new BizApprovalRecord();
                record.setInstanceId(instanceId);
                record.setTaskId(task.getId());
                record.setNodeCode(task.getTaskDefinitionKey());
                record.setNodeName(task.getName());
                record.setApprover(username);
                record.setResult("5");
                record.setComment("发起人自动通过");
                record.setApproveTime(new Date());
                record.setCostDuration(System.currentTimeMillis() - task.getCreateTime().getTime());
                approvalRecordService.insertRecord(record);
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectInstanceCountByDay(int days)
    {
        return instanceMapper.selectInstanceCountByDay(days);
    }

    @Override
    @Transactional
    public Long withdrawInstance(Long instanceId)
    {
        BizProcessInstance instance = instanceMapper.selectInstanceById(instanceId);
        if (instance == null || !"0".equals(instance.getStatus()))
        {
            throw new RuntimeException("只有审批中的流程才能撤回");
        }

        runtimeService.deleteProcessInstance(instance.getProcInstId(), "申请人撤回");

        // 插入撤回审批记录
        BizApprovalRecord record = new BizApprovalRecord();
        record.setInstanceId(instanceId);
        record.setNodeCode(instance.getCurrentNodeCode() != null ? instance.getCurrentNodeCode() : "withdraw");
        record.setNodeName(instance.getCurrentNodeName() != null ? instance.getCurrentNodeName() : "撤回申请");
        record.setApprover(instance.getApplicant());
        record.setResult("4");
        record.setComment("申请人撤回");
        record.setApproveTime(new Date());
        record.setCostDuration(0L);
        approvalRecordService.insertRecord(record);

        BizProcessInstance update = new BizProcessInstance();
        update.setInstanceId(instanceId);
        update.setStatus("3");
        update.setCompleteTime(new Date());
        instanceMapper.updateInstance(update);

        return instanceId;
    }
}
