package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.BizProcessInstance;

public interface BizProcessInstanceMapper
{
    public BizProcessInstance selectInstanceById(Long instanceId);
    public BizProcessInstance selectInstanceByProcInstId(String procInstId);
    public List<BizProcessInstance> selectInstanceList(BizProcessInstance instance);
    public int insertInstance(BizProcessInstance instance);
    public int updateInstance(BizProcessInstance instance);
    public int deleteInstanceById(Long instanceId);
    public List<Map<String, Object>> selectInstanceCountByDay(@Param("days") int days);
}
