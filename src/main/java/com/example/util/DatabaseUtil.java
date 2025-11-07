package com.example.studentmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseUtil 是一个简单的数据库连接工具类。
 * <p>
 * 该类将 JDBC 连接的细节封装为静态方法 {@link #getConnection()}，
 * 方便项目中其他类直接调用以获取数据库连接。
 * </p>
 *
 * <p><b>注意：</b>当前实现将数据库连接信息硬编码在常量中，且直接使用
 * {@link DriverManager#getConnection(String, String, String)}。这是开发/学习阶段
 * 可接受的做法，但生产环境应使用外部配置、连接池和更健壮的错误处理。</p>
 */
public class DatabaseUtil {

    /**
     * JDBC 连接字符串（URL）。
     * <p>
     * 格式：jdbc:mysql://host:port/database?参数
     * 参数示例：
     * - useSSL=false：是否使用 SSL（开发阶段常设为 false）
     * - serverTimezone=UTC：设置服务器时区，避免时区问题
     * - allowPublicKeyRetrieval=true：允许公钥检索（特定 MySQL 配置下需要；生产需谨慎）
     * </p>
     */
    private static final String URL = "jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /** 数据库用户名 */
    private static final String USERNAME = "root";

    /** 数据库密码 */

    private static final String PASSWORD = "123456"; // 确认密码正确！

    /**
     * 获得一个数据库连接。
     *
     * @return 成功时返回 {@link Connection}，失败时返回 null（调用者需判断返回值）
     * @implNote 现代的 JDBC 驱动通常会在类加载时自动注册，所以不需要显式调用 {@code Class.forName(...)}。
     *           本方法简单直接地使用 DriverManager 获取连接，适合学习和小型 demo。
     */
    public static Connection getConnection() {
        try {
            // 通过 DriverManager 建立并返回连接（可能抛出 SQLException）
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // 连接失败时打印错误信息（生产建议使用日志框架并做更细致的错误处理）
            System.out.println("数据库连接失败: " + e.getMessage());
            return null;
        }
    }
}
