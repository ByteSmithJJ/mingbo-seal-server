package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizSealAuthorization;

/**
 * 印章授权 数据层
 *
 * @author ruoyi
 */
public interface BizSealAuthorizationMapper
{
    public BizSealAuthorization selectAuthorizationById(Long authId);

    public List<BizSealAuthorization> selectAuthorizationList(BizSealAuthorization authorization);

    public int insertAuthorization(BizSealAuthorization authorization);

    public int updateAuthorization(BizSealAuthorization authorization);

    public int deleteAuthorizationById(Long authId);

    public int deleteAuthorizationByIds(Long[] authIds);
}
