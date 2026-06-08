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
import com.ruoyi.system.domain.BizSeal;
import com.ruoyi.system.service.IBizSealService;

/**
 * 印章信息 操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/seal/info")
public class BizSealController extends BaseController
{
    @Autowired
    private IBizSealService sealService;

    /**
     * 获取印章列表
     */
    @PreAuthorize("@ss.hasPermi('seal:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSeal seal)
    {
        startPage();
        List<BizSeal> list = sealService.selectSealList(seal);
        return getDataTable(list);
    }

    /**
     * 获取印章详细信息
     */
    @GetMapping(value = "/{sealId}")
    public AjaxResult getInfo(@PathVariable Long sealId)
    {
        return success(sealService.selectSealById(sealId));
    }

    /**
     * 新增印章
     */
    @PreAuthorize("@ss.hasPermi('seal:info:add')")
    @Log(title = "印章信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizSeal seal)
    {
        if (!sealService.checkSealCodeUnique(seal))
        {
            return error("印章编号'" + seal.getSealCode() + "'已存在");
        }
        seal.setCreateBy(getUsername());
        return toAjax(sealService.insertSeal(seal));
    }

    /**
     * 修改印章
     */
    @PreAuthorize("@ss.hasPermi('seal:info:edit')")
    @Log(title = "印章信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizSeal seal)
    {
        if (!sealService.checkSealCodeUnique(seal))
        {
            return error("印章编号'" + seal.getSealCode() + "'已存在");
        }
        seal.setUpdateBy(getUsername());
        return toAjax(sealService.updateSeal(seal));
    }

    /**
     * 删除印章
     */
    @PreAuthorize("@ss.hasPermi('seal:info:remove')")
    @Log(title = "印章信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sealIds}")
    public AjaxResult remove(@PathVariable Long[] sealIds)
    {
        return toAjax(sealService.deleteSealByIds(sealIds));
    }
}
