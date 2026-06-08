package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizSealPosition;

/**
 * 印章位置配置 服务层
 *
 * @author ruoyi
 */
public interface IBizSealPositionService
{
    public BizSealPosition selectPositionById(Long positionId);

    public List<BizSealPosition> selectPositionList(BizSealPosition position);

    public int insertPosition(BizSealPosition position);

    public int updatePosition(BizSealPosition position);

    public int deletePositionByIds(Long[] positionIds);
}
