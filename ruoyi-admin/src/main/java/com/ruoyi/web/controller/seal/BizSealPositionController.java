package com.ruoyi.web.controller.seal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.system.domain.BizSealPosition;
import com.ruoyi.system.service.IBizSealPositionService;

/**
 * 印章位置配置 操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/seal/position")
public class BizSealPositionController extends BaseController
{
    @Autowired
    private IBizSealPositionService positionService;

    /**
     * 获取位置配置列表
     */
    @PreAuthorize("@ss.hasPermi('seal:position:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSealPosition position)
    {
        startPage();
        List<BizSealPosition> list = positionService.selectPositionList(position);
        return getDataTable(list);
    }

    /**
     * 获取位置配置详细信息
     */
    @GetMapping(value = "/{positionId}")
    public AjaxResult getInfo(@PathVariable Long positionId)
    {
        return success(positionService.selectPositionById(positionId));
    }

    /**
     * 新增位置配置
     */
    @PreAuthorize("@ss.hasPermi('seal:position:add')")
    @Log(title = "印章位置配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSealPosition position)
    {
        position.setCreateBy(getUsername());
        return toAjax(positionService.insertPosition(position));
    }

    /**
     * 修改位置配置
     */
    @PreAuthorize("@ss.hasPermi('seal:position:edit')")
    @Log(title = "印章位置配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSealPosition position)
    {
        position.setUpdateBy(getUsername());
        return toAjax(positionService.updatePosition(position));
    }

    /**
     * 删除位置配置
     */
    @PreAuthorize("@ss.hasPermi('seal:position:remove')")
    @Log(title = "印章位置配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{positionIds}")
    public AjaxResult remove(@PathVariable Long[] positionIds)
    {
        return toAjax(positionService.deletePositionByIds(positionIds));
    }
}
