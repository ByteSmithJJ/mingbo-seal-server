package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BizApprovalRecord
{
    private static final long serialVersionUID = 1L;

    private Long recordId;
    private Long instanceId;
    private String taskId;
    private String nodeCode;
    private String nodeName;
    private String approver;
    private Long approverDeptId;
    private String result;
    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    private Long costDuration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getInstanceId() { return instanceId; }
    public void setInstanceId(Long instanceId) { this.instanceId = instanceId; }
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    public String getNodeCode() { return nodeCode; }
    public void setNodeCode(String nodeCode) { this.nodeCode = nodeCode; }
    public String getNodeName() { return nodeName; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }
    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public Long getApproverDeptId() { return approverDeptId; }
    public void setApproverDeptId(Long approverDeptId) { this.approverDeptId = approverDeptId; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Date getApproveTime() { return approveTime; }
    public void setApproveTime(Date approveTime) { this.approveTime = approveTime; }
    public Long getCostDuration() { return costDuration; }
    public void setCostDuration(Long costDuration) { this.costDuration = costDuration; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("instanceId", getInstanceId())
            .append("taskId", getTaskId())
            .append("result", getResult())
            .toString();
    }
}
