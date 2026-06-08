package com.ruoyi.system.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.system.domain.BizApprovalRecord;
import com.ruoyi.system.domain.BizProcessInstance;
import com.ruoyi.system.mapper.BizProcessInstanceMapper;
import com.ruoyi.system.service.IBizApprovalRecordService;

@Component("approvalTaskListener")
public class ApprovalTaskListener implements TaskListener
{
    private static final long serialVersionUID = 1L;

    private static BizProcessInstanceMapper instanceMapper;
    private static IBizApprovalRecordService approvalRecordService;
    private static TaskService taskService;

    @Autowired
    public void setInstanceMapper(BizProcessInstanceMapper mapper) { ApprovalTaskListener.instanceMapper = mapper; }

    @Autowired
    public void setApprovalRecordService(IBizApprovalRecordService s) { ApprovalTaskListener.approvalRecordService = s; }

    @Autowired
    public void setTaskService(TaskService ts) { ApprovalTaskListener.taskService = ts; }

    @Override
    public void notify(DelegateTask delegateTask)
    {
        Object bizIdVar = delegateTask.getVariable("bizInstanceId");
        if (bizIdVar == null) return;
        Long instanceId = Long.valueOf(bizIdVar.toString());

        String eventName = delegateTask.getEventName();
        System.out.println("[ApprovalTaskListener] event=" + eventName + " node=" + delegateTask.getName());

        if (TaskListener.EVENTNAME_CREATE.equals(eventName))
        {
            // 更新当前节点信息
            BizProcessInstance update = new BizProcessInstance();
            update.setInstanceId(instanceId);
            update.setCurrentNodeCode(delegateTask.getTaskDefinitionKey());
            update.setCurrentNodeName(delegateTask.getName());
            instanceMapper.updateInstance(update);

            // 发起人任务：自动跳过
            String assignee = delegateTask.getAssignee();
            Object initiatorObj = delegateTask.getVariable("initiator");
            String initiator = initiatorObj != null ? initiatorObj.toString() : null;
            System.out.println("[发起人检测] instanceId=" + instanceId + " node=" + delegateTask.getName()
                + " assignee=" + assignee + " initiator=" + initiator);

            if (assignee != null && assignee.equals(initiator))
            {
                System.out.println("[发起人自动通过] instanceId=" + instanceId + " node=" + delegateTask.getName());
                autoComplete(delegateTask, instanceId);
            }
        }
        else if (TaskListener.EVENTNAME_COMPLETE.equals(eventName))
        {
            String result = (String) delegateTask.getVariable("approvalResult");
            String comment = (String) delegateTask.getVariable("approvalComment");
            String approver = (String) delegateTask.getVariable("approver");

            BizApprovalRecord record = new BizApprovalRecord();
            record.setInstanceId(instanceId);
            record.setTaskId(delegateTask.getId());
            record.setNodeCode(delegateTask.getTaskDefinitionKey());
            record.setNodeName(delegateTask.getName());
            record.setApprover(approver);
            record.setResult(result);
            record.setComment(comment);
            record.setApproveTime(new Date());
            record.setCostDuration(System.currentTimeMillis() - delegateTask.getCreateTime().getTime());
            approvalRecordService.insertRecord(record);
        }
    }

    private void autoComplete(DelegateTask delegateTask, Long instanceId)
    {
        if (taskService == null)
        {
            System.out.println("[发起人自动通过] 失败: taskService=" + taskService);
            return;
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("approvalResult", "0");
        vars.put("approvalComment", "发起人自动通过");
        vars.put("approver", delegateTask.getAssignee());
        taskService.setVariables(delegateTask.getId(), vars);
        taskService.complete(delegateTask.getId());
    }
}
