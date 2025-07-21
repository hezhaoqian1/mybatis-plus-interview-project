# MyBatis-Plus 分页查询面试项目

## 📋 项目简介

这是一个基于SpringBoot + MyBatis-Plus的分页查询实现项目，主要展示了在禁止联合查询约束下的用户信息分页查询功能。

## 🎯 功能特性

- ✅ 用户信息分页查询
- ✅ 支持部门名称模糊查询
- ✅ 支持性别精准查询  
- ✅ 支持用户名模糊查询
- ✅ 禁止联合查询，采用分步查询方案
- ✅ 完整的SpringBoot测试验证

## 🏗️ 技术栈

- **框架**: SpringBoot 2.6.7
- **ORM**: MyBatis-Plus 3.5.1
- **数据库**: MySQL 8.0
- **连接池**: Druid 1.2.8
- **测试**: JUnit 5
- **构建工具**: Maven 3.6.3

## 📁 项目结构

```
hh-admin/
├── src/main/java/io/hh/
│   ├── AdminApplication.java                    # 启动类
│   ├── common/config/MybatisPlusConfig.java    # MyBatis-Plus配置
│   └── modules/api/
│       ├── dto/                                # 数据传输对象
│       │   ├── UserQueryVo.java               # 查询条件封装
│       │   └── UserResultDto.java             # 返回结果封装
│       ├── entity/                             # 实体类
│       │   ├── SysUser.java                   # 用户实体
│       │   └── SysDept.java                   # 部门实体
│       ├── mapper/                             # 数据访问层
│       │   ├── SysUserMapper.java             # 用户Mapper
│       │   └── SysDeptMapper.java             # 部门Mapper
│       └── service/                            # 业务逻辑层
│           ├── SysUserService.java            # 用户服务接口
│           ├── SysDeptService.java            # 部门服务接口
│           └── impl/
│               ├── SysUserServiceImpl.java    # 用户服务实现★
│               └── SysDeptServiceImpl.java    # 部门服务实现
├── src/test/java/io/hh/
│   └── Test.java                              # SpringBoot测试类★
├── src/main/resources/
│   ├── application.yml                        # 主配置文件
│   └── application-dev.yml                    # 开发环境配置
├── db/
│   └── test1.sql                              # 数据库初始化脚本
└── pom.xml                                    # Maven配置
```

## 🔧 核心实现

### 分步查询策略

由于禁止使用联合查询，采用以下分步查询方案：

1. **部门查询**: 根据部门名称模糊查询获取部门ID集合
2. **用户分页查询**: 使用部门ID集合查询用户信息
3. **部门信息补充**: 批量查询部门信息并组装到返回结果

### 关键代码片段

```java
// 1. 部门名称查询
if (StringUtils.hasText(condition.getDeptName())) {
    LambdaQueryWrapper<SysDept> deptWrapper = new LambdaQueryWrapper<>();
    deptWrapper.like(SysDept::getName, condition.getDeptName());
    List<SysDept> deptList = sysDeptService.list(deptWrapper);
    
    if (!deptList.isEmpty()) {
        List<Long> deptIds = deptList.stream()
            .map(SysDept::getId)
            .collect(Collectors.toList());
        userWrapper.in(SysUser::getDeptId, deptIds);
    } else {
        userWrapper.eq(SysUser::getId, -1L); // 确保查询结果为空
    }
}

// 2. 分页查询
Page<SysUser> page = new Page<>(pageInfo.getCurrent(), pageInfo.getSize());
Page<SysUser> userPage = this.page(page, userWrapper);

// 3. 部门信息组装
Map<Long, String> deptNameMap = // 批量查询部门信息构建映射
List<UserResultDto.UserInfo> userInfoList = userList.stream()
    .map(user -> {
        UserResultDto.UserInfo userInfo = new UserResultDto.UserInfo();
        // ... 设置用户基本信息
        if (deptNameMap != null && user.getDeptId() != null) {
            userInfo.setDeptName(deptNameMap.get(user.getDeptId()));
        }
        return userInfo;
    })
    .collect(Collectors.toList());
```

## 🚀 快速开始

### 1. 环境要求
- Java 8+
- Maven 3.6+
- MySQL 5.7+

### 2. 数据库初始化
```sql
# 创建数据库
CREATE DATABASE test1;

# 导入数据
mysql -u root -p test1 < hh-admin/db/test1.sql
```

### 3. 配置数据库连接
修改 `src/main/resources/application-dev.yml`:
```yaml
spring:
  datasource:
    druid:
      url: jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
      username: root
      password: your_password
```

### 4. 运行测试
```bash
cd hh-admin
mvn test -Dtest=io.hh.Test
```

## 📊 测试结果示例

```
查询结果：
总记录数：1
当前页数据：
ID: 1067246875800000001, 用户名: admin, 性别: 0, 真实姓名: 管理员, 部门名称: 杭州宏汉软件技术有限公司
```

### SQL执行日志
```sql
-- 1. 查询部门
SELECT * FROM sys_dept WHERE (name LIKE ?)
Parameters: %技术%(String) 
Total: 2

-- 2. 统计总数
SELECT COUNT(*) FROM sys_user WHERE (gender = ? AND username LIKE ? AND dept_id IN (?, ?))
Parameters: 0(Integer), %admin%(String), 1067246875800000062(Long), 1067246875800000066(Long)
Total: 1

-- 3. 分页查询
SELECT * FROM sys_user WHERE (gender = ? AND username LIKE ? AND dept_id IN (?,?)) LIMIT ?
Parameters: 0(Integer), %admin%(String), 1067246875800000062(Long), 1067246875800000066(Long), 10(Long)
Total: 1

-- 4. 查询部门信息
SELECT * FROM sys_dept WHERE id IN ( ? )
Parameters: 1067246875800000066(Long)
Total: 1
```

## 🎯 设计亮点

1. **严格遵循约束**: 完全禁止联合查询，用分步查询实现
2. **类型安全**: 使用Lambda表达式构造查询条件
3. **性能优化**: 批量查询部门信息，减少N+1查询问题
4. **代码规范**: Vo/Dto命名规范，分层清晰
5. **测试完整**: SpringBoot测试类全覆盖

## 🔍 面试要点总结

### 技术难点解决
- **关联查询问题**: 用分步查询替代JOIN
- **性能优化**: 批量查询 + Map映射避免循环查询
- **条件构造**: MyBatis-Plus LambdaQueryWrapper确保类型安全

### 代码质量
- 遵循单一职责原则
- 良好的异常处理
- 完整的空值判断
- 清晰的代码注释

## 📄 许可证

MIT License

## 👨‍💻 作者

面试项目实现