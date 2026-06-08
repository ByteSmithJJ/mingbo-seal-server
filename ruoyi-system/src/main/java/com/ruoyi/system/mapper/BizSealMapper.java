package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizSeal;

/**
 * 印章信息 数据层
 *
 * @author ruoyi
 */
public interface BizSealMapper
{
    public BizSeal selectSealById(Long sealId);

    public List<BizSeal> selectSealList(BizSeal seal);

    public int insertSeal(BizSeal seal);

    public int updateSeal(BizSeal seal);

    public int deleteSealById(Long sealId);

    public int deleteSealByIds(Long[] sealIds);

    public BizSeal checkSealCodeUnique(String sealCode);
}
