-- =============================================
-- 补充"个人中心"菜单（隐藏菜单，通过用户头像下拉菜单访问）
-- 执行前请确认 menu_id = 1180 未被占用
-- =============================================

INSERT INTO sys_menu VALUES(
  '1180',                       -- menu_id（确保不与其他菜单ID冲突）
  '个人中心',                   -- menu_name
  '1',                          -- parent_id（挂在"系统管理"目录下）
  '10',                         -- order_num（排序号，放在系统管理子菜单末尾）
  'user-center',                -- path（路由地址）
  'system/user-center/index',   -- component（组件路径，相对于 views 目录）
  '',                           -- query（路由参数，空）
  'UserCenter',                 -- route_name（路由名称，对应前端 name 配置）
  1,                            -- is_frame（1=非外链，0=外链）
  0,                            -- is_cache（0=缓存，1=不缓存。对应前端 keepAlive: true）
  'C',                          -- menu_type（C=菜单，M=目录，F=按钮）
  '1',                          -- visible（1=隐藏，0=显示。对应前端 isHide: true）
  '0',                          -- status（0=正常，1=停用）
  '',                           -- perms（权限标识，空表示所有登录用户均可访问）
  'user',                       -- icon（菜单图标，映射为前端 ri:user-line）
  'admin',                      -- create_by
  sysdate(),                    -- create_time
  '',                           -- update_by
  NULL,                         -- update_time
  '个人中心页面'                -- remark
);
