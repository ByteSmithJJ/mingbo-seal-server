package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizApprovalRecord;

public interface BizApprovalRecordMapper
{
    public BizApprovalRecord selectRecordById(Long recordId);
    public List<BizApprovalRecord> selectRecordList(BizApprovalRecord record);
    public int insertRecord(BizApprovalRecord record);
    public int updateRecord(BizApprovalRecord record);
    public int deleteRecordById(Long recordId);
}
