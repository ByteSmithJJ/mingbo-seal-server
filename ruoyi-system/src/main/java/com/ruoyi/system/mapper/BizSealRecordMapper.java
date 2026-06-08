package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizSealRecord;

public interface BizSealRecordMapper
{
    public BizSealRecord selectRecordById(Long sealRecordId);
    public List<BizSealRecord> selectRecordList(BizSealRecord record);
    public List<BizSealRecord> selectSealRecordsByInstanceId(Long instanceId);
    public int insertRecord(BizSealRecord record);
    public int deleteRecordById(Long sealRecordId);
}
