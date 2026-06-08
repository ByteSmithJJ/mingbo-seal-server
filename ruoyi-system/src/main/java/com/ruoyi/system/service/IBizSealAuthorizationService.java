package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizSealAuthorization;

/**
 * 印章授权 服务层
 *
 * @author ruoyi
 */
public interface IBizSealAuthorizationService
{
    public BizSealAuthorization selectAuthorizationById(Long authId);

    public List<BizSealAuthorization> selectAuthorizationList(BizSealAuthorization authorization);

    public int insertAuthorization(BizSealAuthorization authorization);

    public int updateAuthorization(BizSealAuthorization authorization);

    public int deleteAuthorizationByIds(Long[] authIds);
}
