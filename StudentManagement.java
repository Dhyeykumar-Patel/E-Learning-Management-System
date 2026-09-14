import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManagement {

    Scanner sc = new Scanner(System.in);
    StudentDAO dao = new StudentDAO();

    // ==========================
    // STUDENT MANAGEMENT MENU
    // ==========================

    public void studentMenu1() {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("      STUDENT MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }

        }

    }

    // ==========================
    // ADD STUDENT
    // ==========================

    public void addStudent() {

        Student s = new Student();

        System.out.print("Enter Name : ");
        s.setName(sc.nextLine());

        // ==========================
// EMAIL
// ==========================

        String email;

        while (true) {

            System.out.print("Enter Email : ");

            email = sc.nextLine().trim().toLowerCase();

            // ==========================
            // CHECK EMAIL DOMAIN
            // ==========================

            if (!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com") || email.endsWith("@zmail.com"))) {

                System.out.println("Email must end with @gmail.com, @yahoo.com, @outlook.com or @zmail.com");

                continue;
            }

            // ==========================
            // GET USERNAME
            // ==========================

            String username = email.substring(0, email.indexOf("@"));

            // ==========================
            // CHECK USERNAME
            // ==========================

            boolean valid = true;

            for (int i = 0; i < username.length(); i++) {

                char ch = username.charAt(i);
                if (!(Character.isLetterOrDigit(ch) || ch == '.' || ch == '_')) {

                    valid = false;
                    break;
                }
            }

            if (!valid || username.length() == 0) {

                System.out.println("Invalid Email!");

                continue;
            }

            // ==========================
            // CHECK DATABASE
            // ==========================

            if (dao.isEmailExists(email)) {

                System.out.println("Email Already Exists In Database.");

                continue;
            }

            break;
        }

        s.setEmail(email);


        // ==========================
// PASSWORD
// ==========================

        String password;

        while (true) {

            System.out.print("Enter Password : ");

            password = sc.nextLine().trim();

            boolean valid = true;

            if (password.isEmpty()) {

                System.out.println("Password Cannot Be Empty.");

                continue;
            }

            for (int i = 0; i < password.length(); i++) {

                if (!Character.isDigit(password.charAt(i))) {

                    valid = false;
                    break;
                }
            }

            if (!valid) {

                System.out.println("Password must contain only numbers.");

                continue;
            }

            // CHECK DATABASE
            if (dao.isPasswordExists(password)) {

                System.out.println("Student Password Already Exists In Database.");

                continue;
            }

            break;
        }

        s.setPassword(password);
        // ==========================
// MOBILE
// ==========================

        String mobile;

        while (true) {

            System.out.print("Enter Mobile Number : ");

            mobile = sc.nextLine().trim();

            // Check 10 digits
            if (mobile.length() != 10) {

                System.out.println("Mobile Number must be exactly 10 digits and contain only numbers.");

                continue;
            }

            boolean valid = true;

            for (int i = 0; i < mobile.length(); i++) {

                if (!Character.isDigit(mobile.charAt(i))) {

                    valid = false;
                    break;
                }
            }

            if (!valid) {

                System.out.println("Mobile Number must be exactly 10 digits and contain only numbers.");

                continue;
            }

            // CHECK DATABASE
            if (dao.isMobileExists(mobile)) {

                System.out.println("Student Mobile Number Already Exists In Database.");

                continue;
            }

            break;
        }

        s.setMobile(mobile);

        // ==========================
// COURSE
// ==========================

        CourseDAO courseDAO = new CourseDAO();

        courseDAO.displayCourseList();

        Course selectedCourse;

        while (true) {

            System.out.print("Enter Course Name : ");

            String courseName = sc.nextLine().trim();

            selectedCourse = courseDAO.searchCourseByName(courseName);

            if (selectedCourse == null) {
                System.out.println("Course Not Found.");
                continue;
            }
            System.out.println("Selected Course : " + selectedCourse.getCourseName());

            // Set course name in Student
            s.setCourse(selectedCourse.getCourseName());

            // Set course fees in Student
            s.setCourseFees(selectedCourse.getFees());

            break;
        }

// ==========================
// PAYMENT STATUS
// ==========================

        String paymentStatus;

        while (true) {
            System.out.print("Enter Payment Status (Paid/Pending) : ");
            paymentStatus = sc.nextLine().trim();
            if (paymentStatus.equalsIgnoreCase("Paid")) {
                paymentStatus = "Paid";
                break;
            } else if (paymentStatus.equalsIgnoreCase("Pending")) {
                paymentStatus = "Pending";
                break;
            } else {
                System.out.println("Please enter Paid or Pending only.");
            }
        }

        s.setPaymentStatus(paymentStatus);
// ==========================
// ADD STUDENT
// ==========================

        dao.addStudent(s);

    }

    // ==========================
    // VIEW STUDENTS
    // ==========================

    public void viewStudents() {

        if (dao.getAllStudents().isEmpty()) {

            System.out.println("No Student Found.");
            return;

        }

        for (Student s : dao.getAllStudents()) {

            System.out.println("--------------------------------");
            System.out.println("Student ID : " + s.getStudentId());
            System.out.println("Name       : " + s.getName());
            System.out.println("Email      : " + s.getEmail());
            System.out.println("Password   : " + s.getPassword());
            System.out.println("Mobile     : " + s.getMobile());
            System.out.println("Course     : " + s.getCourse());

        }

    }

    // ==========================
    // SEARCH STUDENT
    // ==========================

    public void searchStudent() {

        Student studentByName = null;
        Student studentByEmail = null;
        Student studentByMobile = null;

        String name;
        String email;
        String mobile;


        // =========================================
        // STUDENT NAME
        // KEEP ASKING UNTIL NAME IS FOUND
        // =========================================

        while (studentByName == null) {

            System.out.print("Enter Student Name : ");
            name = sc.nextLine();

            studentByName = dao.searchStudentByName(name);

            if (studentByName == null) {
                System.out.println("Student Not Found In Database.");
                System.out.println("Please Enter Student Name Again.\n");
            }
        }


        // =========================================
        // EMAIL
        // KEEP ASKING UNTIL EMAIL IS FOUND
        // =========================================

        while (studentByEmail == null) {

            System.out.print("Enter Email : ");
            email = sc.nextLine();

            studentByEmail = dao.searchStudentByEmail(email);

            if (studentByEmail == null) {
                System.out.println("Student Email Not Found In Database.");
                System.out.println("Please Enter Email Again.\n");
            }
        }


        // =========================================
        // MOBILE
        // KEEP ASKING UNTIL MOBILE IS FOUND
        // =========================================

        while (studentByMobile == null) {
            System.out.print("Enter Mobile : ");
            mobile = sc.nextLine();
            studentByMobile = dao.searchStudentByMobile(mobile);

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
        // STUDENT FOUND
        // =========================================
        System.out.println("\nStudent Found Successfully.");
        System.out.println("================================");

        System.out.println("Student ID : " + studentByName.getStudentId());

        System.out.println("Name       : " + studentByName.getName());

        System.out.println("Email      : " + studentByName.getEmail());

        System.out.println("Mobile     : " + studentByName.getMobile());

        System.out.println("Course     : " + studentByName.getCourse());

        System.out.println("================================");
    }

    // ==========================
    // UPDATE STUDENT
    // ==========================

    public void updateStudent() {

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

            studentByName = dao.searchStudentByName(name);

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

            studentByEmail = dao.searchStudentByEmail(email);

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

            studentByMobile = dao.searchStudentByMobile(mobile);

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

            if (dao.emailExists(newEmail, studentByName.getEmail())) {

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

        Student updatedStudent = new Student(studentByName.getStudentId(), newName, newEmail, studentByName.getPassword(), newMobile,
                newCourse, studentByName.getCourseFees(), studentByName.getPaymentStatus());


        // =========================================
        // UPDATE
        // =========================================

        dao.updateStudent(updatedStudent, studentByName.getStudentId());
    }

    // ==========================
    // DELETE STUDENT
    // ==========================
    public void deleteStudent() {

        Student studentByName = null;
        Student studentByEmail = null;
        Student studentByMobile = null;

        String name;
        String email;
        String mobile;


        // =========================================
        // ENTER STUDENT NAME
        // =========================================

        while (studentByName == null) {
            System.out.print("Enter Student Name : ");
            name = sc.nextLine();
            studentByName = dao.searchStudentByName(name);

            if (studentByName == null) {
                System.out.println("Student Not Found In Database.");
                System.out.println("Please Enter Student Name Again.\n");
            }
        }


        // =========================================
        // ENTER EMAIL
        // =========================================

        while (studentByEmail == null) {
            System.out.print("Enter Email : ");
            email = sc.nextLine();
            studentByEmail = dao.searchStudentByEmail(email);

            if (studentByEmail == null) {
                System.out.println("Student Email Not Found In Database.");
                System.out.println("Please Enter Email Again.\n");
            }
        }


        // =========================================
        // ENTER MOBILE
        // =========================================

        while (studentByMobile == null) {

            System.out.print("Enter Mobile : ");
            mobile = sc.nextLine();

            studentByMobile = dao.searchStudentByMobile(mobile);

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
        // CONFIRM DELETE
        // =========================================

        System.out.println("\nStudent Found.");
        System.out.println("Name   : " + studentByName.getName());
        System.out.println("Email  : " + studentByName.getEmail());
        System.out.println("Mobile : " + studentByName.getMobile());
        System.out.print("\nAre you sure you want to delete? (Y/N) : ");

        String choice = sc.nextLine();


        if (choice.equalsIgnoreCase("Y")) {

            dao.deleteStudent(studentByName.getStudentId());

        } else {

            System.out.println("Delete Cancelled.");
        }
    }


}