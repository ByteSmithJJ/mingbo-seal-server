-- =============================================
-- 印章管理、表单管理、流程管理 菜单数据恢复
-- =============================================

-- 印章管理（目录）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
(200, '印章管理', 0, 5, 'seal', NULL, 1, 0, 'M', '0', '0', '', 'shield', 'admin', NOW(), '印章管理目录'),
(201, '印章信息', 200, 1, 'info', 'seal/info/index', 1, 1, 'C', '0', '0', 'seal:info:list', 'seal', 'admin', NOW(), '印章信息菜单'),
(202, '印章授权', 200, 2, 'authorization', 'seal/authorization/index', 1, 1, 'C', '0', '0', 'seal:authorization:list', 'user', 'admin', NOW(), '印章授权菜单'),
(203, '印章位置配置', 200, 3, 'position', 'seal/position/index', 1, 1, 'C', '0', '0', 'seal:position:list', 'location', 'admin', NOW(), '印章位置配置菜单'),

-- 印章信息按钮
(220, '印章查询', 201, 1, '', '', 1, 0, 'F', '0', '0', 'seal:info:query', '#', 'admin', NOW(), ''),
(221, '印章新增', 201, 2, '', '', 1, 0, 'F', '0', '0', 'seal:info:add', '#', 'admin', NOW(), ''),
(222, '印章修改', 201, 3, '', '', 1, 0, 'F', '0', '0', 'seal:info:edit', '#', 'admin', NOW(), ''),
(223, '印章删除', 201, 4, '', '', 1, 0, 'F', '0', '0', 'seal:info:remove', '#', 'admin', NOW(), ''),

-- 印章授权按钮
(224, '授权查询', 202, 1, '', '', 1, 0, 'F', '0', '0', 'seal:authorization:query', '#', 'admin', NOW(), ''),
(225, '授权新增', 202, 2, '', '', 1, 0, 'F', '0', '0', 'seal:authorization:add', '#', 'admin', NOW(), ''),
(226, '授权修改', 202, 3, '', '', 1, 0, 'F', '0', '0', 'seal:authorization:edit', '#', 'admin', NOW(), ''),
(227, '授权删除', 202, 4, '', '', 1, 0, 'F', '0', '0', 'seal:authorization:remove', '#', 'admin', NOW(), ''),

-- 印章位置配置按钮
(228, '位置查询', 203, 1, '', '', 1, 0, 'F', '0', '0', 'seal:position:query', '#', 'admin', NOW(), ''),
(229, '位置新增', 203, 2, '', '', 1, 0, 'F', '0', '0', 'seal:position:add', '#', 'admin', NOW(), ''),
(230, '位置修改', 203, 3, '', '', 1, 0, 'F', '0', '0', 'seal:position:edit', '#', 'admin', NOW(), ''),
(231, '位置删除', 203, 4, '', '', 1, 0, 'F', '0', '0', 'seal:position:remove', '#', 'admin', NOW(), ''),

-- 表单管理（目录）
(240, '表单管理', 0, 6, 'form', NULL, 1, 0, 'M', '0', '0', '', 'form', 'admin', NOW(), '表单管理目录'),
(241, '表单模板', 240, 1, 'template', 'form/template/index', 1, 1, 'C', '0', '0', 'form:template:list', 'documentation', 'admin', NOW(), '表单模板菜单'),

-- 表单模板按钮
(242, '模板查询', 241, 1, '', '', 1, 0, 'F', '0', '0', 'form:template:query', '#', 'admin', NOW(), ''),
(243, '模板新增', 241, 2, '', '', 1, 0, 'F', '0', '0', 'form:template:add', '#', 'admin', NOW(), ''),
(244, '模板修改', 241, 3, '', '', 1, 0, 'F', '0', '0', 'form:template:edit', '#', 'admin', NOW(), ''),
(245, '模板删除', 241, 4, '', '', 1, 0, 'F', '0', '0', 'form:template:remove', '#', 'admin', NOW(), ''),

-- 流程管理（目录）
(250, '流程管理', 0, 7, 'process', NULL, 1, 0, 'M', '0', '0', '', 'flow-chart', 'admin', NOW(), '流程管理目录'),
(251, '流程定义', 250, 1, 'definition', 'process/definition/index', 1, 1, 'C', '0', '0', 'process:definition:list', 'file-list-3', 'admin', NOW(), '流程定义菜单'),
(252, '流程设计', 250, 2, 'designer/:id', 'process/designer/index', 1, 0, 'C', '1', '0', 'process:definition:design', 'edit', 'admin', NOW(), '流程设计页面（隐藏）'),
(253, '发起申请', 250, 3, 'start', 'process/start/index', 1, 0, 'C', '0', '0', 'process:instance:start', 'file-add', 'admin', NOW(), '发起申请菜单'),
(254, '我的申请', 250, 4, 'my-applications', 'process/my-applications/index', 1, 1, 'C', '0', '0', 'process:instance:list', 'draft', 'admin', NOW(), '我的申请菜单'),
(255, '申请详情', 250, 5, 'my-applications/:id', 'process/application-detail/index', 1, 0, 'C', '1', '0', 'process:instance:list', '#', 'admin', NOW(), '申请详情页面（隐藏）'),
(256, '待审批', 250, 6, 'pending', 'process/pending/index', 1, 1, 'C', '0', '0', 'process:task:list', 'task', 'admin', NOW(), '待审批菜单'),
(257, '已审批', 250, 7, 'approved', 'process/approved/index', 1, 1, 'C', '0', '0', 'process:task:list', 'check-double', 'admin', NOW(), '已审批菜单'),

-- 流程定义按钮
(260, '流程查询', 251, 1, '', '', 1, 0, 'F', '0', '0', 'process:definition:query', '#', 'admin', NOW(), ''),
(261, '流程新增', 251, 2, '', '', 1, 0, 'F', '0', '0', 'process:definition:add', '#', 'admin', NOW(), ''),
(262, '流程修改', 251, 3, '', '', 1, 0, 'F', '0', '0', 'process:definition:edit', '#', 'admin', NOW(), ''),
(263, '流程删除', 251, 4, '', '', 1, 0, 'F', '0', '0', 'process:definition:remove', '#', 'admin', NOW(), ''),
(264, '流程部署', 251, 5, '', '', 1, 0, 'F', '0', '0', 'process:definition:deploy', '#', 'admin', NOW(), ''),

-- 审批操作按钮
(270, '审批通过', 256, 1, '', '', 1, 0, 'F', '0', '0', 'process:task:approve', '#', 'admin', NOW(), ''),
(271, '撤回申请', 254, 1, '', '', 1, 0, 'F', '0', '0', 'process:instance:withdraw', '#', 'admin', NOW(), '');
