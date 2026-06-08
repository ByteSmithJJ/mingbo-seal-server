package com.ruoyi.web.controller.seal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.BizApprovalRecord;
import com.ruoyi.system.domain.BizFormData;
import com.ruoyi.system.domain.BizProcessInstance;
import com.ruoyi.system.domain.BizSealRecord;
import com.ruoyi.system.service.IBizApprovalRecordService;
import com.ruoyi.system.service.IBizFormDataService;
import com.ruoyi.system.service.IBizProcessInstanceService;
import com.ruoyi.system.service.IBizSealRecordService;

@RestController
@RequestMapping("/process/instance")
public class BizProcessInstanceController extends BaseController
{
    @Autowired
    private IBizProcessInstanceService instanceService;

    @Autowired
    private IBizFormDataService formDataService;

    @Autowired
    private IBizApprovalRecordService approvalRecordService;

    @Autowired
    private IBizSealRecordService sealRecordService;

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/start")
    @PreAuthorize("@ss.hasPermi('process:instance:start')")
    public AjaxResult start(@RequestBody StartRequest request)
    {
        try
        {
            Long instanceId = instanceService.startProcess(
                request.getDefinitionId(),
                request.getTitle(),
                request.getFormData(),
                getUsername()
            );
            return success(instanceId);
        }
        catch (RuntimeException e)
        {
            return error(e.getMessage());
        }
    }

    @GetMapping("/myList")
    @PreAuthorize("@ss.hasPermi('process:instance:list')")
    public TableDataInfo myList(BizProcessInstance instance)
    {
        startPage();
        instance.setApplicant(getUsername());
        List<BizProcessInstance> list = instanceService.selectInstanceList(instance);
        return getDataTable(list);
    }

    @GetMapping("/{instanceId}")
    @PreAuthorize("@ss.hasPermi('process:instance:list')")
    public AjaxResult getInfo(@PathVariable Long instanceId)
    {
        BizProcessInstance instance = instanceService.selectInstanceById(instanceId);
        BizFormData formData = formDataService.selectFormDataByInstanceId(instanceId);

        BizApprovalRecord query = new BizApprovalRecord();
        query.setInstanceId(instanceId);
        List<BizApprovalRecord> approvalRecords = approvalRecordService.selectRecordList(query);

        List<BizSealRecord> sealRecords = sealRecordService.selectSealRecordsByInstanceId(instanceId);

        // 获取当前活跃节点 ID（用于流程图高亮）
        List<String> activeNodeIds = new ArrayList<>();
        if ("0".equals(instance.getStatus()) && instance.getProcInstId() != null) {
            try {
                activeNodeIds = runtimeService.createActivityInstanceQuery()
                    .processInstanceId(instance.getProcInstId())
                    .unfinished()
                    .list()
                    .stream()
                    .map(ai -> ai.getActivityId())
                    .collect(Collectors.toList());
            } catch (Exception ignored) {}
        }

        return success(new InstanceDetail(instance, formData, approvalRecords, sealRecords, activeNodeIds));
    }

    @PutMapping("/{instanceId}/withdraw")
    @PreAuthorize("@ss.hasPermi('process:instance:start')")
    public AjaxResult withdraw(@PathVariable Long instanceId)
    {
        try
        {
            instanceService.withdrawInstance(instanceId);
            return success();
        }
        catch (RuntimeException e)
        {
            return error(e.getMessage());
        }
    }

    @GetMapping("/trend")
    @PreAuthorize("@ss.hasPermi('process:instance:list')")
    public AjaxResult trend()
    {
        List<Map<String, Object>> list = instanceService.selectInstanceCountByDay(7);
        return success(list);
    }

    public static class StartRequest
    {
        private Long definitionId;
        private String title;
        private String formData;
        public Long getDefinitionId() { return definitionId; }
        public void setDefinitionId(Long id) { this.definitionId = id; }
        public String getTitle() { return title; }
        public void setTitle(String t) { this.title = t; }
        public String getFormData() { return formData; }
        public void setFormData(String d) { this.formData = d; }
    }

    public static class InstanceDetail
    {
        private BizProcessInstance instance;
        private BizFormData formData;
        private List<BizApprovalRecord> approvalRecords;
        private List<BizSealRecord> sealRecords;
        private List<String> activeNodeIds;
        public InstanceDetail(BizProcessInstance i, BizFormData f, List<BizApprovalRecord> a, List<BizSealRecord> s, List<String> n)
        {
            this.instance = i; this.formData = f; this.approvalRecords = a; this.sealRecords = s; this.activeNodeIds = n;
        }
        public BizProcessInstance getInstance() { return instance; }
        public BizFormData getFormData() { return formData; }
        public List<BizApprovalRecord> getApprovalRecords() { return approvalRecords; }
        public List<BizSealRecord> getSealRecords() { return sealRecords; }
        public List<String> getActiveNodeIds() { return activeNodeIds; }
    }
}
