import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceDAO {

    private Connection con;

    public AttendanceDAO() {
        con = DatabaseConnection.getConnection();
    }

    // ==========================================
    // ADD / MARK ATTENDANCE
    // ==========================================

    public void markAttendance(Attendance attendance) {

        try {

            // ==========================================
            // GET COURSE ID FROM COURSE NAME
            // ==========================================

            String courseSql = "SELECT courseId " + "FROM course " + "WHERE TRIM(LOWER(courseName)) = TRIM(LOWER(?))";

            PreparedStatement coursePs = con.prepareStatement(courseSql);

            coursePs.setString(1, attendance.getCourseName());

            ResultSet courseRs = coursePs.executeQuery();

            int courseId = 0;

            if (courseRs.next()) {

                courseId = courseRs.getInt("courseId");

            } else {

                System.out.println("Course Not Found.");

                courseRs.close();
                coursePs.close();

                return;
            }

            courseRs.close();
            coursePs.close();


            // ==========================================
            // INSERT ATTENDANCE
            // ==========================================

            String sql = "INSERT INTO attendance " + "(studentName, studentEmail, studentMobile, " + "courseId, courseName, teacherName, "
                    + "attendanceDate, status) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, attendance.getStudentName());
            ps.setString(2, attendance.getStudentEmail());
            ps.setString(3, attendance.getStudentMobile());

            ps.setInt(4, courseId);

            ps.setString(5, attendance.getCourseName());

            ps.setString(6, attendance.getTeacherName());

            ps.setDate(7, java.sql.Date.valueOf(attendance.getAttendanceDate()));

            ps.setString(8, attendance.getStatus());


            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Attendance Marked Successfully.");
            }

            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // ==========================================
    // VIEW ALL ATTENDANCE
    // ==========================================

    public ArrayList<Attendance> getAllAttendance() {

        ArrayList<Attendance> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM attendance " + "ORDER BY attendanceId";

            Statement st =con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                list.add(createAttendance(rs));
            }

            rs.close();
            st.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }

    // ==========================================
    // SEARCH
    // Name OR Email OR Mobile OR Course OR Teacher
    // ==========================================

    public ArrayList<Attendance> searchAttendance(
            String value) {

        ArrayList<Attendance> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM attendance " + "WHERE studentName=? " + "OR studentEmail=? " + "OR studentMobile=? " + "OR courseName=? " + "OR teacherName=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ps.setString(5, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(createAttendance(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }

    // ==========================================
    // SEARCH BY ATTENDANCE ID
    // ==========================================
    public Attendance searchAttendance1(String studentName, String studentEmail, String studentMobile, String courseName, LocalDate attendanceDate) {

        Attendance attendance = null;

        try {

            String sql = "SELECT attendanceId, attendanceDate, status, " + "studentName, studentEmail, studentMobile, " + "courseName, teacherName " +
            "FROM attendance " + "WHERE studentName=? " + "AND studentEmail=? " + "AND studentMobile=? " + "AND courseName=? " + "AND attendanceDate=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, studentName);
            ps.setString(2, studentEmail);
            ps.setString(3, studentMobile);
            ps.setString(4, courseName);
            ps.setDate(5, java.sql.Date.valueOf(attendanceDate));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                attendance = new Attendance(
                        rs.getInt("attendanceId"),
                        rs.getDate("attendanceDate").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("studentName"),
                        rs.getString("studentEmail"),
                        rs.getString("studentMobile"),
                        rs.getString("courseName"),
                        rs.getString("teacherName")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return attendance;
    }




// =====================================================
// UPDATE ATTENDANCE
// WITHOUT STUDENT ID AND COURSE ID
// =====================================================
public void updateAttendance(int attendanceId, LocalDate attendanceDate, String status) {

    try {

        String sql = "UPDATE attendance SET " + "attendanceDate=?, " + "status=? " + "WHERE attendanceId=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDate(1, java.sql.Date.valueOf(attendanceDate));

        ps.setString(2, status);

        ps.setInt(3, attendanceId);

        int row = ps.executeUpdate();

        if (row > 0) {

            System.out.println("Attendance Updated Successfully.");

        } else {

            System.out.println("Attendance Not Found.");
        }

        ps.close();

    } catch (Exception e) {
        System.out.println("Error : " + e.getMessage());
    }
}

    // ==========================================
    // DELETE
    // ==========================================

    // =====================================================
// DELETE ATTENDANCE
// =====================================================

    public void deleteAttendance(String studentName, String courseName, LocalDate attendanceDate) {

        try {

            String sql = "DELETE FROM attendance " + "WHERE LOWER(TRIM(studentName)) = LOWER(TRIM(?)) " + "AND LOWER(TRIM(courseName)) = LOWER(TRIM(?)) " + "AND attendanceDate=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, studentName);
            ps.setString(2, courseName);
            ps.setDate(3, java.sql.Date.valueOf(attendanceDate));

            int row = ps.executeUpdate();

            if (row > 0) {

                System.out.println("Attendance Deleted Successfully.");

            } else {

                System.out.println("Attendance Not Found In Database.");
            }

            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
    // ==========================================
    // CREATE ATTENDANCE OBJECT
    // ==========================================

    private Attendance createAttendance(ResultSet rs) throws SQLException {

        return new Attendance(rs.getInt("attendanceId"), rs.getDate("attendanceDate").toLocalDate(),
                rs.getString("status"), rs.getString("studentName"), rs.getString("studentEmail"),
                rs.getString("studentMobile"), rs.getString("courseName"), rs.getString("teacherName"));
    }
    // =====================================================
// GET STUDENT ID
// =====================================================

    public int getStudentId(String name, String email, String mobile) {

        try {

            String sql = "SELECT studentId FROM student " + "WHERE name=? AND email=? AND mobile=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("studentId");

                rs.close();
                ps.close();

                return id;
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return -1;
    }
    // =====================================================
// CHECK STUDENT NAME
// =====================================================

    public boolean studentNameExists(String name) {

        try {

            String sql = "SELECT studentId FROM student WHERE name=?";

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
// CHECK STUDENT NAME + EMAIL
// =====================================================

    public boolean studentNameEmailExists(String name, String email) {

        try {

            String sql = "SELECT studentId FROM student " + "WHERE name=? AND email=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

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
// CHECK STUDENT NAME + EMAIL + MOBILE
// =====================================================

    public boolean studentDetailsExists(String name, String email, String mobile) {

        try {

            String sql = "SELECT studentId FROM student " + "WHERE name=? AND email=? AND mobile=?";

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
// GET ATTENDANCE BY ID
// =====================================================

    public Attendance getAttendanceById(int attendanceId) {

        Attendance attendance = null;

        try {

            String sql = "SELECT a.attendanceId, " + "a.attendanceDate, " +
                    "a.status, " + "s.name AS studentName, " + "s.email AS studentEmail, " + "s.mobile AS studentMobile, " +
                            "c.courseName AS courseName, " +
                            "t.name AS teacherName " +
                            "FROM attendance a " + "LEFT JOIN student s " + "ON a.studentId = s.studentId " + "LEFT JOIN course c " + "ON a.courseId = c.courseId " +
                            "LEFT JOIN teacher t " + "ON a.teacherId = t.teacherId " +
                            "WHERE a.attendanceId=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, attendanceId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                attendance = new Attendance(rs.getInt("attendanceId"), rs.getDate("attendanceDate").toLocalDate(),
                        rs.getString("status"), rs.getString("studentName"), rs.getString("studentEmail"),
                        rs.getString("studentMobile"), rs.getString("courseName"), rs.getString("teacherName"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return attendance;
    }
    public Attendance searchAttendance(
            String studentName,
            String studentEmail,
            String studentMobile,
            String courseName,
            LocalDate attendanceDate) {

        Attendance attendance = null;

        try {

            String sql = "SELECT a.attendanceId, " + "a.attendanceDate, " + "a.status, " +"s.name AS studentName, " +
                            "s.email AS studentEmail, " + "s.mobile AS studentMobile, " + "c.courseName AS courseName, " + "t.name AS teacherName " +
                            "FROM attendance a " +
                            "JOIN student s ON a.studentId = s.studentId " + "JOIN course c ON a.courseId = c.courseId " + "JOIN teacher t ON a.teacherId = t.teacherId " +
                            "WHERE s.name=? " +
                            "AND s.email=? " + "AND s.mobile=? " + "AND c.courseName=? " + "AND a.attendanceDate=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentName);
            ps.setString(2, studentEmail);
            ps.setString(3, studentMobile);
            ps.setString(4, courseName);
            ps.setDate(5, java.sql.Date.valueOf(attendanceDate));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                attendance = new Attendance(rs.getInt("attendanceId"), rs.getDate("attendanceDate").toLocalDate(),
                        rs.getString("status"), rs.getString("studentName"), rs.getString("studentEmail"),
                        rs.getString("studentMobile"), rs.getString("courseName"), rs.getString("teacherName"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return attendance;
    }
    public Attendance searchAttendanceForDelete(String studentName, String courseName, LocalDate attendanceDate) {

        Attendance attendance = null;

        try {

            String sql = "SELECT attendanceId, attendanceDate, status, " + "studentName, studentEmail, studentMobile, " +
                            "courseName, teacherName " + "FROM attendance " + "WHERE LOWER(TRIM(studentName)) = LOWER(TRIM(?)) " +
                            "AND LOWER(TRIM(courseName)) = LOWER(TRIM(?)) " + "AND attendanceDate = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, studentName);
            ps.setString(2, courseName);
            ps.setDate(3, java.sql.Date.valueOf(attendanceDate));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                attendance = new Attendance(rs.getInt("attendanceId"), rs.getDate("attendanceDate").toLocalDate(),
                        rs.getString("status"), rs.getString("studentName"), rs.getString("studentEmail"),
                        rs.getString("studentMobile"), rs.getString("courseName"), rs.getString("teacherName"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return attendance;
    }
}