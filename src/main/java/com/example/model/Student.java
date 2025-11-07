package com.example.studentmanagement.model;

import java.time.LocalDateTime;

public class Student {
    private Integer id;
    private String studentId;
    private String name;
    private String gender;
    private Integer age;
    private String major;
    private String phone;
    private String email;
    private LocalDateTime createTime;

    // 构造方法
    public Student() {}

    public Student(String studentId, String name, String gender, Integer age, String major, String phone, String email) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.phone = phone;
        this.email = email;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    @Override
    public String toString() {
        return String.format("学号: %s, 姓名: %s, 性别: %s, 年龄: %d, 专业: %s, 电话: %s, 邮箱: %s",
                studentId, name, gender, age, major, phone, email);
    }
}