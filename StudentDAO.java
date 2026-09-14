import java.sql.*;
import java.util.*;

public class StudentDAO {

    Connection con = DatabaseConnection.getConnection();


    // =========================================================
    // ADD STUDENT
    // =========================================================

// ADD STUDENT
// =====================================================

    public void addStudent(Student s) {

        try {

            // ==========================
            // CHECK EMAIL
            // ==========================

            String emailSQL = "SELECT studentId FROM student WHERE email=?";

            PreparedStatement emailPS = con.prepareStatement(emailSQL);

            emailPS.setString(1, s.getEmail());

            ResultSet emailRS = emailPS.executeQuery();

            if (emailRS.next()) {

                System.out.println("Student Email Already Exists In Database.");

                emailRS.close();
                emailPS.close();

                return;
            }

            emailRS.close();
            emailPS.close();


            // ==========================
            // CHECK MOBILE
            // ==========================

            String mobileSQL = "SELECT studentId FROM student WHERE mobile=?";

            PreparedStatement mobilePS = con.prepareStatement(mobileSQL);

            mobilePS.setString(1, s.getMobile());

            ResultSet mobileRS = mobilePS.executeQuery();

            if (mobileRS.next()) {

                System.out.println("Student Mobile Already Exists In Database.");
                mobileRS.close();
                mobilePS.close();

                return;
            }

            mobileRS.close();
            mobilePS.close();


            // ==========================
            // INSERT STUDENT
            // ==========================

            String sql = "INSERT INTO student " + "(name, email, password, mobile, course, courseFees, paymentStatus) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getMobile());
            ps.setString(5, s.getCourse());
            ps.setDouble(6, s.getCourseFees());
            ps.setString(7, s.getPaymentStatus());
            int row = ps.executeUpdate();


            // ==========================
            // SUCCESS
            // ==========================

            if (row > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    int studentId = keys.getInt(1);
                    System.out.println("\n======================================");
                    System.out.println("Student Added Successfully.");
                    System.out.println("======================================");
                    System.out.println("Student ID : " + studentId);
                    System.out.println("Name       : " + s.getName());
                    System.out.println("Email      : " + s.getEmail());
                    System.out.println("Mobile     : " + s.getMobile());
                    System.out.println("Course     : " + s.getCourse());
                    System.out.println("Fees       : ₹" + s.getCourseFees());
                    System.out.println("Payment    : " + s.getPaymentStatus());
                }

                keys.close();
            }

            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }


    // =========================================================
    // DISPLAY ALL STUDENTS
    // =========================================================

    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM student";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Student s = new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                                rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                                rs.getDouble("courseFees"), rs.getString("paymentStatus"));

                list.add(s);
            }

            rs.close();
            st.close();


        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // =========================================================
    // SEARCH STUDENT BY NAME / EMAIL / MOBILE
    // =========================================================

    public ArrayList<Student> searchStudent(String value) {

        ArrayList<Student> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM student " + "WHERE name=? OR email=? OR mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Student s = new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));
                list.add(s);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // =========================================================
    // SEARCH STUDENT BY NAME + EMAIL + MOBILE
    // WITH INDIVIDUAL ERROR MESSAGE
    // =========================================================
    public Student searchStudent1(String name, String email, String mobile) {

        try {
            // -------------------------
            // CHECK NAME
            // -------------------------
            PreparedStatement namePS = con.prepareStatement("SELECT * FROM student WHERE name=?");
            namePS.setString(1, name);

            ResultSet nameRS = namePS.executeQuery();

            if (!nameRS.next()) {
                System.out.println("Student Name Not Found In Database.");

                nameRS.close();
                namePS.close();

                return null;
            }

            nameRS.close();
            namePS.close();


            // -------------------------
            // CHECK EMAIL
            // -------------------------
            PreparedStatement emailPS = con.prepareStatement("SELECT * FROM student WHERE email=?");
            emailPS.setString(1, email);
            ResultSet emailRS = emailPS.executeQuery();
            if (!emailRS.next()) {

                System.out.println("Student Email Not Found In Database.");

                emailRS.close();
                emailPS.close();

                return null;
            }

            emailRS.close();
            emailPS.close();


            // -------------------------
            // CHECK MOBILE
            // -------------------------

            PreparedStatement mobilePS = con.prepareStatement("SELECT * FROM student WHERE mobile=?");

            mobilePS.setString(1, mobile);

            ResultSet mobileRS = mobilePS.executeQuery();

            if (!mobileRS.next()) {

                System.out.println("Student Mobile Number Not Found In Database.");

                mobileRS.close();
                mobilePS.close();

                return null;
            }

            mobileRS.close();
            mobilePS.close();


            // -------------------------
            // CHECK COMPLETE STUDENT
            // -------------------------

            String sql = "SELECT * FROM student " + "WHERE name=? AND email=? AND mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Student s = new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));
                System.out.println("\nStudent Found Successfully.");
                System.out.println("================================");
                System.out.println("Student ID : " + s.getStudentId());
                System.out.println("Name       : " + s.getName());
                System.out.println("Email      : " + s.getEmail());
                System.out.println("Mobile     : " + s.getMobile());
                System.out.println("Course     : " + s.getCourse());
                System.out.println("Course Fees: ₹" + s.getCourseFees());
                System.out.println("Payment    : " + s.getPaymentStatus());
                System.out.println("================================");


                rs.close();
                ps.close();

                return s;
            }


            // Name/email/mobile individually exist,
            // but they belong to different students.
            System.out.println("Student Not Found.");
            System.out.println("Name, Email and Mobile do not belong " + "to the same student.");

            rs.close();
            ps.close();


        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }


    // =========================================================
    // UPDATE STUDENT
    // =========================================================
    public boolean emailExists(String email, String oldEmail) {

        try {
            String sql = "SELECT studentId FROM student " + "WHERE email=? AND email<>?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, oldEmail);

            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();

            return exists;

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }
    public void updateStudent(Student s, int studentId) {

        try {

            // =========================================
            // CHECK COURSE
            // =========================================
            PreparedStatement coursePS = con.prepareStatement("SELECT fees FROM course WHERE courseName=?");
            coursePS.setString(1, s.getCourse());

            ResultSet courseRS = coursePS.executeQuery();


            if (!courseRS.next()) {

                System.out.println("Course Not Found In Database.");

                courseRS.close();
                coursePS.close();

                return;
            }


            double courseFees = courseRS.getDouble("fees");


            courseRS.close();
            coursePS.close();


            // =========================================
            // UPDATE STUDENT
            // =========================================
            String sql = "UPDATE student SET " + "name=?, " + "email=?, " + "mobile=?, " + "course=?, " + "courseFees=? " + "WHERE studentId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getMobile());
            ps.setString(4, s.getCourse());
            ps.setDouble(5, courseFees);
            ps.setInt(6, studentId);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("\nStudent Updated Successfully.");
                System.out.println("Course Fees : ₹" + courseFees);

            } else {
                System.out.println("Student Not Found In Database.");
            }


            ps.close();


        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }


    // =========================================================
    // DELETE STUDENT
    // =========================================================

    // ==========================
// DELETE STUDENT
// ==========================

    public void deleteStudent(int studentId) {

        try {

            // Delete student
            String sql = "DELETE FROM student WHERE studentId=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            int row = ps.executeUpdate();

            ps.close();


            if (row > 0) {

                // Delete login record also
                String loginSQL = "DELETE FROM login WHERE id=?";

                PreparedStatement loginPS = con.prepareStatement(loginSQL);

                loginPS.setInt(1, studentId);

                loginPS.executeUpdate();

                loginPS.close();

                System.out.println("Student Deleted Successfully.");

            } else {

                System.out.println("Student Not Found In Database.");
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }


    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

    public void changePassword(int studentId, String newPassword) {

        try {

            // -------------------------
            // UPDATE STUDENT TABLE
            // -------------------------
            String sql1 = "UPDATE student SET password=? " + "WHERE studentId=?";

            PreparedStatement ps1 = con.prepareStatement(sql1);

            ps1.setString(1, newPassword);
            ps1.setInt(2, studentId);

            int row1 = ps1.executeUpdate();


            // -------------------------
            // UPDATE LOGIN TABLE
            // -------------------------
            String sql2 = "UPDATE login SET password=? " + "WHERE id=?";
            PreparedStatement ps2 = con.prepareStatement(sql2);

            ps2.setString(1, newPassword);
            ps2.setInt(2, studentId);

            int row2 = ps2.executeUpdate();


            if (row1 > 0 && row2 > 0) {

                System.out.println("Password Changed Successfully.");

            } else {

                System.out.println("Student Login Details Not Found.");
            }


            ps1.close();
            ps2.close();


        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }


    // =========================================================
    // VIEW ALL STUDENTS
    // =========================================================

    public ArrayList<Student> viewStudents() {

        ArrayList<Student> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM student";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);


            while (rs.next()) {

                Student s = new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));

                list.add(s);
            }


            rs.close();
            st.close();


        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // =========================================================
    // SEARCH STUDENT BY ID
    // =========================================================

    public Student searchStudentById(int studentId) {

        try {

            String sql = "SELECT * FROM student " + "WHERE studentId=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                Student s = new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));

                rs.close();
                ps.close();

                return s;
            }


            System.out.println("Student ID Not Found.");

            rs.close();
            ps.close();


        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }
    public Student searchStudentByName(String name) {

        try {

            String sql = "SELECT * FROM student WHERE name=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }


    public Student searchStudentByEmail(String email) {

        try {

            String sql = "SELECT * FROM student WHERE email=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }


    public Student searchStudentByMobile(String mobile) {

        try {

            String sql = "SELECT * FROM student WHERE mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mobile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(rs.getInt("studentId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("course"),
                        rs.getDouble("courseFees"), rs.getString("paymentStatus"));
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }
    // =====================================================
// CHECK EMAIL EXISTS
// =====================================================

    public boolean isEmailExists(String email) {

        try {

            String sql = "SELECT studentId FROM student WHERE email=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();

            return exists;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }


// =====================================================
// CHECK MOBILE EXISTS
// =====================================================

    public boolean isMobileExists(String mobile) {

        try {

            String sql = "SELECT studentId FROM student WHERE mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, mobile);

            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();

            rs.close();
            ps.close();

            return exists;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }
    // =====================================================
// CHECK PASSWORD EXISTS
// =====================================================

    public boolean isPasswordExists(String password) {

        try {

            String sql = "SELECT studentId FROM student WHERE password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, password);

            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            rs.close();
            ps.close();

            return exists;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }
    // ==========================================
// CHECK CURRENT PASSWORD
// ==========================================

    public boolean checkPassword(String email, String mobile, String password) {

        try {

            String sql = "SELECT studentId FROM student " + "WHERE LOWER(TRIM(email)) = LOWER(TRIM(?)) " + "AND TRIM(mobile) = TRIM(?) " + "AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, mobile);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();

            boolean valid = rs.next();

            rs.close();
            ps.close();

            return valid;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }
    // ==========================================
// CHANGE PASSWORD
// ==========================================

    public boolean changePassword(String email, String mobile, String newPassword) {

        try {

            String sql = "UPDATE student " + "SET password=? " + "WHERE LOWER(TRIM(email)) = LOWER(TRIM(?)) " + "AND TRIM(mobile) = TRIM(?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.setString(3, mobile);

            int row = ps.executeUpdate();

            ps.close();

            return row > 0;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }
    // ==========================================
// CHECK EMAIL + MOBILE BELONG TO SAME STUDENT
// ==========================================

    public boolean isStudentEmailMobileMatch(String email, String mobile) {

        try {

            String sql = "SELECT studentId FROM student " + "WHERE LOWER(TRIM(email)) = LOWER(TRIM(?)) " + "AND TRIM(mobile) = TRIM(?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, mobile);

            ResultSet rs = ps.executeQuery();

            boolean match = rs.next();

            rs.close();
            ps.close();

            return match;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }
}
