package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizSealPosition;
import com.ruoyi.system.mapper.BizSealPositionMapper;
import com.ruoyi.system.service.IBizSealPositionService;

/**
 * 印章位置配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class BizSealPositionServiceImpl implements IBizSealPositionService
{
    @Autowired
    private BizSealPositionMapper positionMapper;

    @Override
    public BizSealPosition selectPositionById(Long positionId)
    {
        return positionMapper.selectPositionById(positionId);
    }

    @Override
    public List<BizSealPosition> selectPositionList(BizSealPosition position)
    {
        return positionMapper.selectPositionList(position);
    }

    @Override
    public int insertPosition(BizSealPosition position)
    {
        return positionMapper.insertPosition(position);
    }

    @Override
    public int updatePosition(BizSealPosition position)
    {
        return positionMapper.updatePosition(position);
    }

    @Override
    public int deletePositionByIds(Long[] positionIds)
    {
        return positionMapper.deletePositionByIds(positionIds);
    }
}
