package com.example.studentmanagement.dao;
//导入要操作的实体对象,封装数据库连接的工具类,以及JDBC的核心接口sql.*,还有装查询结果集的list和arraylist
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    /*
    添加学生.定义 SQL 模板：? 是占位符，用来防止 SQL 注入。
     */
    //方法声明,传入一个Student对象,返回一个布尔值
    // 向数据库添加一名学生
    public boolean addStudent(Student student) {
        // 1️⃣ 定义 SQL 语句，使用 ? 作为占位符（防止SQL注入）
        String sql = "INSERT INTO students (student_id, name, gender, age, major, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 2️⃣ 使用 try-with-resources 自动管理数据库资源
        //    getConnection()：获取数据库连接对象
        //    prepareStatement(sql)：创建执行SQL的预处理语句对象
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 3️⃣ 依次给 SQL 语句中的 ? 赋值
            pstmt.setString(1, student.getStudentId()); // 第1个参数：学号
            pstmt.setString(2, student.getName());      // 第2个参数：姓名
            pstmt.setString(3, student.getGender());    // 第3个参数：性别
            pstmt.setInt(4, student.getAge());          // 第4个参数：年龄
            pstmt.setString(5, student.getMajor());     // 第5个参数：专业
            pstmt.setString(6, student.getPhone());     // 第6个参数：电话
            pstmt.setString(7, student.getEmail());     // 第7个参数：邮箱

            // 4️⃣ 执行插入操作
            // executeUpdate() 会返回受影响的行数（成功插入返回1）
            // >0 表示至少插入了一行记录，说明添加成功
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // 5️⃣ 捕获数据库异常，打印错误信息并返回失败
            e.printStackTrace();
            return false;
        }
    }


    // 删除学生
    public boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新学生信息
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, gender=?, age=?, major=?, phone=?, email=? WHERE student_id=?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getMajor());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getStudentId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查询所有学生
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentId(rs.getString("student_id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setMajor(rs.getString("major"));
                student.setPhone(rs.getString("phone"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // 根据学号查询学生
    public Student getStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentId(rs.getString("student_id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setMajor(rs.getString("major"));
                student.setPhone(rs.getString("phone"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}