package com.ruoyi.web.controller.seal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizProcessDefinition;
import com.ruoyi.system.service.IBizProcessDefinitionService;

@RestController
@RequestMapping("/process/definition")
public class BizProcessDefinitionController extends BaseController
{
    @Autowired
    private IBizProcessDefinitionService definitionService;

    @PreAuthorize("@ss.hasPermi('process:definition:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProcessDefinition definition)
    {
        startPage();
        List<BizProcessDefinition> list = definitionService.selectDefinitionList(definition);
        return getDataTable(list);
    }

    @GetMapping(value = "/{definitionId}")
    public AjaxResult getInfo(@PathVariable Long definitionId)
    {
        return success(definitionService.selectDefinitionById(definitionId));
    }

    @PreAuthorize("@ss.hasPermi('process:definition:add')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizProcessDefinition definition)
    {
        if (!definitionService.checkDefinitionKeyUnique(definition))
        {
            return error("流程标识'" + definition.getDefinitionKey() + "'已存在");
        }
        definition.setVersion(1);
        definition.setStatus("0");
        definition.setCreateBy(getUsername());
        return toAjax(definitionService.insertDefinition(definition));
    }

    @PreAuthorize("@ss.hasPermi('process:definition:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizProcessDefinition definition)
    {
        if (!definitionService.checkDefinitionKeyUnique(definition))
        {
            return error("流程标识'" + definition.getDefinitionKey() + "'已存在");
        }
        definition.setUpdateBy(getUsername());
        return toAjax(definitionService.updateDefinition(definition));
    }

    @PreAuthorize("@ss.hasPermi('process:definition:remove')")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{definitionIds}")
    public AjaxResult remove(@PathVariable Long[] definitionIds)
    {
        return toAjax(definitionService.deleteDefinitionByIds(definitionIds));
    }

    @PreAuthorize("@ss.hasPermi('process:definition:deploy')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PostMapping("/deploy/{definitionId}")
    public AjaxResult deploy(@PathVariable Long definitionId)
    {
        int rows = definitionService.deployDefinition(definitionId);
        return toAjax(rows);
    }

    @PreAuthorize("@ss.hasPermi('process:definition:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BizProcessDefinition definition)
    {
        return toAjax(definitionService.changeStatus(definition.getDefinitionId(), definition.getStatus()));
    }

    /**
     * 根据表单模板ID获取BPMN用户任务节点列表（供印章位置配置用）
     */
    @GetMapping("/nodesByTemplate/{templateId}")
    public AjaxResult nodesByTemplate(@PathVariable Long templateId)
    {
        BizProcessDefinition query = new BizProcessDefinition();
        query.setFormTemplateId(templateId);
        query.setStatus("1"); // 只查已部署的
        List<BizProcessDefinition> defs = definitionService.selectDefinitionList(query);
        if (defs.isEmpty()) return success(new ArrayList<>());

        String bpmn = defs.get(0).getBpmnXml();
        if (bpmn == null || bpmn.isEmpty()) return success(new ArrayList<>());

        // 正则提取 userTask 的 id 和 name
        List<Map<String, String>> nodes = new ArrayList<>();
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
            "<(?:bpmn:)?userTask\\s+id=\"(\\S+?)\"[^>]*name=\"(\\S+?)\"[^>]*>"
        ).matcher(bpmn);
        while (m.find()) {
            Map<String, String> node = new HashMap<>();
            node.put("activityId", m.group(1));
            node.put("nodeName", m.group(2));
            nodes.add(node);
        }
        return success(nodes);
    }
}
