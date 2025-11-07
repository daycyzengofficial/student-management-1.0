package com.example.studentmanagement.service;
//属于service包,说明职责是业务逻辑处理
/*
业务逻辑层,三层架构中最核心的一环,负责把控制层main与数据访问层dao连接起来
 */

/*
说明这个类依赖两个关键部分：
StudentDao：数据访问层（负责和数据库交互）
Student：数据模型（学生对象）
 */
import com.example.studentmanagement.dao.StudentDao;
import com.example.studentmanagement.model.Student;
//List：用来装多个学生对象。
import java.util.List;

/*
StudentService 类是业务逻辑层的主体。
里面有一个私有成员变量 studentDao，用于与数据库层沟通。
Service 不直接操作数据库，而是委托给 Dao。
这样逻辑清晰、职责分明。
 */
public class StudentService {
    /*
    在业务逻辑层（StudentService）里，声明了一个 StudentDao 成员变量，用来与数据访问层交互。
     */
    private StudentDao studentDao;

    /*
    构造方法,当你在 Main 里写 new StudentService() 时，这里会执行。
    它创建一个新的 StudentDao 对象，并赋值给 studentDao。
    也就是说：每个 Service 拥有一个自己的 Dao，用它来访问数据库
     */
    public StudentService() {
        this.studentDao = new StudentDao();
    }

    /*
    运行逻辑：
    先查重：调用 studentDao.getStudentById() 看学号是否已经存在。
    如果找到了学生（返回非 null），说明重复 → 打印错误 → 返回 false。
    未重复时添加：调用 studentDao.addStudent(student) 真正执行数据库插入。
    这是业务层的职责：保证逻辑正确性（比如防止重复添加）。Dao 层只管“能不能插”，不管“应不应该插”。
     */
    public boolean addStudent(Student student) {
        // 检查学号是否已存在
        if (studentDao.getStudentById(student.getStudentId()) != null) {
            System.out.println("错误：学号 " + student.getStudentId() + " 已存在！");
            return false;
        }
        return studentDao.addStudent(student);
    }

    /*
    逻辑：
    检查要删的学生是否存在。
    不存在则打印错误并返回 false；存在则调用 Dao 的 deleteStudent() 执行删除。
    Service 层负责校验存在性，防止无效删除操作。
     */
    public boolean deleteStudent(String studentId) {
        if (studentDao.getStudentById(studentId) == null) {
            System.out.println("错误：学号 " + studentId + " 不存在！");
            return false;
        }
        return studentDao.deleteStudent(studentId);
    }

    /*
    逻辑：
    检查学号是否存在（不能修改一个不存在的学生）。
    存在 → 调用 Dao 执行更新；
    不存在 → 打印错误并返回 false。
    业务层保证“修改操作的合法性”，Dao 层只管“更新语句是否成功”。
     */
    public boolean updateStudent(Student student) {
        if (studentDao.getStudentById(student.getStudentId()) == null) {
            System.out.println("错误：学号 " + student.getStudentId() + " 不存在！");
            return false;
        }
        return studentDao.updateStudent(student);
    }

    /*
    没有额外逻辑，直接从 Dao 拿数据返回给 Main。
    因为这类操作一般没有业务规则，不用校验。
     */
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    //同样是简单转发：Main 想查某个学生 → 调 Service → 调 Dao。
    public Student getStudentById(String studentId) {
        return studentDao.getStudentById(studentId);
    }
}