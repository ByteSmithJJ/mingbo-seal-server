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
import com.ruoyi.system.domain.BizSealAuthorization;
import com.ruoyi.system.service.IBizSealAuthorizationService;

/**
 * 印章授权 操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/seal/authorization")
public class BizSealAuthorizationController extends BaseController
{
    @Autowired
    private IBizSealAuthorizationService authorizationService;

    /**
     * 获取授权列表
     */
    @PreAuthorize("@ss.hasPermi('seal:authorization:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSealAuthorization authorization)
    {
        startPage();
        List<BizSealAuthorization> list = authorizationService.selectAuthorizationList(authorization);
        return getDataTable(list);
    }

    /**
     * 获取授权详细信息
     */
    @GetMapping(value = "/{authId}")
    public AjaxResult getInfo(@PathVariable Long authId)
    {
        return success(authorizationService.selectAuthorizationById(authId));
    }

    /**
     * 新增授权
     */
    @PreAuthorize("@ss.hasPermi('seal:authorization:add')")
    @Log(title = "印章授权", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSealAuthorization authorization)
    {
        authorization.setCreateBy(getUsername());
        return toAjax(authorizationService.insertAuthorization(authorization));
    }

    /**
     * 修改授权
     */
    @PreAuthorize("@ss.hasPermi('seal:authorization:edit')")
    @Log(title = "印章授权", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSealAuthorization authorization)
    {
        authorization.setUpdateBy(getUsername());
        return toAjax(authorizationService.updateAuthorization(authorization));
    }

    /**
     * 删除授权
     */
    @PreAuthorize("@ss.hasPermi('seal:authorization:remove')")
    @Log(title = "印章授权", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authIds}")
    public AjaxResult remove(@PathVariable Long[] authIds)
    {
        return toAjax(authorizationService.deleteAuthorizationByIds(authIds));
    }
}
