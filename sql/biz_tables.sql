-- 印章信息表
CREATE TABLE biz_seal (
  seal_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '印章ID',
  seal_name varchar(64) NOT NULL COMMENT '印章名称',
  seal_code varchar(64) NOT NULL COMMENT '印章编号',
  seal_type varchar(20) DEFAULT '' COMMENT '印章类型',
  seal_image varchar(500) DEFAULT '' COMMENT '印章图片路径',
  status char(1) DEFAULT '0' COMMENT '状态 0启用 1停用',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (seal_id),
  UNIQUE KEY uk_seal_code (seal_code)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='印章信息表';

-- 印章授权表
CREATE TABLE biz_seal_authorization (
  auth_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '授权ID',
  seal_id bigint(20) NOT NULL COMMENT '印章ID',
  target_type varchar(20) NOT NULL COMMENT '授权对象类型',
  target_id bigint(20) NOT NULL COMMENT '授权对象ID',
  auth_type varchar(20) NOT NULL COMMENT '授权类型',
  begin_time datetime DEFAULT NULL COMMENT '授权开始时间',
  end_time datetime DEFAULT NULL COMMENT '授权结束时间',
  status char(1) DEFAULT '0' COMMENT '状态 0启用 1停用',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (auth_id),
  KEY idx_seal_id (seal_id),
  KEY idx_target (target_type, target_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='印章授权表';

-- 印章位置配置表
CREATE TABLE biz_seal_position (
  position_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '位置ID',
  seal_id bigint(20) NOT NULL COMMENT '印章ID',
  form_template_id bigint(20) NOT NULL COMMENT '表单模板ID',
  position_name varchar(64) DEFAULT '' COMMENT '位置名称',
  pos_x decimal(10,2) DEFAULT 0 COMMENT 'X坐标',
  pos_y decimal(10,2) DEFAULT 0 COMMENT 'Y坐标',
  seal_width int(4) DEFAULT 120 COMMENT '印章显示宽度',
  seal_height int(4) DEFAULT 120 COMMENT '印章显示高度',
  page_no int(4) DEFAULT 1 COMMENT '所在页码',
  sort int(4) DEFAULT 0 COMMENT '排序号',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (position_id),
  KEY idx_seal_id (seal_id),
  KEY idx_form_template_id (form_template_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='印章位置配置表';

-- 表单模板表
CREATE TABLE biz_form_template (
  template_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  template_name varchar(100) NOT NULL COMMENT '模板名称',
  template_key varchar(64) NOT NULL COMMENT '模板唯一标识',
  form_config longtext COMMENT '表单设计器JSON结构',
  version int(4) DEFAULT 1 COMMENT '版本号',
  status char(1) DEFAULT '0' COMMENT '状态 0草稿 1已发布 2已停用',
  del_flag char(1) DEFAULT '0' COMMENT '删除标志',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (template_id),
  UNIQUE KEY uk_template_key (template_key, version)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='表单模板表';

-- 流程定义表
CREATE TABLE biz_process_definition (
  definition_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定义ID',
  definition_name varchar(100) NOT NULL COMMENT '流程名称',
  definition_key varchar(64) NOT NULL COMMENT '流程唯一标识',
  bpmn_xml longtext COMMENT 'BPMN 2.0 XML',
  form_template_id bigint(20) DEFAULT NULL COMMENT '表单模板ID',
  deployment_id varchar(64) DEFAULT '' COMMENT 'Flowable部署ID',
  version int(4) DEFAULT 1 COMMENT '版本号',
  status char(1) DEFAULT '0' COMMENT '状态 0草稿 1已部署 2已停用',
  del_flag char(1) DEFAULT '0' COMMENT '删除标志',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (definition_id),
  UNIQUE KEY uk_definition_key (definition_key, version)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='流程定义表';

-- 流程节点配置表
CREATE TABLE biz_process_node (
  node_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '节点ID',
  definition_id bigint(20) NOT NULL COMMENT '所属流程定义ID',
  node_code varchar(64) NOT NULL COMMENT 'BPMN节点ID',
  node_name varchar(100) DEFAULT '' COMMENT '节点显示名称',
  node_type varchar(20) DEFAULT '' COMMENT '节点类型',
  assignee_type varchar(20) DEFAULT '' COMMENT '审批人类型',
  assignee_value varchar(500) DEFAULT '' COMMENT '审批人值',
  form_visible char(1) DEFAULT 'Y' COMMENT '表单是否可见',
  form_editable char(1) DEFAULT 'N' COMMENT '表单是否可编辑',
  allow_seal char(1) DEFAULT 'N' COMMENT '审批通过后是否允许签章',
  sort int(4) DEFAULT 0 COMMENT '节点排序号',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (node_id),
  KEY idx_definition_id (definition_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='流程节点配置表';

-- 流程实例表
CREATE TABLE biz_process_instance (
  instance_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实例ID',
  definition_id bigint(20) NOT NULL COMMENT '所属流程定义ID',
  definition_name varchar(100) DEFAULT '' COMMENT '流程名称',
  form_template_id bigint(20) DEFAULT NULL COMMENT '表单模板ID',
  proc_inst_id varchar(64) DEFAULT '' COMMENT 'Flowable流程实例ID',
  business_no varchar(64) NOT NULL COMMENT '业务编号',
  title varchar(200) DEFAULT '' COMMENT '流程标题',
  status char(1) DEFAULT '0' COMMENT '流程状态',
  current_node_code varchar(64) DEFAULT '' COMMENT '当前所在节点code',
  current_node_name varchar(100) DEFAULT '' COMMENT '当前所在节点名称',
  applicant varchar(64) DEFAULT '' COMMENT '发起人',
  applicant_dept_id bigint(20) DEFAULT NULL COMMENT '发起人部门ID',
  apply_time datetime DEFAULT NULL COMMENT '申请时间',
  complete_time datetime DEFAULT NULL COMMENT '完成时间',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (instance_id),
  KEY idx_definition_id (definition_id),
  KEY idx_applicant (applicant),
  KEY idx_status (status),
  KEY idx_proc_inst_id (proc_inst_id),
  KEY idx_business_no (business_no)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='流程实例表';

-- 审批记录表
CREATE TABLE biz_approval_record (
  record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  instance_id bigint(20) NOT NULL COMMENT '流程实例ID',
  task_id varchar(64) DEFAULT '' COMMENT 'Flowable任务ID',
  node_code varchar(64) DEFAULT '' COMMENT '审批节点code',
  node_name varchar(100) DEFAULT '' COMMENT '审批节点名称',
  approver varchar(64) DEFAULT '' COMMENT '审批人',
  approver_dept_id bigint(20) DEFAULT NULL COMMENT '审批人部门ID',
  result char(1) DEFAULT '0' COMMENT '审批结果',
  comment varchar(1000) DEFAULT '' COMMENT '审批意见',
  approve_time datetime DEFAULT NULL COMMENT '审批时间',
  cost_duration bigint(20) DEFAULT 0 COMMENT '耗时',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (record_id),
  KEY idx_instance_id (instance_id),
  KEY idx_approver (approver),
  KEY idx_task_id (task_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='审批记录表';

-- 签章记录表
CREATE TABLE biz_seal_record (
  seal_record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签章记录ID',
  instance_id bigint(20) NOT NULL COMMENT '流程实例ID',
  approval_record_id bigint(20) NOT NULL COMMENT '关联审批记录ID',
  seal_id bigint(20) NOT NULL COMMENT '印章ID',
  position_id bigint(20) NOT NULL COMMENT '印章位置配置ID',
  sealer varchar(64) DEFAULT '' COMMENT '盖章人',
  seal_time datetime DEFAULT NULL COMMENT '盖章时间',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (seal_record_id),
  KEY idx_instance_id (instance_id),
  KEY idx_approval_record_id (approval_record_id),
  KEY idx_seal_id (seal_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='签章记录表';

-- 表单数据表
CREATE TABLE biz_form_data (
  data_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  instance_id bigint(20) NOT NULL COMMENT '流程实例ID',
  template_id bigint(20) DEFAULT NULL COMMENT '表单模板ID',
  form_data longtext COMMENT '表单填写数据JSON',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (data_id),
  KEY idx_instance_id (instance_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='表单数据表';
