package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizSealPosition;

/**
 * 印章位置配置 数据层
 *
 * @author ruoyi
 */
public interface BizSealPositionMapper
{
    public BizSealPosition selectPositionById(Long positionId);

    public List<BizSealPosition> selectPositionList(BizSealPosition position);

    public int insertPosition(BizSealPosition position);

    public int updatePosition(BizSealPosition position);

    public int deletePositionById(Long positionId);

    public int deletePositionByIds(Long[] positionIds);
}
