<h1 align="center">MingBo Seal Server</h1>
<p align="center">
  Enterprise E-Seal & Approval Workflow Platform · Backend
</p>
<p align="center">
  Built with Spring Boot 3 + Flowable Engine + MySQL + Redis
</p>

<div align="center">English | <a href="./README.zh-CN.md">简体中文</a></div>

<br />

<div align="center">

[![license](https://img.shields.io/badge/license-MIT-green.svg)](./LICENSE)
[![JDK](https://img.shields.io/badge/JDK-17-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F.svg)](https://spring.io/projects/spring-boot)

</div>

<br />

## Overview

MingBo Seal Server is the backend service of the MingBo e-signature and approval workflow platform. Core capabilities include:

- **Seal Lifecycle Management** — Digital management of seal creation, binding, authorization, revocation, and destruction
- **Approval Workflow Engine** — Flowable BPMN 2.0 based process design, execution, countersign/or-sign, and conditional branching
- **Fine-Grained Access Control** — Three-tier permission model (menu, button, data) with JWT multi-terminal authentication
- **System Monitoring** — Online users, server monitor, cache monitor, connection pool monitor, and operation logs

> 🔗 **Frontend**: [mingbo-seal-web](https://github.com/ByteSmithJJ/mingbo-seal-web) — Vue 3 + TypeScript + Element Plus

## Tech Stack

| Category | Technology |
|----------|------------|
| Application Framework | Spring Boot 3.5, Spring Security |
| Workflow Engine | Flowable 7.0 (BPMN 2.0) |
| Data Persistence | MyBatis 3, Druid Connection Pool, PageHelper Pagination |
| Cache | Redis (Lettuce Client) |
| Database | MySQL 8.0 |
| Authentication | JWT (jjwt), Spring Security |
| API Documentation | SpringDoc / Swagger UI |
| Scheduled Tasks | Quartz |
| Utilities | Fastjson2, Apache POI, Commons IO, Oshi |
| Build Tool | Maven 3.8+ |
| Runtime | JDK 17+ |

## Screenshots

<table>
  <tr>
    <td><img src="doc/screenshots/login.png" alt="Login"></td>
    <td><img src="doc/screenshots/dashboard.png" alt="Dashboard"></td>
  </tr>
  <tr>
    <td align="center"><strong>Login</strong></td>
    <td align="center"><strong>Dashboard</strong></td>
  </tr>
  <tr>
    <td><img src="doc/screenshots/seal-position.png" alt="Seal Position"></td>
    <td><img src="doc/screenshots/form-designer.png" alt="Form Designer"></td>
  </tr>
  <tr>
    <td align="center"><strong>Seal Position Configuration</strong></td>
    <td align="center"><strong>Form Designer</strong></td>
  </tr>
  <tr>
    <td><img src="doc/screenshots/process-designer.png" alt="Process Designer"></td>
    <td><img src="doc/screenshots/start-process.png" alt="Start Process"></td>
  </tr>
  <tr>
    <td align="center"><strong>BPMN Process Designer</strong></td>
    <td align="center"><strong>Start Process</strong></td>
  </tr>
  <tr>
    <td colspan="2"><img src="doc/screenshots/my-applications.png" alt="My Applications"></td>
  </tr>
  <tr>
    <td align="center" colspan="2"><strong>My Applications</strong></td>
  </tr>
</table>

## Module Structure

```
mingbo-seal-server/
├── ruoyi-admin/         # Web Entry — Controllers, Spring Boot startup, YAML config
├── ruoyi-framework/     # Framework — Security config, JWT filters, AOP aspects, global exception handling
├── ruoyi-system/        # Business — Entities, Mapper interfaces, Service implementations
├── ruoyi-common/        # Common — Annotations, enums, utilities, base types (AjaxResult, etc.)
├── ruoyi-quartz/        # Scheduled Task Module
├── ruoyi-generator/     # Code Generator (Velocity templates)
├── sql/                 # Database initialization scripts
└── doc/                 # Design documentation
```

**Dependency chain**: `ruoyi-admin` → `ruoyi-framework` → `ruoyi-system` → `ruoyi-common`

## Built-in Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | User Management | System user configuration with multi-role support |
| 2 | Department Management | Tree-structured organization with data permissions |
| 3 | Position Management | User position/job title management |
| 4 | Menu Management | Menu and button permission configuration |
| 5 | Role Management | Menu permission assignment + data scope control |
| 6 | Dictionary Management | Fixed dictionary data maintenance |
| 7 | Parameter Management | System dynamic parameter configuration |
| 8 | Notifications | System notice publishing |
| 9 | Operation Logs | Operation log recording and querying |
| 10 | Login Logs | Login log query (including abnormal logins) |
| 11 | Online Users | Active user status monitoring |
| 12 | Scheduled Tasks | Online Cron-based task scheduling |
| 13 | Code Generator | One-click CRUD code generation |
| 14 | API Documentation | Auto-generated Swagger/SpringDoc API docs |
| 15 | Server Monitor | Real-time CPU, memory, disk, JVM monitoring |
| 16 | Cache Monitor | Redis cache info and command statistics |
| 17 | Connection Pool Monitor | Druid pool status and slow SQL detection |
| 18 | 🔐 Seal Management | Seal creation, authorization, revocation, destruction |
| 19 | 📋 Approval Workflow | Flowable process design, execution, approval |

## Quick Start

### Prerequisites

- **JDK** >= 17
- **Maven** >= 3.8
- **MySQL** >= 8.0
- **Redis** >= 6.0

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/ByteSmithJJ/mingbo-seal-server.git
cd mingbo-seal-server

# 2. Initialize the database (run in order)
#    - sql/ry_20260417.sql    Base table structures
#    - sql/quartz.sql          Scheduled task tables
#    - sql/biz_tables.sql      Seal & approval business tables
#    - flowable-init.sql       Workflow engine tables (project root)

# 3. Configure database / Redis connection
#    Edit ruoyi-admin/src/main/resources/application.yml
#    Edit ruoyi-admin/src/main/resources/application-druid.yml

# 4. Build & Run
mvn clean compile
mvn clean package -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

After startup, access:
- **API Docs**: http://localhost:8080/swagger-ui.html
- **Druid Console**: http://localhost:8080/druid/ (default credentials: `ruoyi` / `123456`)

### Environment Variables

| Config | File | Default | Description |
|--------|------|---------|-------------|
| `server.port` | application.yml | 8080 | Server port |
| `spring.data.redis.host` | application.yml | localhost | Redis host |
| `spring.data.redis.password` | application.yml | (empty) | Redis password |
| `token.secret` | application.yml | changeme | JWT signing key |
| `ruoyi.profile` | application.yml | ./uploadPath | File upload path |
| Database connection | application-druid.yml | localhost:3306 | MySQL connection info |

> ⚠️ In production, always change `token.secret`, database password, and Redis password.

## Acknowledgments

This project is built upon the following excellent open source projects:

| Project | Purpose | License |
|---------|---------|---------|
| **[RuoYi-Vue (若依)](https://gitee.com/y_project/RuoYi-Vue)** | Rapid Development Framework | MIT |
| **[Flowable](https://github.com/flowable/flowable-engine)** | BPMN 2.0 Workflow Engine | Apache 2.0 |
| **[Spring Boot](https://github.com/spring-projects/spring-boot)** | Java Application Framework | Apache 2.0 |
| **[Spring Security](https://github.com/spring-projects/spring-security)** | Security & Authentication | Apache 2.0 |
| **[MyBatis](https://github.com/mybatis/mybatis-3)** | Persistence Framework | Apache 2.0 |
| **[Druid](https://github.com/alibaba/druid)** | Database Connection Pool | Apache 2.0 |
| **[Redis](https://redis.io/)** | In-Memory Database (Cache) | BSD |
| **[MySQL](https://www.mysql.com/)** | Relational Database | GPL |
| **[jjwt](https://github.com/jwtk/jjwt)** | JWT Token Generation & Parsing | Apache 2.0 |
| **[PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)** | MyBatis Pagination Plugin | MIT |
| **[SpringDoc](https://github.com/springdoc/springdoc-openapi)** | OpenAPI/Swagger Documentation | Apache 2.0 |
| **[Quartz](http://www.quartz-scheduler.org/)** | Scheduled Task Scheduling | Apache 2.0 |
| **[Fastjson2](https://github.com/alibaba/fastjson2)** | JSON Parser | Apache 2.0 |
| **[Oshi](https://github.com/oshi/oshi)** | System Information Monitoring | MIT |
| **[Kaptcha](https://github.com/lingochamp/kaptcha)** | CAPTCHA Generation | Apache 2.0 |

> Special thanks to the **RuoYi-Vue** project and its community for providing a solid foundational architecture.

## Contributing

Contributions are welcome! See [CONTRIBUTING.md](./CONTRIBUTING.md) for development guidelines and PR workflow.

## License

This project is open-sourced under the [MIT License](./LICENSE). Portions of the code originate from RuoYi-Vue (Copyright © 2018 RuoYi), used under the MIT license.
