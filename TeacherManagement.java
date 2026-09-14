import java.util.ArrayList;
import java.util.Scanner;

class TeacherManagement {

    private Scanner sc;
    private TeacherDAO teacherDAO;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public TeacherManagement() {

        sc = new Scanner(System.in);

        teacherDAO = new TeacherDAO();
    }


    // =====================================================
    // TEACHER MENU
    // =====================================================

    public void teacherMenu() {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("          TEACHER MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Teacher");
            System.out.println("2. View Teachers");
            System.out.println("3. Search Teacher");
            System.out.println("4. Update Teacher");
            System.out.println("5. Delete Teacher");
            System.out.println("6. Back");
            System.out.print("Enter Choice : ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid Choice. Enter number only.");
                sc.nextLine();
                continue;
            }
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

        // =================================================
        // NAME
        // =================================================

        String name;

        while (true) {
            System.out.print("Enter Name : ");
            name = sc.nextLine().trim();

            if (!name.isEmpty()) {
                break;
            }

            System.out.println("Name cannot be empty.");
        }


        // =================================================
        // EMAIL
        // =================================================


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

            if (teacherDAO.isEmailExists(email)) {

                System.out.println("Email Already Exists In Database.");

                continue;
            }

            break;
        }
        // =================================================
        // PASSWORD
        // =================================================



        String password;

        while (true) {

            System.out.print("Enter Password : ");
            password = sc.nextLine().trim();

            if (password.isEmpty()) {

                System.out.println("Password Cannot Be Empty.");

                continue;
            }

            boolean valid = true;

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

            // ==========================
            // CHECK DATABASE
            // ==========================

            if (teacherDAO.isPasswordExists(password)) {

                System.out.println("Teacher Password Already Exists In Database.");

                continue;
            }

            break;
        }



        // =================================================
        // MOBILE
        // =================================================

        String mobile;

        while (true) {
            System.out.print("Enter Mobile Number : ");
            mobile =sc.nextLine().trim();


            if (!isValidMobile(mobile)) {
                System.out.println("Mobile Number must be exactly " + "10 digits and contain only numbers.");
                continue;
            }
            if (teacherDAO.mobileExists(mobile, 0)) {
                System.out.println("Mobile Number already exists.");

                continue;
            }

            break;
        }


        // =================================================
        // SUBJECT
        // =================================================

        String subject;

        while (true) {

            System.out.print("Enter Subject : ");
            subject = sc.nextLine().trim();
            if (!subject.isEmpty()) {

                break;
            }

            System.out.println("Subject cannot be empty.");
        }


        // =================================================
        // SALARY
        // =================================================

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


        // =================================================
        // CREATE TEACHER
        // =================================================

        Teacher t = new Teacher(0, name, email, password, mobile, subject, salary);


        teacherDAO.addTeacher(t);
    }


    // =====================================================
    // VIEW TEACHERS
    // =====================================================

    public void viewTeachers() {

        ArrayList<Teacher> list = teacherDAO.getAllTeachers();
        if (list.isEmpty()) {
            System.out.println("No Teachers Found.");
            return;
        }
        System.out.println("\n========== ALL TEACHERS ==========");


        for (Teacher t : list) {

            t.displayTeacher();
        }
    }


    // =====================================================
    // SEARCH TEACHER
    // =====================================================

    public void searchTeacher() {

        while (true) {

            System.out.print("\nEnter Teacher Name OR Email OR Mobile : ");
            String value = sc.nextLine().trim();
            ArrayList<Teacher> list = teacherDAO.searchTeacher(value);


            if (list.isEmpty()) {
                System.out.println("Teacher Not Found.");
                System.out.println("Please Enter Again.");

                continue;
            }


            for (Teacher t : list) {

                t.displayTeacher();
            }

            break;
        }
    }


    // =====================================================
    // UPDATE TEACHER
    // =====================================================

    public void updateTeacher() {

        System.out.println("\n========== FIND TEACHER ==========");


        // =================================================
        // FIND TEACHER
        // NAME
        // =================================================


        String oldName;

        while (true) {

            System.out.print("Enter Old Name : ");
            oldName = sc.nextLine().trim();
            if (teacherDAO.teacherNameExists(oldName)) {
                break;
            }
            System.out.println("Teacher Name Not Found In Database.");
            System.out.println("Please Enter Teacher Name Again.\n");
        }
// =====================================================
// FIND TEACHER - EMAIL
// =====================================================
        String oldEmail;
        while (true) {
            System.out.print("Enter Old Email : ");
            oldEmail = sc.nextLine().trim();
            if (teacherDAO.teacherNameEmailExists(oldName, oldEmail)) {
                break;
            }
            System.out.println("Teacher Email Not Found In Database.");
            System.out.println("Please Enter Email Again.\n");
        }


// =====================================================
// FIND TEACHER - MOBILE
// =====================================================

        String oldMobile;

        while (true) {

            System.out.print("Enter Old Mobile : ");
            oldMobile = sc.nextLine().trim();

            if (teacherDAO.teacherDetailsExists(oldName, oldEmail, oldMobile)) {

                break;
            }

            System.out.println("Teacher Mobile Not Found In Database.");

            System.out.println("Please Enter Mobile Again.\n");
        }


// =====================================================
// GET TEACHER
// =====================================================

        Teacher t = teacherDAO.searchTeacher1(oldName, oldEmail, oldMobile);

        System.out.println("\nTeacher Found Successfully.");


        // =================================================
        // NEW NAME// =================================================
        System.out.print("\nNew Name : ");
        String newName = sc.nextLine().trim();
        while (newName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            System.out.print("New Name : ");

            newName = sc.nextLine().trim();}

        t.setName(newName);


        // =================================================
        // NEW EMAIL
        // KEEP ASKING IF DUPLICATE
        // =================================================

        String email;

        while (true) {
            System.out.print("Enter New Email : ");
            email = sc.nextLine().trim().toLowerCase();
            if (!isValidEmail(email)) {
                System.out.println("Email must end with @yahoo.com, @email.com, @zmail.com or @outlook.com");continue;}
            if (teacherDAO.emailExists(email, t.getTeacherId())) {
                System.out.println("Email Already Exists In Database.");
                System.out.println("Please Enter Email Again.\n");
                continue;
            }
            break;
        }

        t.setEmail(email);

        // =================================================
        // NEW PASSWORD
        // =================================================
        String password;

        while (true) {
            System.out.print("Enter New Password : ");
            password = sc.nextLine();

            if (isNumeric(password) && password.length() > 0 && password.length() <= 10) {
                break;

            } else {

                System.out.println("Password must contain only numbers " + "and maximum 10 characters.");
            }
        }


        t.setPassword(password);


        // =================================================
        // NEW MOBILE
        // KEEP ASKING IF DUPLICATE
        // =================================================

        String mobile;

        while (true) {

            System.out.print("Enter New Mobile Number : ");
            mobile = sc.nextLine().trim();
            if (!isValidMobile(mobile)) {
                System.out.println("Mobile Number must be exactly " + "10 digits and contain only numbers.");
                continue;
            }
            if (teacherDAO.mobileExists(mobile, t.getTeacherId())) {
                System.out.println("Mobile Number Already Exists In Database.");
                System.out.println("Please Enter Mobile Again.\n");
                continue;
            }
            break;
        }
        t.setMobile(mobile);
        // =================================================
        // SUBJECT
        // =================================================
        String subject;
        while (true) {
            System.out.print("New Subject : ");
            subject = sc.nextLine().trim();
            if (!subject.isEmpty()) {
                break;
            }
            System.out.println("Subject cannot be empty.");
        }
        t.setSubject(subject);


        // =================================================
        // SALARY
        // =================================================
        double salary;

        while (true) {
            System.out.print("New Salary : ");
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


        t.setSalary(salary);


        // =================================================
        // UPDATE
        // =================================================

        teacherDAO.updateTeacher(t, oldName, oldEmail, oldMobile);
    }


    // =====================================================
    // DELETE TEACHER
    // =====================================================




        public void deleteTeacher() {
            System.out.println("\n========== DELETE TEACHER ==========");


            // =================================================
            // 1. ENTER TEACHER NAME
            // =================================================

            String name;
            while (true) {
                System.out.print("Enter Teacher Name : ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Teacher Name Cannot Be Empty.");
                    continue;
                }
                if (teacherDAO.teacherNameExists(name)) {
                    break;

                } else {
                    System.out.println("Teacher Name Not Found In Database.");
                    System.out.println("Please Enter Teacher Name Again.\n");
                }
            }


            // =================================================
            // 2. ENTER EMAIL
            // =================================================

            String email;

            while (true) {

                System.out.print("Enter Email : ");
                email = sc.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("Email Cannot Be Empty.");continue;
                }
                if (teacherDAO.teacherNameEmailExists(name, email)) {
                    break;

                } else {
                    System.out.println("Teacher Email Not Found In Database.");
                    System.out.println("Please Enter Email Again.\n");
                }
            }


            // =================================================
            // 3. ENTER MOBILE
            // =================================================

            String mobile;
            while (true) {

                System.out.print("Enter Mobile : ");
                mobile = sc.nextLine().trim();
                if (mobile.isEmpty()) {
                    System.out.println("Mobile Cannot Be Empty.");
                    continue;
                }
                if (teacherDAO.teacherDetailsExists(name, email, mobile)) {
                    break;
                } else {
                    System.out.println("Teacher Mobile Not Found In Database.");
                    System.out.println("Please Enter Mobile Again.\n");
                }
            }


            // =================================================
            // 4. FIND TEACHER
            // =================================================

            Teacher t = teacherDAO.searchTeacher1(name, email, mobile);

            if (t == null) {
                System.out.println("Teacher Details Not Found.");
                return;
            }


            // =================================================
            // 5. DISPLAY TEACHER
            // =================================================
            System.out.println("\n========== TEACHER FOUND ==========");
            t.displayTeacher();


            // =================================================
            // 6. CONFIRM DELETE
            // =================================================

            while (true) {

                System.out.print("\nAre You Sure You Want To Delete This Teacher? (Y/N) : ");

                String choice = sc.nextLine().trim();


                // =================================================
                // YES
                // =================================================
                if (choice.equalsIgnoreCase("Y")) {
                    teacherDAO.deleteTeacher(name, email, mobile);

                    break;
                }


                // =================================================
                // NO
                // =================================================

                else if (choice.equalsIgnoreCase("N")) {

                    System.out.println("Delete Cancelled.");

                    break;
                }


                // =================================================
                // INVALID CHOICE
                // =================================================

                else {

                    System.out.println("Invalid Choice.");

                    System.out.println("Please Enter Y or N.");
                }
            }
        }




    // =====================================================
    // EMAIL VALIDATION
    // =====================================================

    private boolean isValidEmail(
            String email) {
        if (email == null || !email.endsWith("@gmail.com")) {return false;}
        int atIndex = email.indexOf("@");
        if (atIndex <= 0) {
            return false;
        }
        String username = email.substring(0, atIndex);
        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);


            if (!(Character.isLetterOrDigit(ch) || ch == '.' || ch == '_')) {

                return false;
            }
        }


        return true;
    }


    // =====================================================
    // MOBILE VALIDATION
    // =====================================================

    private boolean isValidMobile(String mobile) {
        if (mobile == null || mobile.length() != 10) {
            return false;
        }
        for (int i = 0; i < mobile.length(); i++) {
            if (!Character.isDigit(mobile.charAt(i))) {
                return false;
            }
        }


        return true;
    }


    // =====================================================
    // NUMBER VALIDATION
    // =====================================================

    private boolean isNumeric(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }


        for (int i = 0; i < value.length(); i++) {

            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
