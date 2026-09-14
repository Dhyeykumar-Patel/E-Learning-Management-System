
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentService {

    private Scanner sc;
    private StudentDAO studentDAO;
    private CourseService courseService;
    private ResultService resultService;
    private QuizService quizService;
    private CourseDAO courseDAO;

    // Logged-in Student ID
    private int studentId;

    // ==========================
    // Constructor
    // ==========================

    public StudentService() {

        sc = new Scanner(System.in);
        studentDAO = new StudentDAO();
        courseService = new CourseService();
        resultService = new ResultService();
        quizService=new QuizService();
        courseDAO=new CourseDAO();
    }

    public StudentService(int studentId) {

        sc = new Scanner(System.in);
        studentDAO = new StudentDAO();
        courseService = new CourseService();
        resultService = new ResultService();
        quizService=new QuizService();
        courseDAO=new CourseDAO();
        this.studentId = studentId;

    }

    // ==========================
    // STUDENT MENU
    // ==========================

    public void studentMenu() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("         STUDENT SERVICE");
            System.out.println("======================================");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Display All Courses");
            System.out.println("4. View Result");
            System.out.println("5. Attempt Quiz");
            System.out.println("6. Search Course & Read Notes");
            System.out.println("7. Change Password");
            System.out.println("8. Logout");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewStudents();
                    break;

                case 2:
                    updateProfile();
                    break;

                case 3:
                    courseService.displayCourse();
                    break;

                case 4:
                    resultService.viewStudentResults();
                    break;
                case 5:
                    attemptQuiz();
                    break;
                case 6 :
                    courseManagement();
                    break;
                case 7 :
                    changePassword();
                    break;
                case 8:

                    System.out.println("Logout Successfully.");

                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ==========================
// VIEW PROFILE
// ==========================

    public void viewStudents() {

        ArrayList<Student> list = studentDAO.viewStudents();

        if (list.isEmpty()) {

            System.out.println("No Student Found.");
            return;

        }

        for (Student s : list) {

            System.out.println("\n===============================");
            System.out.println("Student ID : " + s.getStudentId());
            System.out.println("Name       : " + s.getName());
            System.out.println("Email      : " + s.getEmail());
            System.out.println("Mobile     : " + s.getMobile());
            System.out.println("Course     : " + s.getCourse());
            System.out.println("===============================");

        }

    }


// ==========================
// UPDATE PROFILE
// ==========================

    public void updateProfile() {

        Student studentByName = null;
        Student studentByEmail = null;
        Student studentByMobile = null;

        String name;
        String email;
        String mobile;


        // =========================================
        // NAME
        // =========================================

        while (studentByName == null) {

            System.out.print("Enter Student Name : ");
            name = sc.nextLine();

            studentByName = studentDAO.searchStudentByName(name);
            if (studentByName == null) {
                System.out.println("Student Not Found In Database.");
                System.out.println("Please Enter Student Name Again.\n");
            }
        }


        // =========================================
        // EMAIL
        // =========================================

        while (studentByEmail == null) {

            System.out.print("Enter Email : ");
            email = sc.nextLine();
            studentByEmail = studentDAO.searchStudentByEmail(email);
            if (studentByEmail == null) {
                System.out.println("Student Email Not Found In Database.");
                System.out.println("Please Enter Email Again.\n");
            }
        }


        // =========================================
        // MOBILE
        // =========================================

        while (studentByMobile == null) {

            System.out.print("Enter Mobile : ");
            mobile = sc.nextLine();

            studentByMobile = studentDAO.searchStudentByMobile(mobile);
            if (studentByMobile == null) {
                System.out.println("Student Mobile Not Found In Database.");
                System.out.println("Please Enter Mobile Again.\n");
            }
        }


        // =========================================
        // CHECK SAME STUDENT
        // =========================================
        if (studentByName.getStudentId() != studentByEmail.getStudentId()) {
            System.out.println("Email does not belong to this student.");

            return;
        }

        if (studentByName.getStudentId() != studentByMobile.getStudentId()) {
            System.out.println("Mobile does not belong to this student.");
            return;
        }
        // =========================================
        // SHOW CURRENT DETAILS
        // =========================================
        System.out.println("\n========== CURRENT DETAILS ==========");
        System.out.println("Student ID : " + studentByName.getStudentId());
        System.out.println("Name       : " + studentByName.getName());
        System.out.println("Email      : " + studentByName.getEmail());
        System.out.println("Mobile     : " + studentByName.getMobile());
        System.out.println("Course     : " + studentByName.getCourse());
        System.out.println("=====================================");


        // =========================================
        // ENTER NEW DETAILS
        // =========================================
        System.out.println("\n========== UPDATE DETAILS ==========");
        System.out.print("Enter New Name : ");

        String newName = sc.nextLine();

// =========================================
// ENTER NEW EMAIL
// KEEP ASKING UNTIL EMAIL IS AVAILABLE
// =========================================

        String newEmail;
        while (true) {

            System.out.print("Enter New Email : ");
            newEmail = sc.nextLine().trim().toLowerCase();

            // ==========================
            // CHECK EMAIL DOMAIN
            // ==========================

            if (!(newEmail.endsWith("@gmail.com") || newEmail.endsWith("@yahoo.com") || newEmail.endsWith("@outlook.com") || newEmail.endsWith("@zmail.com"))) {
                System.out.println("Email must end with @gmail.com, @yahoo.com, @outlook.com or @zmail.com");

                continue;
            }

            // ==========================
            // CHECK EMAIL EXISTS
            // ==========================
            if (studentDAO.emailExists(newEmail, studentByName.getEmail())) {
                System.out.println("Email Already Exists In Database.");
                System.out.println("Please Enter Email Again.\n");
            } else {
                // Email is available
                break;
            }
        }
        studentByName.setEmail(newEmail);
        System.out.print("Enter New Mobile : ");
        String newMobile = sc.nextLine();
        CourseDAO courseDAO = new CourseDAO();
        String newCourse;
        // =========================================
// SHOW COURSES FROM DATABASE
// =========================================

        ArrayList<Course> courses = courseDAO.getAllCourses();
        System.out.println("\n========== AVAILABLE COURSES ==========");
        for (Course c : courses) {
            System.out.println("ID: " + c.getCourseId() + " | Course: " + c.getCourseName() + " | Fees: ₹" + c.getFees());
        }
        System.out.println("=======================================");
        int courseId;
        while (true) {
            System.out.print("Enter Course ID : ");
            try {
                courseId = sc.nextInt();
                sc.nextLine();
                Course selectedCourse = courseDAO.searchCourseById(courseId);

                if (selectedCourse != null) {
                    newCourse = selectedCourse.getCourseName();
                    break;

                } else {
                    System.out.println("Course Not Found In Database.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid Course ID. Enter number only.");
                sc.nextLine();
            }
        }
        // =========================================
        // CREATE UPDATED STUDENT
        // =========================================

        Student updatedStudent = new Student(studentByName.getStudentId(), newName, newEmail, studentByName.getPassword(),
                newMobile, newCourse, studentByName.getCourseFees(), studentByName.getPaymentStatus());
        // =========================================
        // UPDATE
        // =========================================

        studentDAO.updateStudent(updatedStudent, studentByName.getStudentId());
    }

    public void courseManagement() {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("      COURSE MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Search Course");
            System.out.println("2. Read Course Notes");
            System.out.println("3. Back");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    courseService.searchCourse();
                    break;
                case 2:
                    System.out.print("Enter Course Name : ");
                    String courseName = sc.nextLine().trim();

                    Course course = courseDAO.searchCourseByName(courseName);

                    if (course == null) {
                        System.out.println("Course Not Found.");
                        return;
                    }
                    System.out.println("Selected Course : " + course.getCourseName());
                    courseService.readCourseNotes(courseName);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
// ==========================
// ATTEMPT QUIZ
// ==========================

    public void attemptQuiz() {
        QuizService quiz=new QuizService();
        quiz.startQuiz();
    }


// ==========================
// VIEW RESULT
// ==========================


    public void changePassword() {
        System.out.println("\n========== CHANGE PASSWORD ==========");

        // ==========================================
        // EMAIL
        // ==========================================
        String email;
        while (true) {
            System.out.print("Enter Email : ");
            email = sc.nextLine().trim().toLowerCase();
            if (!studentDAO.isEmailExists(email)) {
                System.out.println("Email Not Found In Database.");

                continue;
            }
            break;
        }
        // ==========================================
        // MOBILE
        // ==========================================
        String mobile;
        while (true) {
            System.out.print("Enter Mobile Number : ");
            mobile = sc.nextLine().trim();
            if (!studentDAO.isMobileExists(mobile)) {
                System.out.println("Mobile Number Not Found In Database.");
                continue;
            }
            break;
        }
        // ==========================================
        // CHECK EMAIL + MOBILE BELONG TO SAME STUDENT
        // ==========================================
        if (!studentDAO.isStudentEmailMobileMatch(email, mobile)) {
            System.out.println("Email and Mobile Number do not belong to the same student.");
            return;
        }
        // ==========================================
        // CURRENT PASSWORD
        // ==========================================
        String currentPassword;
        while (true) {
            System.out.print("Enter Current Password : ");
            currentPassword = sc.nextLine();
            if (!studentDAO.checkPassword(email, mobile, currentPassword)) {
                System.out.println("Current Password is Incorrect.");
                continue;
            }
            break;
        }
        // ==========================================
        // NEW PASSWORD
        // ==========================================
        String newPassword;
        while (true) {
            System.out.print("Enter New Password : ");
            newPassword = sc.nextLine();
            if (newPassword.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }
            boolean valid = true;
            for (int i = 0; i < newPassword.length(); i++) {
                char ch = newPassword.charAt(i);
                if (ch < '0' || ch > '9') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Password must contain only numbers.");
                continue;
            }
            if (newPassword.equals(currentPassword)) {
                System.out.println("New Password must be different from Current Password.");
                continue;
            }
            break;
        }
        // ==========================================
        // CONFIRM PASSWORD
        // ==========================================
        String confirmPassword;
        while (true) {

            System.out.print("Confirm New Password : ");
            confirmPassword = sc.nextLine();
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Password does not match.");
                continue;
            }
            break;
        }

        // ==========================================
        // UPDATE PASSWORD
        // ==========================================
        boolean changed = studentDAO.changePassword(email, mobile, newPassword);

        if (changed) {
            System.out.println("\nPassword Changed Successfully.");

        } else {
            System.out.println("\nPassword Change Failed.");
        }
    }
    public void viewResult() {
        System.out.println("\n========== MY RESULT ==========");
        resultService.viewStudentResults();
    }


// ==========================
// LOGOUT
// ==========================
    public void logout() {
        System.out.println("\nLogout Successfully.");
    }
}
