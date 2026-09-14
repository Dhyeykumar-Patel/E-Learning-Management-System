import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AttendanceService {

    private Scanner sc;
    private AttendanceDAO attendanceDAO;

    public AttendanceService() {

        sc = new Scanner(System.in);

        attendanceDAO = new AttendanceDAO();
    }

    // ==========================================
    // MENU
    // ==========================================

    public void attendanceMenu() {

        while (true) {
            System.out.println("\n======================================");
            System.out.println("       ATTENDANCE MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Search Attendance");
            System.out.println("4. Update Attendance");
            System.out.println("5. Delete Attendance");

            System.out.println("6. Back");

            System.out.print("\nEnter Choice : ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");

                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:
                    markAttendance();
                    break;

                case 2:
                    viewAttendance();
                    break;

                case 3:
                    searchAttendance();
                    break;

                case 4:
                    updateAttendance();
                    break;

                case 5:
                    deleteAttendance();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    // ==========================================
    // ADD ATTENDANCE
    // ==========================================

    public void markAttendance() {

        System.out.println("\n========== ADD ATTENDANCE ==========");

        // ==========================================
        // STUDENT
        // ==========================================

        // ==========================================
// SHOW AVAILABLE STUDENTS
// ==========================================

        StudentDAO studentDAO = new StudentDAO();

        ArrayList<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {

            System.out.println("No Students Available.");

            return;
        }

        System.out.println("\n========== AVAILABLE STUDENTS ==========");

        for (int i = 0; i < students.size(); i++) {

            Student s = students.get(i);

            System.out.println((i + 1) + ". " + s.getName() + " | " + s.getEmail() + " | " + s.getMobile());
        }

        System.out.print("\nEnter Student Choice : ");

        if (!sc.hasNextInt()) {

            System.out.println("Please enter number only.");

            sc.nextLine();
            return;
        }

        int studentChoice = sc.nextInt();
        sc.nextLine();

        if (studentChoice < 1 || studentChoice > students.size()){
            System.out.println("Invalid Student Choice.");

            return;
        }
        // Selected student
        Student selectedStudent = students.get(studentChoice - 1);
        String studentName = selectedStudent.getName();
        String studentEmail = selectedStudent.getEmail();

        String studentMobile = selectedStudent.getMobile();

        // ==========================================
        // COURSE FROM DATABASE
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("Course Not Found.");

            return;
        }

        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (int i = 0; i < courses.size(); i++) {

            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        System.out.print("Enter Course Choice : ");

        if (!sc.hasNextInt()) {

            System.out.println("Please enter number only.");

            sc.nextLine();
            return;
        }

        int courseChoice = sc.nextInt();

        sc.nextLine();

        if (courseChoice < 1 || courseChoice > courses.size()) {
            System.out.println("Invalid Course Choice.");

            return;
        }

        String courseName = courses.get(courseChoice - 1).getCourseName();

        // ==========================================
        // TEACHER FROM DATABASE
        // ==========================================

        TeacherDAO teacherDAO = new TeacherDAO();

        ArrayList<Teacher> teachers = teacherDAO.getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("Teacher Not Found.");

            return;
        }

        System.out.println("\n========== AVAILABLE TEACHERS ==========");

        for (int i = 0; i < teachers.size(); i++) {

            System.out.println((i + 1) + ". " + teachers.get(i).getName());
        }
        System.out.print("Enter Teacher Choice : ");
        if (!sc.hasNextInt()) {
            System.out.println("Please enter number only.");
            sc.nextLine();
            return;
        }

        int teacherChoice = sc.nextInt();

        sc.nextLine();

        if (teacherChoice < 1 || teacherChoice > teachers.size()) {

            System.out.println("Invalid Teacher Choice.");

            return;
        }

        String teacherName = teachers.get(teacherChoice - 1).getName();

        // ==========================================
        // DATE
        // ==========================================

        LocalDate date = readDate();

        // ==========================================
        // STATUS
        // ==========================================

        String status;

        while (true) {

            System.out.print("Enter Status (Present/Absent) : ");
            status = sc.nextLine();
            if (status.equalsIgnoreCase("Present")) {

                status = "Present";
                break;
            }
            if (status.equalsIgnoreCase("Absent")) {
                status = "Absent";
                break;
            }
            System.out.println("Enter Present or Absent only.");
        }

        // ==========================================
        // CREATE OBJECT
        // ==========================================

        Attendance attendance = new Attendance(0, date, status, studentName, studentEmail, studentMobile, courseName, teacherName);

        attendanceDAO.markAttendance(attendance);
    }

    // ==========================================
    // VIEW
    // ==========================================

    public void viewAttendance() {

        ArrayList<Attendance> list = attendanceDAO.getAllAttendance();

        if (list.isEmpty()) {

            System.out.println("Attendance Not Found.");

            return;
        }

        System.out.println("\n========== ALL ATTENDANCE ==========");

        for (Attendance a : list) {

            a.displayAttendance();
        }
    }

    // ==========================================
    // SEARCH
    // ==========================================

    public void searchAttendance() {

        System.out.println("\n========== SEARCH ATTENDANCE ==========");

        System.out.print("Enter Student Name OR Email OR Mobile OR " +"Course OR Teacher : ");

        String value = sc.nextLine();

        ArrayList<Attendance> list =attendanceDAO.searchAttendance(value);

        if (list.isEmpty()) {

            System.out.println("Attendance Not Found.");

            return;
        }

        for (Attendance a : list) {
            a.displayAttendance();
        }
    }


    // ==========================================
// UPDATE ATTENDANCE
// =====================================================

    public void updateAttendance() {

        System.out.println("\n========== UPDATE ATTENDANCE ==========");

        // ==========================================
        // STUDENT NAME
        // ==========================================

        String studentName;

        while (true) {

            System.out.print("Enter Student Name : ");

            studentName = sc.nextLine().trim();

            if (attendanceDAO.studentNameExists(studentName)) {

                break;

            } else {

                System.out.println("Student Name Not Found In Database.");
            }
        }


        // ==========================================
        // EMAIL
        // ==========================================

        String studentEmail;

        while (true) {

            System.out.print("Enter Student Email : ");

            studentEmail = sc.nextLine().trim();

            if (attendanceDAO.studentNameEmailExists(studentName, studentEmail)) {

                break;

            } else {
                System.out.println("Student Email Not Found In Database.");
            }
        }


        // ==========================================
        // MOBILE
        // ==========================================

        String studentMobile;

        while (true) {

            System.out.print("Enter Student Mobile : ");

            studentMobile = sc.nextLine().trim();
            if (attendanceDAO.studentDetailsExists(studentName, studentEmail, studentMobile)) {

                break;

            } else {

                System.out.println("Student Mobile Not Found In Database.");
            }
        }


        // ==========================================
        // COURSE
        // ==========================================

        ArrayList<Course> courses = new CourseDAO().getAllCourses();

        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (int i = 0; i < courses.size(); i++) {

            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        int courseChoice;

        while (true) {

            System.out.print("Enter Course Choice : ");

            if (sc.hasNextInt()) {

                courseChoice = sc.nextInt();

                sc.nextLine();

                if (courseChoice >= 1 && courseChoice <= courses.size()) {

                    break;
                }

            } else {

                sc.nextLine();
            }

            System.out.println("Invalid Course Choice.");
        }

        Course selectedCourse = courses.get(courseChoice - 1);


        // ==========================================
        // OLD ATTENDANCE DATE
        // ==========================================

        LocalDate oldDate;

        while (true) {
            System.out.print("Enter Current Attendance Date " + "(yyyy-MM-dd) : ");
            String date = sc.nextLine().trim();
            try {
                oldDate = LocalDate.parse(date);

                break;

            } catch (Exception e) {

                System.out.println("Invalid Date Format.");
            }
        }


        // ==========================================
        // FIND ATTENDANCE
        // ==========================================

        Attendance attendance = attendanceDAO.searchAttendance1(studentName, studentEmail, studentMobile, selectedCourse.getCourseName(), oldDate);


        if (attendance == null) {
            System.out.println("Attendance Not Found In Database.");

            return;
        }


        System.out.println("\n========== ATTENDANCE DETAILS ==========");

        attendance.displayAttendance();


        // ==========================================
        // NEW DATE
        // ==========================================

        LocalDate newDate;

        while (true) {

            System.out.print("Enter New Attendance Date (yyyy-MM-dd) : ");

            String date = sc.nextLine().trim();

            try {

                newDate = LocalDate.parse(date);
                break;
            } catch (Exception e) {

                System.out.println("Invalid Date Format. Use yyyy-MM-dd.");
            }
        }


        // ==========================================
        // NEW STATUS
        // ==========================================

        String status;

        while (true) {

            System.out.print("Enter Status (Present/Absent) : ");

            status = sc.nextLine().trim();

            if (status.equalsIgnoreCase("Present")) {

                status = "Present";
                break;

            } else if (status.equalsIgnoreCase("Absent")) {

                status = "Absent";
                break;

            } else {

                System.out.println("Enter Present or Absent only.");
            }
        }


        // ==========================================
        // TEACHER
        // ==========================================

        ArrayList<Teacher> teachers = new TeacherDAO().getAllTeachers();

        System.out.println("\n========== AVAILABLE TEACHERS ==========");

        for (int i = 0; i < teachers.size(); i++) {

            System.out.println((i + 1) + ". " + teachers.get(i).getName());
        }

        int teacherChoice;

        while (true) {

            System.out.print("Enter Teacher Choice : ");

            if (sc.hasNextInt()) {

                teacherChoice = sc.nextInt();
                sc.nextLine();

                if (teacherChoice >= 1 && teacherChoice <= teachers.size()) {

                    break;
                }
            } else {

                sc.nextLine();
            }

            System.out.println("Invalid Teacher Choice.");
        }

        Teacher selectedTeacher = teachers.get(teacherChoice - 1);
        // ==========================================
        // UPDATE
        // ==========================================

        attendanceDAO.updateAttendance(attendance.getAttendanceId(), newDate, status);
    }
    // ==========================================
// DELETE ATTENDANCE
// =====================================================

    public void deleteAttendance() {

        System.out.println("\n========== DELETE ATTENDANCE ==========");

        StudentDAO studentDAO = new StudentDAO();

        // ==========================================
        // STUDENT NAME
        // ==========================================

        String studentName;
        Student student;

        while (true) {

            System.out.print("Enter Student Name : ");
            studentName = sc.nextLine().trim();

            student = studentDAO.searchStudentByName(studentName);

            if (student == null) {

                System.out.println("Student Name Not Found In Database.");

                continue;
            }

            break;
        }

        // ==========================================
        // EMAIL
        // ==========================================

        String studentEmail;

        while (true) {

            System.out.print("Enter Student Email : ");
            studentEmail = sc.nextLine().trim();

            if (!student.getEmail().equalsIgnoreCase(studentEmail)) {

                System.out.println("Student Email Not Found In Database.");

                continue;
            }

            break;
        }

        // ==========================================
        // MOBILE
        // ==========================================

        String studentMobile;

        while (true) {

            System.out.print("Enter Student Mobile : ");
            studentMobile = sc.nextLine().trim();

            if (!student.getMobile().equals(studentMobile)) {

                System.out.println("Student Mobile Not Found In Database.");

                continue;
            }

            break;
        }

        // ==========================================
        // COURSES
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.getAllCourses();

        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (int i = 0; i < courses.size(); i++) {

            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        int courseChoice;

        while (true) {

            System.out.print("Enter Course Choice : ");

            if (!sc.hasNextInt()) {

                System.out.println("Enter Number Only.");
                sc.nextLine();
                continue;
            }

            courseChoice = sc.nextInt();
            sc.nextLine();

            if (courseChoice >= 1 && courseChoice <= courses.size()) {

                break;
            }

            System.out.println("Course Not Found.");
        }

        Course selectedCourse = courses.get(courseChoice - 1);

        // ==========================================
        // DATE
        // ==========================================

        LocalDate attendanceDate;

        while (true) {
            System.out.print("Enter Attendance Date (yyyy-MM-dd) : ");

            String date = sc.nextLine().trim();
            try {
                attendanceDate = LocalDate.parse(date);
                break;

            } catch (Exception e) {

                System.out.println("Invalid Date Format. Use yyyy-MM-dd.");
            }
        }

        // ==========================================
        // FIND ATTENDANCE
        // ==========================================

        Attendance attendance = attendanceDAO.searchAttendanceForDelete(studentName, selectedCourse.getCourseName(), attendanceDate);

        if (attendance == null) {
            System.out.println("\nAttendance Not Found In Database.");

            return;
        }

        // ==========================================
        // DISPLAY
        // ==========================================

        System.out.println("\n========== ATTENDANCE DETAILS ==========");

        attendance.displayAttendance();

        // ==========================================
        // CONFIRM DELETE
        // ==========================================
        System.out.print("\nAre You Sure You Want To Delete? (Y/N) : ");

        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            attendanceDAO.deleteAttendance(studentName, selectedCourse.getCourseName(), attendanceDate);

        } else {

            System.out.println("Delete Cancelled.");
        }
    }

    // ==========================================
    // DATE
    // ==========================================
    private LocalDate readDate() {
        while (true) {
            System.out.print("Enter Attendance Date (yyyy-MM-dd) : ");
            String value = sc.nextLine();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date Format.");
                System.out.println("Use yyyy-MM-dd.");
            }
        }
    }
}