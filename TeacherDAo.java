import java.sql.*;
import java.util.ArrayList;

class TeacherDAO {

    private Connection con;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public TeacherDAO() {
        con = DatabaseConnection.getConnection();
    }

    // =====================================================
    // CHECK EMAIL
    // Used while updating teacher
    // =====================================================
    public boolean emailExists(String email, int teacherId) {

        try {
            String sql = "SELECT teacherId FROM teacher " + "WHERE email=? AND teacherId<>?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, teacherId);
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
    // CHECK MOBILE
    // Used while updating teacher
    // =====================================================

    public boolean mobileExists(String mobile, int teacherId) {

        try {
            String sql = "SELECT teacherId FROM teacher " + "WHERE mobile=? AND teacherId<>?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mobile);
            ps.setInt(2, teacherId);
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
    // ADD TEACHER
    // =====================================================

    public void addTeacher(Teacher t) {

        try {
            // -------------------------
            // CHECK EMAIL
            // -------------------------

            PreparedStatement checkEmail = con.prepareStatement("SELECT teacherId FROM teacher " + "WHERE email=?");

            checkEmail.setString(1, t.getEmail());

            ResultSet rs1 = checkEmail.executeQuery();

            if (rs1.next()) {
                System.out.println("Email already exists.");

                rs1.close();
                checkEmail.close();

                return;
            }

            rs1.close();
            checkEmail.close();


            // -------------------------
            // CHECK MOBILE
            // -------------------------

            PreparedStatement checkMobile = con.prepareStatement("SELECT teacherId FROM teacher " + "WHERE mobile=?");
            checkMobile.setString(1, t.getMobile());

            ResultSet rs2 = checkMobile.executeQuery();

            if (rs2.next()) {

                System.out.println("Mobile Number already exists.");

                rs2.close();
                checkMobile.close();

                return;
            }

            rs2.close();
            checkMobile.close();


            // -------------------------
            // INSERT TEACHER
            // -------------------------
            String sql = "INSERT INTO teacher " + "(name,email,password,mobile,subject,salary) " + "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getPassword());
            ps.setString(4, t.getMobile());
            ps.setString(5, t.getSubject());
            ps.setDouble(6, t.getSalary());


            int row = ps.executeUpdate();


            if (row > 0) {

                ResultSet keys = ps.getGeneratedKeys();

                if (keys.next()) {
                    int teacherId = keys.getInt(1);

                    // -------------------------
                    // ADD LOGIN
                    // -------------------------
                    PreparedStatement login = con.prepareStatement("INSERT INTO login" + "(id,password,role)" + " VALUES(?,?,?)");
                    login.setInt(1, teacherId);
                    login.setString(2, t.getPassword());
                    login.setString(3, "teacher");

                    login.executeUpdate();
                    login.close();
                    System.out.println("\n================================");
                    System.out.println("Teacher Added Successfully.");
                    System.out.println("================================");
                    System.out.println("Teacher ID : " + teacherId);
                    System.out.println("Name       : " + t.getName());
                    System.out.println("Subject    : " + t.getSubject());
                    System.out.println("Salary     : ₹" + t.getSalary());
                }
                keys.close();
            }
            ps.close();
        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Teacher ID, Email or Mobile already exists.");

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // =====================================================
    // VIEW ALL TEACHERS
    // =====================================================

    public ArrayList<Teacher> getAllTeachers() {

        ArrayList<Teacher> list = new ArrayList<>();
        try {

            String sql = "SELECT * FROM teacher " + "ORDER BY teacherId";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);


            while (rs.next()) {

                Teacher t = new Teacher(rs.getInt("teacherId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("subject"), rs.getDouble("salary"));

                list.add(t);
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // =====================================================
    // SEARCH TEACHER
    // NAME OR EMAIL OR MOBILE
    // =====================================================

    public ArrayList<Teacher> searchTeacher(
            String value) {

        ArrayList<Teacher> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM teacher " + "WHERE name=? OR email=? OR mobile=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Teacher t = new Teacher(rs.getInt("teacherId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("subject"), rs.getDouble("salary"));

                list.add(t);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // =====================================================
    // SEARCH TEACHER FOR UPDATE / DELETE
    // NAME + EMAIL + MOBILE
    // =====================================================

    public Teacher searchTeacher1(String name, String email, String mobile) {

        Teacher t = null;

        try {

            String sql = "SELECT * FROM teacher " + "WHERE name=? AND email=? AND mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                t = new Teacher(rs.getInt("teacherId"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("mobile"), rs.getString("subject"), rs.getDouble("salary"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return t;
    }


    // =====================================================
    // UPDATE TEACHER
    // =====================================================

    public void updateTeacher(Teacher t, String oldName, String oldEmail, String oldMobile) {

        try {

            // -------------------------
            // CHECK EMAIL
            // -------------------------
            if (emailExists(t.getEmail(), t.getTeacherId())) {
                System.out.println("Email Already Exists In Database.");
                return;
            }


            // -------------------------
            // CHECK MOBILE
            // -------------------------
            if (mobileExists(t.getMobile(), t.getTeacherId())) {

                System.out.println("Mobile Number Already Exists In Database.");

                return;
            }


            // -------------------------
            // UPDATE TEACHER
            // -------------------------

            String sql = "UPDATE teacher SET " + "name=?, email=?, password=?, mobile=?, " + "subject=?, salary=? " + "WHERE name=? AND email=? AND mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getPassword());
            ps.setString(4, t.getMobile());
            ps.setString(5, t.getSubject());
            ps.setDouble(6, t.getSalary());
            ps.setString(7, oldName);
            ps.setString(8, oldEmail);
            ps.setString(9, oldMobile);
            int row = ps.executeUpdate();

            if (row > 0) {

                // -------------------------
                // UPDATE LOGIN
                // -------------------------
                PreparedStatement login = con.prepareStatement("UPDATE login SET " + "password=? WHERE id=?");
                login.setString(1, t.getPassword());
                login.setInt(2, t.getTeacherId());
                login.executeUpdate();

                login.close();
                System.out.println("Teacher Updated Successfully.");

            } else {
                System.out.println("Teacher Not Found.");
            }

            ps.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email or Mobile Already Exists In Database.");

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // =====================================================
    // DELETE TEACHER
    // =====================================================

    public void deleteTeacher(String name, String email, String mobile) {
        try {

            Teacher t = searchTeacher1(name, email, mobile);


            if (t == null) {
                System.out.println("Teacher Not Found.");

                return;
            }


            // -------------------------
            // DELETE LOGIN
            // -------------------------

            PreparedStatement login = con.prepareStatement("DELETE FROM login WHERE id=?");
            login.setInt(1, t.getTeacherId());

            login.executeUpdate();

            login.close();

            // -------------------------
            // DELETE TEACHER
            // -------------------------
            PreparedStatement ps = con.prepareStatement("DELETE FROM teacher " + "WHERE teacherId=?");
            ps.setInt(1, t.getTeacherId());

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Teacher Deleted Successfully.");
            } else {
                System.out.println("Teacher Not Found.");
            }
            ps.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Cannot Delete Teacher Because " + "Teacher Data Is Used In Another Table.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
    // =====================================================
// CHECK TEACHER NAME
// =====================================================

    public boolean teacherNameExists(String name) {

        try {

            String sql = "SELECT teacherId FROM teacher WHERE name=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            boolean found = rs.next();

            rs.close();
            ps.close();

            return found;

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }


// =====================================================
// CHECK NAME + EMAIL
// =====================================================
    public boolean teacherNameEmailExists(String name, String email) {

        try {

            String sql ="SELECT teacherId FROM teacher " + "WHERE name=? AND email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();
            rs.close();
            ps.close();
            return found;

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());

            return false;
        }
    }


// =====================================================
// CHECK NAME + EMAIL + MOBILE
// =====================================================

    public boolean teacherDetailsExists(String name, String email, String mobile) {

        try {
            String sql = "SELECT teacherId FROM teacher " + "WHERE name=? AND email=? AND mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ResultSet rs = ps.executeQuery();

            boolean found = rs.next();

            rs.close();
            ps.close();

            return found;

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

            String sql = "SELECT teacherId FROM teacher WHERE password=?";
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
    // =====================================================
// CHECK TEACHER EMAIL EXISTS
// =====================================================

    public boolean isEmailExists(String email) {

        try {

            String sql = "SELECT teacherId FROM teacher WHERE email=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email.trim());

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
}
