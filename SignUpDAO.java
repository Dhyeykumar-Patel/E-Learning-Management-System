import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class SignUpDAO {

    Scanner sc = new Scanner(System.in);

    // ================= STUDENT SIGN UP =================

    public void studentSignUp() {

        try {

            Connection con = DatabaseConnection.getConnection();

            System.out.print("Enter Student ID : ");
            int id = sc.nextInt();
            sc.nextLine();
            // Check duplicate ID
            PreparedStatement check1 = con.prepareStatement("SELECT * FROM student WHERE studentId=?");
            check1.setInt(1, id);

            ResultSet rs1 = check1.executeQuery();

            if (rs1.next()) {
                System.out.println("Student ID already exists.");
                return;
            }

            PreparedStatement check2 = con.prepareStatement(
                    "SELECT * FROM login WHERE id=?");
            check2.setInt(1, id);

            ResultSet rs2 = check2.executeQuery();

            if (rs2.next()) {
                System.out.println("Login ID already exists.");
                return;
            }

            System.out.print("Enter Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Email : ");
            String email = sc.nextLine();

            String password;
            while (true) {

                System.out.print("Create  Password : ");
                password = sc.nextLine();

                boolean valid = true;

                for (int i = 0; i < password.length(); i++) {

                    char ch = password.charAt(i);

                    if (ch < '0' || ch > '9') {

                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Password! Please enter numbers only.");
                }
            }
            String mobile;
            while (true) {

                System.out.print("Enter Mobile Number : ");
                mobile = sc.nextLine();

                boolean valid = true;

                // Check length
                if (mobile.length() != 10) {
                    valid = false;
                }

                // Check that every character is a digit
                for (int i = 0; i < mobile.length(); i++) {

                    char ch = mobile.charAt(i);

                    if (ch < '0' || ch > '9') {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Mobile Number! Please enter exactly 10 digits.");
                }
            }

            System.out.print("Enter Course : ");
            String course = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student(studentId,name,email,password,mobile,course) VALUES(?,?,?,?,?,?)");

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, mobile);
            ps.setString(6, course);

            ps.executeUpdate();

            PreparedStatement login = con.prepareStatement(
                    "INSERT INTO login(id,password,role) VALUES(?,?,?)");

            login.setInt(1, id);
            login.setString(2, password);
            login.setString(3, "student");

            login.executeUpdate();

            System.out.println("\nStudent Registered Successfully.");
            System.out.println("Student ID : " + id);
            System.out.println("Password   : " + password);

            con.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    // ================= TEACHER SIGN UP =================

    public void teacherSignUp() {

        try {

            Connection con = DatabaseConnection.getConnection();

            System.out.print("Enter Teacher ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            PreparedStatement check1 = con.prepareStatement(
                    "SELECT * FROM teacher WHERE teacherId=?");
            check1.setInt(1, id);

            ResultSet rs1 = check1.executeQuery();

            if (rs1.next()) {
                System.out.println("Teacher ID already exists.");
                return;
            }

            PreparedStatement check2 = con.prepareStatement(
                    "SELECT * FROM login WHERE id=?");
            check2.setInt(1, id);

            ResultSet rs2 = check2.executeQuery();

            if (rs2.next()) {
                System.out.println("Login ID already exists.");
                return;
            }

            System.out.print("Enter Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Email : ");
            String email = sc.nextLine();

            String password;
            while (true) {

                System.out.print("Create  Password : ");
                password = sc.nextLine();

                boolean valid = true;

                for (int i = 0; i < password.length(); i++) {

                    char ch = password.charAt(i);

                    if (ch < '0' || ch > '9') {

                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Password! Please enter numbers only.");
                }
            }
            String mobile;
            while (true) {

                System.out.print("Enter Mobile Number : ");
                mobile = sc.nextLine();

                boolean valid = true;

                // Check length
                if (mobile.length() != 10) {
                    valid = false;
                }

                // Check that every character is a digit
                for (int i = 0; i < mobile.length(); i++) {

                    char ch = mobile.charAt(i);

                    if (ch < '0' || ch > '9') {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Mobile Number! Please enter exactly 10 digits.");
                }
            }

            System.out.print("Enter Subject : ");
            String subject = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO teacher VALUES(?,?,?,?,?,?)");

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, mobile);
            ps.setString(6, subject);

            ps.executeUpdate();

            PreparedStatement login = con.prepareStatement(
                    "INSERT INTO login(id,password,role) VALUES(?,?,?)");

            login.setInt(1, id);
            login.setString(2, password);
            login.setString(3, "Teacher");

            login.executeUpdate();

            System.out.println("\nTeacher Registered Successfully.");
            System.out.println("Teacher ID : " + id);
            System.out.println("Password   : " + password);

            con.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    // ================= ADMIN SIGN UP =================

    public void adminSignUp() {
// ================= ADMIN SIGN UP =================


            try {

                Connection con = DatabaseConnection.getConnection();

                System.out.print("Enter Admin ID : ");
                int id = sc.nextInt();
                sc.nextLine();

                // Check duplicate login ID
                PreparedStatement check = con.prepareStatement("SELECT * FROM login WHERE id=?");

                check.setString(1, String.valueOf(id));

                ResultSet rs = check.executeQuery();

                if (rs.next()) {

                    System.out.println("ID Already Exists.");
                    return;

                }

                System.out.print("Enter Name : ");
                String name = sc.nextLine();

                System.out.print("Enter Email : ");
                String email = sc.nextLine();

                String password;

                while (true) {

                    System.out.print("Create Password : ");
                    password = sc.nextLine();

                    if (password.matches("\\d+")) {
                        break;
                    }

                    System.out.println("Password must contain numbers only.");
                }

                String mobile;

                while (true) {

                    System.out.print("Enter Mobile Number : ");
                    mobile = sc.nextLine();

                    if (mobile.matches("\\d{10}")) {
                        break;
                    }

                    System.out.println("Enter a valid 10-digit mobile number.");

                }

                // ================= SAVE ADMIN =================

                PreparedStatement ps = con.prepareStatement("INSERT INTO admin(id,name,email,password,mobile) VALUES(?,?,?,?,?)");

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.setString(5, mobile);

                ps.executeUpdate();

                // ================= SAVE LOGIN =================

                PreparedStatement login = con.prepareStatement("INSERT INTO login(id,password,role) VALUES(?,?,?)");

                login.setString(1, String.valueOf(id));
                login.setString(2, password);
                login.setString(3, "admin");

                login.executeUpdate();

                System.out.println("\n==================================");
                System.out.println("Admin Registered Successfully.");
                System.out.println("==================================");
                System.out.println("Admin ID : " + id);
                System.out.println("Password : " + password);

                rs.close();
                check.close();
                ps.close();
                login.close();
                con.close();

            }

            catch (Exception e) {

                System.out.println(e.getMessage());

            }

    }
}
