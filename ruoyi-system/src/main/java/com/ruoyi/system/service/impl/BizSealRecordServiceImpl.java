package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.BizSealRecord;
import com.ruoyi.system.mapper.BizSealRecordMapper;
import com.ruoyi.system.service.IBizSealRecordService;

@Service
public class BizSealRecordServiceImpl implements IBizSealRecordService
{
    @Autowired
    private BizSealRecordMapper sealRecordMapper;

    @Override
    public BizSealRecord selectRecordById(Long id) { return sealRecordMapper.selectRecordById(id); }

    @Override
    public List<BizSealRecord> selectRecordList(BizSealRecord r) { return sealRecordMapper.selectRecordList(r); }

    @Override
    public List<BizSealRecord> selectSealRecordsByInstanceId(Long id) { return sealRecordMapper.selectSealRecordsByInstanceId(id); }

    @Override
    public int insertRecord(BizSealRecord r) { return sealRecordMapper.insertRecord(r); }

    @Override
    public int deleteRecordById(Long id) { return sealRecordMapper.deleteRecordById(id); }

    @Override
    public int batchInsertSealRecords(List<BizSealRecord> records)
    {
        int count = 0;
        for (BizSealRecord record : records)
        {
            count += sealRecordMapper.insertRecord(record);
        }
        return count;
    }
}
