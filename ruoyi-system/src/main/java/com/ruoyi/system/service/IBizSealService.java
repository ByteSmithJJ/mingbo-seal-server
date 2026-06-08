package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizSeal;

/**
 * 印章信息 服务层
 *
 * @author ruoyi
 */
public interface IBizSealService
{
    public BizSeal selectSealById(Long sealId);

    public List<BizSeal> selectSealList(BizSeal seal);

    public int insertSeal(BizSeal seal);

    public int updateSeal(BizSeal seal);

    public int deleteSealByIds(Long[] sealIds);

    public boolean checkSealCodeUnique(BizSeal seal);
}
