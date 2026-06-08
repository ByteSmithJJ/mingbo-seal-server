package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizSealAuthorization;
import com.ruoyi.system.mapper.BizSealAuthorizationMapper;
import com.ruoyi.system.service.IBizSealAuthorizationService;

/**
 * 印章授权 服务层实现
 *
 * @author ruoyi
 */
@Service
public class BizSealAuthorizationServiceImpl implements IBizSealAuthorizationService
{
    @Autowired
    private BizSealAuthorizationMapper authorizationMapper;

    @Override
    public BizSealAuthorization selectAuthorizationById(Long authId)
    {
        return authorizationMapper.selectAuthorizationById(authId);
    }

    @Override
    public List<BizSealAuthorization> selectAuthorizationList(BizSealAuthorization authorization)
    {
        return authorizationMapper.selectAuthorizationList(authorization);
    }

    @Override
    public int insertAuthorization(BizSealAuthorization authorization)
    {
        return authorizationMapper.insertAuthorization(authorization);
    }

    @Override
    public int updateAuthorization(BizSealAuthorization authorization)
    {
        return authorizationMapper.updateAuthorization(authorization);
    }

    @Override
    public int deleteAuthorizationByIds(Long[] authIds)
    {
        return authorizationMapper.deleteAuthorizationByIds(authIds);
    }
}
