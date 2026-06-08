package com.ruoyi.framework.config;

import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable 工作流引擎配置
 * 
 * @author ruoyi
 */
@Configuration
public class FlowableConfig {

    /**
     * 配置 Flowable 引擎，确保自动创建数据库表结构
     */
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> engineConfigurationConfigurer() {
        return configuration -> {
            // 设置数据库 schema 更新策略为 true，自动创建和更新表结构
            configuration.setDatabaseSchemaUpdate("true");
            // 启用历史记录功能
            configuration.setDbHistoryUsed(true);
            // 设置历史记录级别为 audit（审计级别，记录详细的历史数据）
            configuration.setHistoryLevel(HistoryLevel.AUDIT);
        };
    }
}
