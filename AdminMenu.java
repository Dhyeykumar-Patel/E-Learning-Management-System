import com.sun.tools.javac.Main;

import java.util.Scanner;

class AdminMenu {
    Scanner sc = new Scanner(System.in);
    //StudentService studentService = new StudentService();
    //TeacherService teacherService = new TeacherService();
    StudentManagement studentManagement=new StudentManagement();
    TeacherManagement teacherManagement=new TeacherManagement();

    CourseService courseService = new CourseService();

    AttendanceService attendanceService = new AttendanceService();

    QuizService quizService = new QuizService();

    ResultService resultService = new ResultService();
    public void adminDashboard() {
        int choice;
        do {
            System.out.println("\n==========================================");
            System.out.println("      E-LEARNING MANAGEMENT SYSTEM");
            System.out.println("            ADMIN DASHBOARD");
            System.out.println("==========================================");
            System.out.println("1. Student Management");
            System.out.println("2. Teacher Management");
            System.out.println("3. Course Management");
            System.out.println("4. Attendance Management");
            System.out.println("5. Quiz Management");
            System.out.println("6. Result Management");
            System.out.println("7. Logout");
            System.out.print("\nEnter Choice : ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    studentManagement.studentMenu1();
                    break;
                case 2:
                    teacherManagement.teacherMenu();
                    break;
                case 3:
                    courseService.courseMenu();
                    break;
                case 4:
                    attendanceService.attendanceMenu();
                    break;
                case 5:
                    quizService.quizMenu();
                    break;
                case 6:
                    resultService.resultMenu();
                    break;
                case 7:
                    System.out.println("\nAdmin Logged Out Successfully.");
                    return;
                default:
                    System.out.println("\nInvalid Choice.");
            }
        } while (choice != 8);
    }
}
