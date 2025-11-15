# 🗃️ 数据库设计文档

本文档描述系统中三张核心表的结构与设计说明，包括字段定义、安全与索引策略等。

---

## 1. 用户表 `users`

**用途**：存储系统注册用户的基本信息，支持账号密码登录。

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| `user_id` | `BIGINT` | PRIMARY KEY, AUTO_INCREMENT | 用户唯一 ID |
| `username` | `VARCHAR(50)` | NOT NULL, UNIQUE | 登录用户名（如：`alice123`） |
| `email` | `VARCHAR(100)` | UNIQUE | 邮箱，可用于登录或找回密码 |
| `password_hash` | `VARCHAR(255)` | NOT NULL | 密码哈希值（使用 bcrypt 等算法加密） |
| `name` | `VARCHAR(100)` | DEFAULT NULL | 用户显示名称 / 真实姓名（如：“张三”） |
| `created_at` | `DATETIME` | DEFAULT CURRENT_TIMESTAMP | 账号创建时间 |
| `updated_at` | `DATETIME` | ON UPDATE CURRENT_TIMESTAMP | 最后信息更新时间 |

### 🔒 安全说明
- 禁止明文存储密码，必须使用 **强哈希算法**（如 bcrypt、Argon2）。  
- `username` 与 `email` 均设为 **唯一索引**，防止重复注册。

---

## 2. 会话表 `sessions`

**用途**：管理用户的多轮对话记录，支持上下文追踪与分组。

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| `session_id` | `VARCHAR(64)` | PRIMARY KEY | 会话唯一 ID（建议使用 UUID 或 ULID） |
| `user_id` | `BIGINT` | NOT NULL, FOREIGN KEY → `users(user_id)` | 所属用户 ID |
| `title` | `VARCHAR(100)` | DEFAULT '新会话' | 会话标题（自动生成或用户编辑） |
| `created_at` | `DATETIME` | DEFAULT CURRENT_TIMESTAMP | 会话创建时间 |
| `updated_at` | `DATETIME` | ON UPDATE CURRENT_TIMESTAMP | 最后交互时间 |
| `is_deleted` | `TINYINT(1)` / `BOOLEAN` | DEFAULT 0 | 软删除标志（0=正常，1=已删除） |
| `status` | `VARCHAR(20)` | DEFAULT 'active' | 会话状态（如：`active`, `archived`） |

### 💡 设计说明
- `session_id` 使用 **非自增主键**，提升安全性与分布式兼容性。  
- 支持 **软删除**，可避免误删会话记录。  
- 启用外键级联删除：当用户注销时，其所有会话将自动清除（`ON DELETE CASCADE`）。

---

## 3. 问答历史表 `qa_history`

**用途**：记录用户与 AI 的问答内容，用于追踪和回溯对话。

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| `qa_id` | `BIGINT` | PRIMARY KEY, AUTO_INCREMENT | 问答记录唯一 ID |
| `user_id` | `BIGINT` | NOT NULL, FOREIGN KEY → `users(user_id)` | 所属用户 ID |
| `session_id` | `VARCHAR(64)` | FOREIGN KEY → `sessions(session_id)` | 所属会话 ID |
| `question` | `TEXT` | NOT NULL | 用户原始提问内容 |
| `answer` | `TEXT` | NOT NULL | AI 返回的回答内容 |
| `created_at` | `DATETIME` | DEFAULT CURRENT_TIMESTAMP | 问答发生时间 |

### ⚠️ 注意事项
- **敏感内容处理**：生产环境建议对 `question` / `answer` 进行脱敏或审核。  
- **索引优化**：
  - `(user_id, created_at)`：快速查询某用户的历史记录。  
  - `(session_id)`：快速加载单个会话的问答记录。  
- 若系统未启用会话功能，`session_id` 可为空（但建议始终创建会话以便未来扩展）。

---

## 🧩 外键关系图

