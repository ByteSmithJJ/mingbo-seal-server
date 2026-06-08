# 贡献指南

感谢您考虑为 MingBo Seal Server 做出贡献！

## 行为准则

本项目采用 [Contributor Covenant 行为准则](./CODE_OF_CONDUCT.md)。

## 如何贡献

### 报告 Bug

1. 在 [Issues](https://github.com/ByteSmithJJ/mingbo-seal-server/issues) 中搜索，确认 Bug 未被报告过
2. 使用 Bug Report 模板创建新 Issue
3. 提供详细的复现步骤、环境信息和期望行为

### 分支策略

| 分支 | 用途 |
|------|------|
| `main` | 稳定发布分支，仅通过 PR 从 `dev` 合入，禁止直推 |
| `dev` | 日常开发集成分支，所有功能分支从此拉出并合回 |

### 提交代码

1. **Fork** 本项目
2. 从 `dev` 分支创建您的 feature 分支：
   ```bash
   git checkout dev
   git checkout -b feature/your-feature
   ```
3. 进行代码修改，遵循项目现有代码风格
4. 确保构建通过：
   ```bash
   mvn clean compile
   mvn clean package
   ```
5. 提交 Pull Request **到 `dev` 分支**

## 开发环境

### 前置要求

- **JDK** >= 17
- **Maven** >= 3.8
- **MySQL** >= 8.0
- **Redis** >= 6.0

### 快速开始

```bash
# 克隆仓库
git clone https://github.com/ByteSmithJJ/mingbo-seal-server.git
cd mingbo-seal-server

# 初始化数据库（执行 sql/ 目录下的 SQL 文件）
# 1. ry_20260417.sql  — 基础表结构
# 2. quartz.sql       — 定时任务表
# 3. biz_tables.sql   — 印章审批业务表
# 4. flowable-init.sql — 流程引擎表

# 编译
mvn clean compile

# 启动
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

服务默认运行在 `http://localhost:8080`。

### 项目结构

```
seal-approval-server/
├── ruoyi-admin/       # Web 入口（Controller、启动类、配置文件）
├── ruoyi-common/      # 通用模块（注解、枚举、工具类、基础类型）
├── ruoyi-framework/   # 框架模块（安全配置、JWT、AOP、异常处理）
├── ruoyi-system/      # 业务模块（实体、Mapper、Service）
├── ruoyi-quartz/      # 定时任务模块
├── ruoyi-generator/   # 代码生成器模块
├── sql/               # 数据库初始化脚本
└── doc/               # 设计文档
```

## 代码风格

- 遵循阿里巴巴 Java 开发手册
- 类名使用大驼峰，方法名和变量名使用小驼峰
- Controller 使用 `@RestController`，Service 使用 `@Service`
- 统一使用 `AjaxResult` 和 `TableDataInfo` 作为 API 返回格式

## PR 审核流程

1. 维护者会审核您的 PR
2. CI 检查必须全部通过
3. 可能会要求修改
4. 审核通过后合并到 `dev` 分支，后续由维护者将 `dev` 合并到 `main` 并发布

## 许可证

贡献的代码将采用 [MIT License](./LICENSE) 开源。
