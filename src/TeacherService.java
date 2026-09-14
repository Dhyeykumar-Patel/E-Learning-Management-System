import java.util.ArrayList;
import java.util.Scanner;

class TeacherService {

    private Scanner sc;
    private TeacherDAO teacherDAO;

    // ==========================
    // CONSTRUCTOR
    // ==========================

    public TeacherService() {

        sc = new Scanner(System.in);
        teacherDAO = new TeacherDAO();

    }

    // ==========================
    // TEACHER MENU
    // ==========================

    public void teacherMenu() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("        TEACHER MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Add Teacher");
            System.out.println("2. View Teachers");
            System.out.println("3. Search Teacher");
            System.out.println("4. Update Teacher");
            System.out.println("5. Delete Teacher");
            System.out.println("6. Back");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addTeacher();
                    break;

                case 2:
                    viewTeachers();
                    break;

                case 3:
                    searchTeacher();
                    break;

                case 4:
                    updateTeacher();
                    break;

                case 5:
                    deleteTeacher();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    // =====================================================
    // ADD TEACHER
    // =====================================================

    public void addTeacher() {


        System.out.println("\n========== ADD TEACHER ==========");

        System.out.print("Enter Name : ");
        String name = sc.nextLine();

        // ==========================
        // EMAIL
        // ==========================

        String email;

        while (true) {

            System.out.print("Enter Email : ");
            email = sc.nextLine().trim();

            if (!email.endsWith("@gmail.com")) {
                System.out.println("Email must end with @gmail.com");
                continue;
            }

            String username = email.substring(0, email.indexOf("@"));

            boolean valid = true;

            for (int i = 0; i < username.length(); i++) {

                char ch = username.charAt(i);

                if (!(Character.isLetterOrDigit(ch) || ch == '.' || ch == '_')) {
                    valid = false;
                    break;
                }
            }

            if (valid && username.length() > 0) {
                break;
            }

            System.out.println("Invalid Email!");
        }


        // ==========================
        // PASSWORD
        // 1 TO 10 CHARACTERS
        // ==========================

        String password;

        while (true) {
            System.out.print("Enter Password : ");
            password = sc.nextLine();

            boolean valid = true;

            for (int i = 0; i < password.length(); i++) {

                if (password.charAt(i) < '0' || password.charAt(i) > '9') {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                break;
            } else {
                System.out.println("Password must contain only numbers.");
            }
        }

        // ==========================
        // MOBILE
        // ==========================

        String mobile;

        while (true) {
            System.out.print("Enter Mobile Number : ");
            mobile = sc.nextLine();

            boolean valid1 = true;

            if (mobile.length() != 10) {
                valid1 = false;
            }

            for (int i = 0; i < mobile.length(); i++) {

                if (mobile.charAt(i) < '0' || mobile.charAt(i) > '9') {
                    valid1 = false;
                    break;
                }
            }

            if (valid1) {
                break;
            } else {
                System.out.println("Mobile Number must be exactly 10 digits and contain only numbers.");
            }
        }

        // ==========================
        // SUBJECT
        // ==========================

        System.out.print("Enter Subject : ");
        String subject = sc.nextLine();

        // ==========================
        // SALARY
        // ==========================

        double salary;

        while (true) {

            System.out.print("Enter Salary : ");

            try {

                salary = sc.nextDouble();
                sc.nextLine();

                if (salary > 0) {

                    break;

                } else {

                    System.out.println("Salary must be greater than 0.");
                }

            } catch (Exception e) {

                System.out.println("Enter valid salary.");

                sc.nextLine();
            }
        }

        Teacher t = new Teacher(0, name, email, password, mobile, subject, salary);

        teacherDAO.addTeacher(t);
    }

    // =====================================================
    // VIEW TEACHERS
    // =====================================================

    public void viewTeachers() {

        ArrayList<Teacher> list =
                teacherDAO.getAllTeachers();

        if (list.isEmpty()) {

            System.out.println("No Teachers Found.");
            return;
        }

        System.out.println(
                "\n========== ALL TEACHERS ==========");

        for (Teacher t : list) {

            t.displayTeacher();

        }
    }

    // =====================================================
    // SEARCH TEACHER
    // =====================================================

    public void searchTeacher() {

        System.out.print("Enter Teacher Name / Email / Mobile : ");

        String value = sc.nextLine();

        ArrayList<Teacher> list =
                teacherDAO.searchTeacher(value);

        if (list.isEmpty()) {

            System.out.println("Teacher Not Found.");
            return;
        }

        for (Teacher t : list) {

            t.displayTeacher();

        }
    }

    // =====================================================
    // UPDATE TEACHER
    // =====================================================

    public void updateTeacher() {

        System.out.println(
                "\n========== FIND TEACHER ==========");

        System.out.print("Enter Old Name : ");
        String oldName = sc.nextLine();

        System.out.print("Enter Old Email : ");
        String oldEmail = sc.nextLine();

        System.out.print("Enter Old Mobile : ");
        String oldMobile = sc.nextLine();

        Teacher t = teacherDAO.searchTeacher1(oldName, oldEmail, oldMobile);

        if (t == null) {

            System.out.println("Teacher Not Found.");
            return;
        }

        System.out.println(
                "\nTeacher Found Successfully.");

        // ==========================
        // NEW NAME
        // ==========================

        System.out.print("New Name : ");
        t.setName(sc.nextLine());

        // ==========================
        // NEW EMAIL
        // ==========================

        String email;

        while (true) {
            System.out.print("Enter Email : ");
            email = sc.nextLine().trim();

            if (!email.endsWith("@gmail.com")) {
                System.out.println("Email must end with @gmail.com");
                continue;
            }

            String username = email.substring(0, email.indexOf("@"));

            boolean valid = true;

            for (int i = 0; i < username.length(); i++) {

                char ch = username.charAt(i);

                if (!(Character.isLetterOrDigit(ch) || ch == '.' || ch == '_')) {
                    valid = false;
                    break;
                }
            }

            if (valid && username.length() > 0) {
                break;
            }

            System.out.println("Invalid Email!");
        }

        t.setEmail(email);

        // ==========================
        // PASSWORD
        // ==========================

        String password;

        while (true) {
            System.out.print("Enter Password : ");
            password = sc.nextLine();

            boolean valid = true;

            for (int i = 0; i < password.length(); i++) {

                if (password.charAt(i) < '0' || password.charAt(i) > '9') {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                break;
            } else {
                System.out.println("Password must contain only numbers.");
            }
        }

        t.setPassword(password);

        // ==========================
        // MOBILE
        // ==========================

        String mobile;

        while (true) {
            System.out.print("Enter Mobile Number : ");
            mobile = sc.nextLine();

            boolean valid1 = true;

            if (mobile.length() != 10) {
                valid1 = false;
            }

            for (int i = 0; i < mobile.length(); i++) {

                if (mobile.charAt(i) < '0' || mobile.charAt(i) > '9') {
                    valid1 = false;
                    break;
                }
            }

            if (valid1) {
                break;
            } else {
                System.out.println("Mobile Number must be exactly 10 digits and contain only numbers.");
            }
        }
        t.setMobile(mobile);

        // ==========================
        // SUBJECT
        // ==========================
        System.out.print("New Subject : ");
        t.setSubject(sc.nextLine());

        // ==========================
        // SALARY
        // ==========================

        double salary;

        while (true) {

            System.out.print("New Salary : ");
            try {
                salary = sc.nextDouble();
                sc.nextLine();

                if (salary > 0) {
                    break;

                } else {System.out.println("Salary must be greater than 0.");
                }

            } catch (Exception e) {

                System.out.println("Enter valid salary.");
                sc.nextLine();
            }
        }

        t.setSalary(salary);
        teacherDAO.updateTeacher(t, oldName, oldEmail, oldMobile);
    }

    // =====================================================
    // DELETE TEACHER
    // =====================================================

    public void deleteTeacher() {

        System.out.println("\n========== DELETE TEACHER ==========");
        System.out.print("Enter Teacher Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Email : ");
        String email = sc.nextLine();

        System.out.print("Enter Mobile : ");
        String mobile = sc.nextLine();

        Teacher t = teacherDAO.searchTeacher1(name, email, mobile);

        if (t == null) {

            System.out.println("Teacher Not Found.");
            return;
        }

        System.out.println("\nTeacher Found:");

        t.displayTeacher();

        System.out.print("\nAre you sure? (Y/N) : ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            teacherDAO.deleteTeacher(name, email, mobile);
        } else {
            System.out.println("Delete Cancelled.");
        }
    }
}