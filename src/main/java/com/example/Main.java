package com.example.studentmanagement;
//表明这个类属于studentmanagement包

/*
main()
 └── showMenu()
       ├── addStudent()
       ├── deleteStudent()
       ├── updateStudent()
       ├── listAllStudents()
       ├── findStudentById()
       └── getIntInput()
所有数据交互都是通过 studentService 完成的，
而 studentService 下面可能又会调用 StudentDao 去访问数据库。
 */

//导入项目里别的类
import com.example.studentmanagement.model.Student;     //导入学生实体类student,此为封装学生信息的的数据模型
import com.example.studentmanagement.service.StudentService;        //导入业务逻辑层,负责调用DAO或者数据层去增删查改
//导入java标准库的类:list是集合接口(存放学生对象列表),sacnner用于接收用户在命令行输入的内容
import java.util.List;
import java.util.Scanner;

public class Main {
    //静态属性初始化,在类加载时就执行.studentservice创建一个studentservice对象,用来调用增删查改方法;scanner从键盘输入流systemin创建一个扫描器用于读取用户输入;这两个是静态变量因为整个系统只需要一个服务对象和一个输入扫描器就够了
    private static StudentService studentService = new StudentService();
    private static Scanner scanner = new Scanner(System.in);
    //程序入口,main是java程序固定的入口方法,这个main只做了一件事:调用showMenu()显示菜单,启动主循环
    public static void main(String[] args) {
        showMenu();
    }
    //菜单方法showMenu
    private static void showMenu() {
        while (true) {      //死循环,因为程序要一直让用户操作直到选择退出
            //打印菜单选项(增删查改退出)
            System.out.println("\n=== 学生信息管理系统 ===");
            System.out.println("1. 添加学生");
            System.out.println("2. 删除学生");
            System.out.println("3. 修改学生信息");
            System.out.println("4. 查询所有学生");
            System.out.println("5. 根据学号查询学生");
            System.out.println("6. 退出系统");
            System.out.print("请选择操作（1-6）: ");
            //调用getintinput()获取用户输入的数字选项
            int choice = getIntInput();
            //分支逻辑,根据用户输入的数字调用不同的方法,return退出主程序使用的唯一方法从而结束整个程序
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    deleteStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    listAllStudents();
                    break;
                case 5:
                    findStudentById();
                    break;
                case 6:
                    System.out.println("感谢使用学生信息管理系统，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    /*
    添加学生方法
    让用户输入每一项学生信息,每个输入调用scannernextline从命令行读取字符串
    全部信息输入完成后创建一个新的student实例装载这些数据
    调用 studentService.addStudent() 把学生交给业务层去添加到数据库或列表中。
    添加成功或失败都打印提示。
     */
    private static void addStudent() {
        System.out.println("\n--- 添加学生 ---");

        System.out.print("请输入学号: ");
        String studentId = scanner.nextLine();

        System.out.print("请输入姓名: ");
        String name = scanner.nextLine();

        System.out.print("请输入性别: ");
        String gender = scanner.nextLine();

        System.out.print("请输入年龄: ");
        int age = getIntInput();

        System.out.print("请输入专业: ");
        String major = scanner.nextLine();

        System.out.print("请输入电话: ");
        String phone = scanner.nextLine();

        System.out.print("请输入邮箱: ");
        String email = scanner.nextLine();

        Student student = new Student(studentId, name, gender, age, major, phone, email);
        /*
        在 if 语句中，条件必须是一个 布尔类型的表达式。
        这个if条件是方法调用表达式,返回值类型boolean
        这意味着在 StudentService 类中，大概有这样一个方法：
        public boolean addStudent(Student student) {
        // 逻辑：写入数据库或列表
        // 成功返回 true，失败返回 false
        }

         */
        if (studentService.addStudent(student)) {
            System.out.println("学生信息添加成功！");
        } else {
            System.out.println("学生信息添加失败！");
        }
    }

    /*
    删除学生方法
    让学生输入学号
    确认
    调用studentService.deleteStudent(studentId),删除逻辑由service层实现
     */
    private static void deleteStudent() {
        System.out.println("\n--- 删除学生 ---");
        System.out.print("请输入要删除的学生学号: ");
        String studentId = scanner.nextLine();

        System.out.print("确认删除学号为 " + studentId + " 的学生吗？(y/n): ");
        String confirm = scanner.nextLine();

        if ("y".equalsIgnoreCase(confirm)) {
            if (studentService.deleteStudent(studentId)) {
                System.out.println("学生信息删除成功！");
            } else {
                System.out.println("学生信息删除失败！");
            }
        } else {
            System.out.println("已取消删除操作。");
        }
    }

    /*
    修改学生信息方法
    让用户输入学号；
    调用 studentService.getStudentById(studentId) 查找学生；
    若找到，显示当前信息；
    对每个字段让用户输入新值（回车代表不改）；
    更新对象属性；
    调用 studentService.updateStudent(existingStudent) 保存修改。
     */
    private static void updateStudent() {
        System.out.println("\n--- 修改学生信息 ---");
        System.out.print("请输入要修改的学生学号: ");
        String studentId = scanner.nextLine();

        Student existingStudent = studentService.getStudentById(studentId);
        if (existingStudent == null) {
            System.out.println("学生不存在！");
            return;
        }

        System.out.println("当前信息: " + existingStudent);
        System.out.println("请输入新的信息（直接回车保持原值）:");

        System.out.print("姓名[" + existingStudent.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingStudent.setName(name);
        }

        System.out.print("性别[" + existingStudent.getGender() + "]: ");
        String gender = scanner.nextLine();
        if (!gender.isEmpty()) {
            existingStudent.setGender(gender);
        }

        System.out.print("年龄[" + existingStudent.getAge() + "]: ");
        String ageStr = scanner.nextLine();
        if (!ageStr.isEmpty()) {
            existingStudent.setAge(Integer.parseInt(ageStr));
        }

        System.out.print("专业[" + existingStudent.getMajor() + "]: ");
        String major = scanner.nextLine();
        if (!major.isEmpty()) {
            existingStudent.setMajor(major);
        }

        System.out.print("电话[" + existingStudent.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            existingStudent.setPhone(phone);
        }

        System.out.print("邮箱[" + existingStudent.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            existingStudent.setEmail(email);
        }

        if (studentService.updateStudent(existingStudent)) {
            System.out.println("学生信息修改成功！");
        } else {
            System.out.println("学生信息修改失败！");
        }
    }

    /*
    查询所有学生方法
    让service拿到一个学生列表
    循环打印每一条并输出总数
     */
    private static void listAllStudents() {
        System.out.println("\n--- 所有学生信息 ---");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("没有学生信息！");
        } else {
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i));
            }
            System.out.println("共 " + students.size() + " 名学生");
        }
    }

    /*
    根据学号查询方法:调用 Service 层方法返回单个对象，打印结果或提示未找到。
     */
    private static void findStudentById() {
        System.out.println("\n--- 根据学号查询学生 ---");
        System.out.print("请输入学号: ");
        String studentId = scanner.nextLine();

        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            System.out.println("查询结果: " + student);
        } else {
            System.out.println("未找到学号为 " + studentId + " 的学生！");
        }
    }

    /*
    输入校验辅助函数
    👉 专门用于安全地读取整数输入：
    读字符串；
    转成数字；
    如果输入不是数字（抛出异常），提示用户重输。
    它保证菜单选项和年龄输入不会崩溃。
     */
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("输入无效，请输入数字: ");
            }
        }
    }
}