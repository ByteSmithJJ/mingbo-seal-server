-- Flowable 数据库表初始化脚本 (MySQL)
-- 版本: 7.0.0

-- 1. 通用数据表 (ACT_GE_*)
CREATE TABLE ACT_GE_PROPERTY (
    NAME_ varchar(64) NOT NULL,
    VALUE_ varchar(300),
    REV_ integer,
    PRIMARY KEY (NAME_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO ACT_GE_PROPERTY VALUES ('schema.version', '7.0.0.0', 1);
INSERT INTO ACT_GE_PROPERTY VALUES ('schema.history', 'create(7.0.0.0)', 1);
INSERT INTO ACT_GE_PROPERTY VALUES ('next.dbid', '1', 1);

-- 2. 部署相关表 (ACT_RE_*)
CREATE TABLE ACT_RE_DEPLOYMENT (
    ID_ varchar(255) NOT NULL,
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    KEY_ varchar(255),
    TENANT_ID_ varchar(255) DEFAULT '',
    DEPLOY_TIME_ timestamp(3) NULL,
    DERIVED_FROM_ varchar(255),
    DERIVED_FROM_ROOT_ varchar(255),
    PARENT_DEPLOYMENT_ID_ varchar(255),
    ENGINE_VERSION_ varchar(255),
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RE_MODEL (
    ID_ varchar(255) NOT NULL,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp(3) NULL,
    LAST_UPDATE_TIME_ timestamp(3) NULL,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(255),
    TENANT_ID_ varchar(255) DEFAULT '',
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RE_PROCDEF (
    ID_ varchar(255) NOT NULL,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) NOT NULL,
    VERSION_ integer NOT NULL,
    DEPLOYMENT_ID_ varchar(255),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ tinyint,
    HAS_GRAPHICAL_NOTATION_ tinyint,
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) DEFAULT '',
    ENGINE_VERSION_ varchar(255),
    DERIVED_FROM_ varchar(255),
    DERIVED_FROM_ROOT_ varchar(255),
    DERIVED_VERSION_ integer NOT NULL DEFAULT 0,
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_RE_PROCDEF_DEPLOYMENT_ID ON ACT_RE_PROCDEF(DEPLOYMENT_ID_);

-- 3. 运行时表 (ACT_RU_*)
CREATE TABLE ACT_RU_EXECUTION (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ROOT_PROC_INST_ID_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ tinyint,
    IS_CONCURRENT_ tinyint,
    IS_SCOPE_ tinyint,
    IS_EVENT_SCOPE_ tinyint,
    IS_MI_ROOT_ tinyint,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    TENANT_ID_ varchar(255) DEFAULT '',
    NAME_ varchar(255),
    START_TIME_ timestamp(3) NULL,
    START_USER_ID_ varchar(255),
    LOCK_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    IS_COUNT_ENABLED_ tinyint,
    EVT_SUBSCR_COUNT_ integer,
    TASK_COUNT_ integer,
    JOB_COUNT_ integer,
    TIMER_JOB_COUNT_ integer,
    SUSP_JOB_COUNT_ integer,
    VARIABLE_COUNT_ integer,
    ID_LINK_COUNT_ integer,
    APP_VERSION_ integer,
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_RU_EXEC_PROCINST ON ACT_RU_EXECUTION(PROC_INST_ID_);
CREATE INDEX IDX_ACT_RU_EXEC_PROC_DEF ON ACT_RU_EXECUTION(PROC_DEF_ID_);

CREATE TABLE ACT_RU_TASK (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_ID_ varchar(64),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    PROPAGATED_STAGE_INST_ID_ varchar(255),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp(3) NULL,
    DUE_DATE_ datetime(3) NULL,
    CATEGORY_ varchar(255),
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) DEFAULT '',
    FORM_KEY_ varchar(255),
    CLAIM_TIME_ datetime(3) NULL,
    IS_COUNT_ENABLED_ tinyint,
    VAR_COUNT_ integer,
    ID_LINK_COUNT_ integer,
    SUB_TASK_COUNT_ integer,
    APP_VERSION_ integer,
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_RU_TASK_PROCINST ON ACT_RU_TASK(PROC_INST_ID_);
CREATE INDEX IDX_ACT_RU_TASK_EXEC ON ACT_RU_TASK(EXECUTION_ID_);
CREATE INDEX IDX_ACT_RU_TASK_PROCDEF ON ACT_RU_TASK(PROC_DEF_ID_);

CREATE TABLE ACT_RU_VARIABLE (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255) NOT NULL,
    NAME_ varchar(255) NOT NULL,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_RU_VAR_PROCINST ON ACT_RU_VARIABLE(PROC_INST_ID_);
CREATE INDEX IDX_ACT_RU_VAR_EXEC ON ACT_RU_VARIABLE(EXECUTION_ID_);
CREATE INDEX IDX_ACT_RU_VAR_TASK ON ACT_RU_VARIABLE(TASK_ID_);

CREATE TABLE ACT_RU_IDENTITYLINK (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_RU_IDENT_LNK_PROCINST ON ACT_RU_IDENTITYLINK(PROC_INST_ID_);
CREATE INDEX IDX_ACT_RU_IDENT_LNK_TASK ON ACT_RU_IDENTITYLINK(TASK_ID_);

CREATE TABLE ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    EVENT_TYPE_ varchar(255) NOT NULL,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PROC_DEF_ID_ varchar(64),
    SUBSCRIPTION_COUNT_ integer DEFAULT 0,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ tinyint DEFAULT 1,
    EXECUTION_ID_ varchar(255),
    PROCESS_INSTANCE_ID_ varchar(255),
    PROC_DEF_ID_ varchar(64),
    ELEMENT_ID_ varchar(255),
    ELEMENT_NAME_ varchar(255),
    SCOPE_ID_ varchar(255),
    SUB_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    CORRELATION_ID_ varchar(255),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    CUSTOM_VALUES_ID_ varchar(64),
    CREATE_TIME_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RU_TIMER_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ tinyint DEFAULT 1,
    EXECUTION_ID_ varchar(255),
    PROCESS_INSTANCE_ID_ varchar(255),
    PROC_DEF_ID_ varchar(64),
    ELEMENT_ID_ varchar(255),
    ELEMENT_NAME_ varchar(255),
    SCOPE_ID_ varchar(255),
    SUB_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    CORRELATION_ID_ varchar(255),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    CUSTOM_VALUES_ID_ varchar(64),
    CREATE_TIME_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RU_SUSPENDED_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255) NOT NULL,
    EXCLUSIVE_ tinyint DEFAULT 1,
    EXECUTION_ID_ varchar(255),
    PROCESS_INSTANCE_ID_ varchar(255),
    PROC_DEF_ID_ varchar(64),
    ELEMENT_ID_ varchar(255),
    ELEMENT_NAME_ varchar(255),
    SCOPE_ID_ varchar(255),
    SUB_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    CORRELATION_ID_ varchar(255),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    CUSTOM_VALUES_ID_ varchar(64),
    CREATE_TIME_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RU_DEADLETTER_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255) NOT NULL,
    EXCLUSIVE_ tinyint DEFAULT 1,
    EXECUTION_ID_ varchar(255),
    PROCESS_INSTANCE_ID_ varchar(255),
    PROC_DEF_ID_ varchar(64),
    ELEMENT_ID_ varchar(255),
    ELEMENT_NAME_ varchar(255),
    SCOPE_ID_ varchar(255),
    SUB_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    CORRELATION_ID_ varchar(255),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    CUSTOM_VALUES_ID_ varchar(64),
    CREATE_TIME_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_RU_HISTORY_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    LOCK_EXP_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    PROCESS_INSTANCE_ID_ varchar(255),
    ELEMENT_ID_ varchar(255),
    ELEMENT_NAME_ varchar(255),
    SCOPE_ID_ varchar(255),
    SUB_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    FAILED_AT_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 4. 历史表 (ACT_HI_*)
CREATE TABLE ACT_HI_PROCINST (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64),
    START_TIME_ timestamp(3) NOT NULL,
    END_TIME_ timestamp(3) NULL,
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    TENANT_ID_ varchar(255) DEFAULT '',
    NAME_ varchar(255),
    CALLBACK_ID_ varchar(255),
    CALLBACK_TYPE_ varchar(255),
    REFERENCE_ID_ varchar(255),
    REFERENCE_TYPE_ varchar(255),
    PROPAGATED_STAGE_INST_ID_ varchar(255),
    BUSINESS_STATUS_ varchar(255),
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_HI_PROCINST_PROC_INST ON ACT_HI_PROCINST(PROC_INST_ID_);

CREATE TABLE ACT_HI_ACTINST (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    ACT_ID_ varchar(255),
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp(3) NOT NULL,
    END_TIME_ timestamp(3) NULL,
    DURATION_ bigint,
    TRANSACTION_ORDER_ integer,
    TENANT_ID_ varchar(255) DEFAULT '',
    REMOVE_TIME_ timestamp(3) NULL,
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_HI_ACT_INST_PROC_INST ON ACT_HI_ACTINST(PROC_INST_ID_);

CREATE TABLE ACT_HI_TASKINST (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_ID_ varchar(64),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    PROPAGATED_STAGE_INST_ID_ varchar(255),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp(3) NOT NULL,
    CLAIM_TIME_ timestamp(3) NULL,
    END_TIME_ timestamp(3) NULL,
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ datetime(3) NULL,
    FORM_KEY_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) DEFAULT '',
    LAST_UPDATED_TIME_ timestamp(3) NULL,
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_HI_TASK_INST_PROC_INST ON ACT_HI_TASKINST(PROC_INST_ID_);

CREATE TABLE ACT_HI_VARINST (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255),
    VAR_TYPE_ varchar(100),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    CREATE_TIME_ timestamp(3) NULL,
    LAST_UPDATED_TIME_ timestamp(3) NULL,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_HI_VAR_INST_PROC_INST ON ACT_HI_VARINST(PROC_INST_ID_);

CREATE TABLE ACT_HI_COMMENT (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    TYPE_ varchar(255),
    TIME_ timestamp(3) NOT NULL,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ longblob,
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_HI_ATTACHMENT (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    TENANT_ID_ varchar(255) DEFAULT '',
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE ACT_HI_IDENTITYLINK (
    ID_ varchar(64) NOT NULL,
    REV_ integer DEFAULT 1,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    SCOPE_ID_ varchar(255),
    SUB_TASK_SCOPE_ID_ varchar(255),
    SCOPE_TYPE_ varchar(255),
    SCOPE_DEFINITION_ID_ varchar(255),
    PRIMARY KEY (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX IDX_ACT_HI_IDENT_LNK_PROCINST ON ACT_HI_IDENTITYLINK(PROC_INST_ID_);

-- 5. 实体链接表
CREATE TABLE ACT_EVT_LOG (
    LOG_NR_ bigint AUTO_INCREMENT,
    TYPE_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    TIME_STAMP_ timestamp(3) NOT NULL,
    USER_ID_ varchar(255),
    DATA_ longblob,
    LOCK_OWNER_ varchar(255),
    LOCK_TIME_ timestamp(3) NULL,
    IS_PROCESSED_ tinyint DEFAULT 0,
    PRIMARY KEY (LOG_NR_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 创建索引
CREATE INDEX ACT_IDX_TASK_CREATE ON ACT_RU_TASK(CREATE_TIME_);
CREATE INDEX ACT_IDX_JOB_SCOPE_ID ON ACT_RU_JOB(SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_JOB_SUB_SCOPE_ID ON ACT_RU_JOB(SUB_SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_JOB_SCOPE_DEF_ID ON ACT_RU_JOB(SCOPE_DEFINITION_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_TIMER_JOB_SCOPE_ID ON ACT_RU_TIMER_JOB(SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_TIMER_JOB_SUB_SCOPE_ID ON ACT_RU_TIMER_JOB(SUB_SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_TIMER_JOB_SCOPE_DEF_ID ON ACT_RU_TIMER_JOB(SCOPE_DEFINITION_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_SUSP_JOB_SCOPE_ID ON ACT_RU_SUSPENDED_JOB(SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_SUSP_JOB_SUB_SCOPE_ID ON ACT_RU_SUSPENDED_JOB(SUB_SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_SUSP_JOB_SCOPE_DEF_ID ON ACT_RU_SUSPENDED_JOB(SCOPE_DEFINITION_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_DJOB_SCOPE_ID ON ACT_RU_DEADLETTER_JOB(SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_DJOB_SUB_SCOPE_ID ON ACT_RU_DEADLETTER_JOB(SUB_SCOPE_ID_, SCOPE_TYPE_);
CREATE INDEX ACT_IDX_DJOB_SCOPE_DEF_ID ON ACT_RU_DEADLETTER_JOB(SCOPE_DEFINITION_ID_, SCOPE_TYPE_);
