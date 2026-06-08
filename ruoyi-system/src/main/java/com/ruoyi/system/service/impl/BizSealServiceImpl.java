package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizSeal;
import com.ruoyi.system.mapper.BizSealMapper;
import com.ruoyi.system.service.IBizSealService;

/**
 * 印章信息 服务层实现
 *
 * @author ruoyi
 */
@Service
public class BizSealServiceImpl implements IBizSealService
{
    @Autowired
    private BizSealMapper sealMapper;

    @Override
    public BizSeal selectSealById(Long sealId)
    {
        return sealMapper.selectSealById(sealId);
    }

    @Override
    public List<BizSeal> selectSealList(BizSeal seal)
    {
        return sealMapper.selectSealList(seal);
    }

    @Override
    public int insertSeal(BizSeal seal)
    {
        return sealMapper.insertSeal(seal);
    }

    @Override
    public int updateSeal(BizSeal seal)
    {
        return sealMapper.updateSeal(seal);
    }

    @Override
    public int deleteSealByIds(Long[] sealIds)
    {
        return sealMapper.deleteSealByIds(sealIds);
    }

    @Override
    public boolean checkSealCodeUnique(BizSeal seal)
    {
        Long sealId = seal.getSealId() == null ? -1L : seal.getSealId();
        BizSeal info = sealMapper.checkSealCodeUnique(seal.getSealCode());
        if (info != null && info.getSealId().longValue() != sealId.longValue())
        {
            return false;
        }
        return true;
    }
}
