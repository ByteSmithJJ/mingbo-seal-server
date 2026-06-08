package com.ruoyi.web.controller.seal;

import java.util.*;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.system.domain.BizApprovalRecord;
import com.ruoyi.system.domain.BizProcessInstance;
import com.ruoyi.system.domain.BizSealPosition;
import com.ruoyi.system.domain.BizSealRecord;
import com.ruoyi.system.service.*;

@RestController
@RequestMapping("/process/task")
public class BizApprovalTaskController extends BaseController
{
    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IBizProcessInstanceService instanceService;

    @Autowired
    private IBizApprovalRecordService approvalRecordService;

    @Autowired
    private IBizSealRecordService sealRecordService;

    @Autowired
    private IBizSealPositionService sealPositionService;

    @GetMapping("/pending")
    @PreAuthorize("@ss.hasPermi('process:task:list')")
    public TableDataInfo pending()
    {
        String username = getUsername();
        List<Task> tasks = taskService.createTaskQuery()
            .taskAssignee(username)
            .orderByTaskCreateTime().desc()
            .list();

        // 过滤：仅保留有对应业务实例的任务
        List<Map<String, Object>> validTasks = new ArrayList<>();
        for (Task task : tasks)
        {
            BizProcessInstance instance = instanceService.selectInstanceByProcInstId(task.getProcessInstanceId());
            if (instance == null) continue;

            Map<String, Object> item = new HashMap<>();
            item.put("taskId", task.getId());
            item.put("taskName", task.getName());
            item.put("instanceId", instance.getInstanceId());
            item.put("businessNo", instance.getBusinessNo());
            item.put("title", instance.getTitle());
            item.put("applicant", instance.getApplicant());
            item.put("applyTime", instance.getApplyTime());
            validTasks.add(item);
        }

        int total = validTasks.size();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        int pageNum = pageDomain.getPageNum();
        int pageSize = pageDomain.getPageSize();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<Map<String, Object>> result = fromIndex < toIndex
            ? validTasks.subList(fromIndex, toIndex)
            : new ArrayList<>();

        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(com.ruoyi.common.constant.HttpStatus.SUCCESS);
        dataInfo.setMsg("查询成功");
        dataInfo.setTotal(total);
        dataInfo.setRows(result);
        return dataInfo;
    }

    @PutMapping("/{taskId}/approve")
    @PreAuthorize("@ss.hasPermi('process:task:approve')")
    public AjaxResult approve(@PathVariable String taskId, @RequestBody ApproveRequest request)
    {
        try
        {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (task == null) return error("任务不存在");

            Long instanceId = getBizInstanceId(taskId);

            Map<String, Object> variables = new HashMap<>();
            variables.put("approvalResult", "0");
            variables.put("approvalComment", request.getComment());
            variables.put("approver", getUsername());
            taskService.complete(taskId, variables);

            insertApprovalRecord(task, instanceId, "0", request.getComment());
            autoSeal(instanceId, task, taskId);

            // 检查流程是否已结束，如果是则更新状态为已通过
            org.flowable.engine.runtime.ProcessInstance pi =
                runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (pi == null)
            {
                BizProcessInstance update = new BizProcessInstance();
                update.setInstanceId(instanceId);
                update.setStatus("1");
                update.setCompleteTime(new Date());
                instanceService.updateInstance(update);
            }
            else
            {
                // 流程未结束，更新当前节点为下一任务
                List<Task> nextTasks = taskService.createTaskQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .list();
                if (!nextTasks.isEmpty())
                {
                    Task next = nextTasks.get(0);
                    BizProcessInstance nodeUpdate = new BizProcessInstance();
                    nodeUpdate.setInstanceId(instanceId);
                    nodeUpdate.setCurrentNodeCode(next.getTaskDefinitionKey());
                    nodeUpdate.setCurrentNodeName(next.getName());
                    instanceService.updateInstance(nodeUpdate);
                }
            }

            return success();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/reject")
    @PreAuthorize("@ss.hasPermi('process:task:approve')")
    public AjaxResult reject(@PathVariable String taskId, @RequestBody ApproveRequest request)
    {
        try
        {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (task == null) return error("任务不存在");

            Long instanceId = getBizInstanceId(taskId);

            Map<String, Object> variables = new HashMap<>();
            variables.put("approvalResult", "1");
            variables.put("approvalComment", request.getComment());
            variables.put("approver", getUsername());
            taskService.complete(taskId, variables);

            insertApprovalRecord(task, instanceId, "1", request.getComment());

            BizProcessInstance update = new BizProcessInstance();
            update.setInstanceId(instanceId);
            update.setStatus("2");
            update.setCompleteTime(new Date());
            instanceService.updateInstance(update);

            return success();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/return")
    @PreAuthorize("@ss.hasPermi('process:task:approve')")
    public AjaxResult returnTask(@PathVariable String taskId, @RequestBody ApproveRequest request)
    {
        try
        {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (task == null) return error("任务不存在");

            Long instanceId = getBizInstanceId(taskId);

            // 从历史中找到上一个 userTask（退回目标节点）
            List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .finished()
                .orderByHistoricTaskInstanceEndTime().desc()
                .list();
            String targetActivityId = null;
            String targetNodeName = "上一节点";
            for (HistoricTaskInstance ht : hisTasks)
            {
                if (!ht.getTaskDefinitionKey().equals(task.getTaskDefinitionKey()))
                {
                    targetActivityId = ht.getTaskDefinitionKey();
                    targetNodeName = ht.getName();
                    break;
                }
            }
            if (targetActivityId == null)
            {
                return error("找不到上一审批节点");
            }

            // 创建审批记录（标明退回去向）
            BizApprovalRecord record = new BizApprovalRecord();
            record.setInstanceId(instanceId);
            record.setTaskId(task.getId());
            record.setNodeCode(task.getTaskDefinitionKey());
            record.setNodeName(task.getName() + " → " + targetNodeName);
            record.setApprover(getUsername());
            record.setResult("2");
            record.setComment(request.getComment());
            record.setApproveTime(new Date());
            record.setCostDuration(System.currentTimeMillis() - task.getCreateTime().getTime());
            approvalRecordService.insertRecord(record);

            // 将流程回退到上一节点
            runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(task.getProcessInstanceId())
                .moveActivityIdTo(task.getTaskDefinitionKey(), targetActivityId)
                .changeState();

            // 更新实例当前节点为退回目标节点
            BizProcessInstance nodeUpdate = new BizProcessInstance();
            nodeUpdate.setInstanceId(instanceId);
            nodeUpdate.setCurrentNodeCode(targetActivityId);
            nodeUpdate.setCurrentNodeName(targetNodeName);
            instanceService.updateInstance(nodeUpdate);

            return success();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    @GetMapping("/approved")
    @PreAuthorize("@ss.hasPermi('process:task:list')")
    public TableDataInfo approved()
    {
        String username = getUsername();
        // 从 Flowable 历史中查询当前用户已完成的任务
        List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
            .taskAssignee(username)
            .finished()
            .orderByHistoricTaskInstanceEndTime().desc()
            .list();

        // 过滤：仅保留有对应业务实例且非自动通过的记录
        List<Map<String, Object>> result = new ArrayList<>();
        for (HistoricTaskInstance ht : hisTasks)
        {
            BizProcessInstance instance = instanceService.selectInstanceByProcInstId(ht.getProcessInstanceId());
            if (instance == null) continue;

            BizApprovalRecord query = new BizApprovalRecord();
            query.setTaskId(ht.getId());
            List<BizApprovalRecord> records = approvalRecordService.selectRecordList(query);
            BizApprovalRecord record = records.isEmpty() ? null : records.get(0);

            // 仅保留真正的审批操作：有记录、非自动通过、非发起、非撤回
            if (record == null) continue;
            if ("发起人自动通过".equals(record.getComment())) continue;
            if ("3".equals(record.getResult()) || "4".equals(record.getResult()) || "5".equals(record.getResult())) continue;

            Map<String, Object> item = new HashMap<>();
            item.put("taskId", ht.getId());
            item.put("taskName", ht.getName());
            item.put("instanceId", instance.getInstanceId());
            item.put("businessNo", instance.getBusinessNo());
            item.put("title", instance.getTitle());
            item.put("applicant", instance.getApplicant());
            item.put("applyTime", instance.getApplyTime());
            item.put("result", record != null ? record.getResult() : "");
            item.put("comment", record != null ? record.getComment() : "");
            item.put("approveTime", ht.getEndTime());
            item.put("costDuration", ht.getDurationInMillis());
            result.add(item);
        }

        int total = result.size();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        int pageNum = pageDomain.getPageNum();
        int pageSize = pageDomain.getPageSize();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<Map<String, Object>> paged = fromIndex < toIndex
            ? result.subList(fromIndex, toIndex)
            : new ArrayList<>();

        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(com.ruoyi.common.constant.HttpStatus.SUCCESS);
        dataInfo.setMsg("查询成功");
        dataInfo.setTotal(total);
        dataInfo.setRows(paged);
        return dataInfo;
    }

    private Long getBizInstanceId(String taskId)
    {
        Map<String, Object> vars = taskService.getVariables(taskId);
        Object bizId = vars.get("bizInstanceId");
        return bizId != null ? Long.valueOf(bizId.toString()) : null;
    }

    private void autoSeal(Long instanceId, Task task, String taskId)
    {
        BizProcessInstance instance = instanceService.selectInstanceById(instanceId);
        if (instance == null || instance.getFormTemplateId() == null) return;

        BizSealPosition query = new BizSealPosition();
        query.setFormTemplateId(instance.getFormTemplateId());
        List<BizSealPosition> positions = sealPositionService.selectPositionList(query);

        if (positions.isEmpty()) return;

        BizApprovalRecord recordQuery = new BizApprovalRecord();
        recordQuery.setInstanceId(instanceId);
        recordQuery.setTaskId(taskId);
        List<BizApprovalRecord> records = approvalRecordService.selectRecordList(recordQuery);
        Long approvalRecordId = null;
        if (!records.isEmpty()) approvalRecordId = records.get(0).getRecordId();

        // 检查已盖章的位置，避免重复
        List<BizSealRecord> existingSeals = sealRecordService.selectSealRecordsByInstanceId(instanceId);
        java.util.Set<Long> stampedPositions = new java.util.HashSet<>();
        for (BizSealRecord s : existingSeals) {
            if (s.getPositionId() != null) stampedPositions.add(s.getPositionId());
        }

        Date now = new Date();
        String currentNodeCode = task.getTaskDefinitionKey();

        for (BizSealPosition pos : positions)
        {
            // 已盖过章，跳过
            if (stampedPositions.contains(pos.getPositionId())) continue;
            // 位置绑定了特定节点且不匹配当前节点，跳过
            if (pos.getNodeCode() != null && !pos.getNodeCode().isEmpty()
                && !pos.getNodeCode().equals(currentNodeCode)) continue;

            BizSealRecord sealRecord = new BizSealRecord();
            sealRecord.setInstanceId(instanceId);
            sealRecord.setApprovalRecordId(approvalRecordId);
            sealRecord.setSealId(pos.getSealId());
            sealRecord.setPositionId(pos.getPositionId());
            sealRecord.setSealer(getUsername());
            sealRecord.setSealTime(now);
            sealRecord.setCreateBy(getUsername());
            sealRecordService.insertRecord(sealRecord);
        }
    }

    private void insertApprovalRecord(Task task, Long instanceId, String result, String comment)
    {
        // 插入审批记录
        BizApprovalRecord record = new BizApprovalRecord();
        record.setInstanceId(instanceId);
        record.setTaskId(task.getId());
        record.setNodeCode(task.getTaskDefinitionKey());
        record.setNodeName(task.getName());
        record.setApprover(getUsername());
        record.setResult(result);
        record.setComment(comment);
        record.setApproveTime(new Date());
        record.setCostDuration(System.currentTimeMillis() - task.getCreateTime().getTime());
        approvalRecordService.insertRecord(record);
    }

    public static class ApproveRequest
    {
        private String comment;
        public String getComment() { return comment; }
        public void setComment(String c) { this.comment = c; }
    }
}
