package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizApprovalRecord;
import com.ruoyi.system.mapper.BizApprovalRecordMapper;
import com.ruoyi.system.service.IBizApprovalRecordService;

@Service
public class BizApprovalRecordServiceImpl implements IBizApprovalRecordService
{
    @Autowired
    private BizApprovalRecordMapper approvalRecordMapper;

    @Override
    public BizApprovalRecord selectRecordById(Long recordId) { return approvalRecordMapper.selectRecordById(recordId); }

    @Override
    public List<BizApprovalRecord> selectRecordList(BizApprovalRecord record) { return approvalRecordMapper.selectRecordList(record); }

    @Override
    public int insertRecord(BizApprovalRecord record) { return approvalRecordMapper.insertRecord(record); }

    @Override
    public int updateRecord(BizApprovalRecord record) { return approvalRecordMapper.updateRecord(record); }

    @Override
    public int deleteRecordById(Long recordId) { return approvalRecordMapper.deleteRecordById(recordId); }
}
