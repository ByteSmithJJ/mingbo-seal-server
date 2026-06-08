package com.ruoyi.web.controller.seal;

import java.util.List;
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
import com.ruoyi.system.domain.BizFormTemplate;
import com.ruoyi.system.service.IBizFormTemplateService;

@RestController
@RequestMapping("/form/template")
public class BizFormTemplateController extends BaseController
{
    @Autowired
    private IBizFormTemplateService templateService;

    @PreAuthorize("@ss.hasPermi('form:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizFormTemplate template)
    {
        startPage();
        List<BizFormTemplate> list = templateService.selectTemplateList(template);
        return getDataTable(list);
    }

    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable Long templateId)
    {
        return success(templateService.selectTemplateById(templateId));
    }

    @PreAuthorize("@ss.hasPermi('form:template:add')")
    @Log(title = "表单模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizFormTemplate template)
    {
        if (!templateService.checkTemplateKeyUnique(template))
        {
            return error("模板标识'" + template.getTemplateKey() + "'已存在");
        }
        template.setVersion(1);
        template.setCreateBy(getUsername());
        return toAjax(templateService.insertTemplate(template));
    }

    @PreAuthorize("@ss.hasPermi('form:template:edit')")
    @Log(title = "表单模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizFormTemplate template)
    {
        if (!templateService.checkTemplateKeyUnique(template))
        {
            return error("模板标识'" + template.getTemplateKey() + "'已存在");
        }
        // 版本号递增
        BizFormTemplate old = templateService.selectTemplateById(template.getTemplateId());
        if (old != null)
        {
            template.setVersion((old.getVersion() != null ? old.getVersion() : 0) + 1);
        }
        template.setUpdateBy(getUsername());
        return toAjax(templateService.updateTemplate(template));
    }

    @PreAuthorize("@ss.hasPermi('form:template:remove')")
    @Log(title = "表单模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(templateService.deleteTemplateByIds(templateIds));
    }
}
