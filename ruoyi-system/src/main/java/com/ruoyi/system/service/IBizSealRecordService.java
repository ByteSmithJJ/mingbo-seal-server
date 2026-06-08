package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizSealRecord;

public interface IBizSealRecordService
{
    public BizSealRecord selectRecordById(Long sealRecordId);
    public List<BizSealRecord> selectRecordList(BizSealRecord record);
    public List<BizSealRecord> selectSealRecordsByInstanceId(Long instanceId);
    public int insertRecord(BizSealRecord record);
    public int deleteRecordById(Long sealRecordId);
    public int batchInsertSealRecords(List<BizSealRecord> records);
}
