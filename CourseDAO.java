import java.sql.*;
import java.util.ArrayList;

public class CourseDAO {

    private Connection con;

    // ==========================
    // CONSTRUCTOR
    // ==========================

    public CourseDAO() {

        con = DatabaseConnection.getConnection();

    }
    // ==========================
// DISPLAY COURSE LIST
// ==========================
    public ArrayList<String> getCourseNames() {

        ArrayList<String> courses = new ArrayList<>();

        try {

            String sql = "SELECT courseName FROM course ORDER BY courseId";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                courses.add(rs.getString("courseName"));

            }

            rs.close();
            st.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return courses;
    }
    public void displayCourseList() {

        try {

            String sql = "SELECT courseId, courseName, fees FROM course";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n======================================");
            System.out.println("          AVAILABLE COURSES");
            System.out.println("======================================");

            while (rs.next()) {
                System.out.println("ID : " + rs.getInt("courseId") + " | Course : " + rs.getString("courseName") + " | Fees : ₹" +
                        rs.getDouble("fees"));
            }

            rs.close();
            st.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }
    // ==========================
// GET COURSE NAME
// ==========================

    public String getCourseName(String courseName) {

        String name = null;

        try {

            String sql = "SELECT courseName FROM course WHERE courseName=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("courseName");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return name;
    }
    // ==========================
    // GET TEACHER NAME
    // ==========================

    public String getTeacherName(int teacherId) {

        String teacherName = null;

        try {

            String sql = "SELECT name FROM teacher WHERE teacherId=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, teacherId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                teacherName = rs.getString("name");

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return teacherName;
    }

    // ==========================
    // ADD COURSE
    // ==========================

    public void addCourse(Course c) {

        try {

            // CHECK COURSE NAME
            PreparedStatement check = con.prepareStatement("SELECT * FROM course WHERE courseName=?");

            check.setString(1, c.getCourseName());

            ResultSet rs = check.executeQuery();

            if (rs.next()) {

                System.out.println("Course already exists.");

                rs.close();
                check.close();

                return;
            }

            rs.close();
            check.close();

            // CHECK TEACHER
            String teacherName = getTeacherName(c.getTeacherId());

            if (teacherName == null) {

                System.out.println("Teacher ID not found.");

                return;
            }

            // INSERT COURSE

            String sql = "INSERT INTO course " + "(courseName,duration,fees,teacherId," + "teacherName,totalChapters,notes) " + "VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getDuration());
            ps.setDouble(3, c.getFees());
            ps.setInt(4, c.getTeacherId());
            ps.setString(5, teacherName);
            ps.setInt(6, c.getTotalChapters());
            ps.setString(7, c.getNotes());

            int row = ps.executeUpdate();

            if (row > 0) {

                System.out.println("\nCourse Added Successfully.");
                System.out.println("Course Name  : " + c.getCourseName());
                System.out.println("Teacher Name : " + teacherName);
                System.out.println("Course Fees  : ₹" + c.getFees());
            }

            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    // ==========================
    // DISPLAY ALL COURSES
    // ==========================

    public ArrayList<Course> getAllCourses() {

        ArrayList<Course> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM course ORDER BY courseId";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Course c = new Course(rs.getInt("courseId"), rs.getString("courseName"), rs.getString("duration"),
                        rs.getDouble("fees"), rs.getInt("teacherId"), rs.getString("teacherName"),
                        rs.getInt("totalChapters"), rs.getString("notes"));

                list.add(c);
            }

            rs.close();
            st.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return list;
    }

    // ==========================
    // SEARCH COURSE
    // ==========================

    public ArrayList<Course> searchCourse(String value) {

        ArrayList<Course> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM course " + "WHERE courseName=? " + "OR teacherName=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, value);
            ps.setString(2, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Course c = new Course(rs.getInt("courseId"), rs.getString("courseName"), rs.getString("duration"),
                        rs.getDouble("fees"), rs.getInt("teacherId"), rs.getString("teacherName"),
                        rs.getInt("totalChapters"), rs.getString("notes"));

                list.add(c);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return list;
    }

    // ==========================
    // FIND COURSE
    // ==========================

    public Course findCourse(String courseName) {

        Course c = null;

        try {

            String sql = "SELECT * FROM course WHERE courseName=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                c = new Course(rs.getInt("courseId"), rs.getString("courseName"), rs.getString("duration"),
                        rs.getDouble("fees"), rs.getInt("teacherId"), rs.getString("teacherName"),
                        rs.getInt("totalChapters"), rs.getString("notes"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

        return c;
    }

    // ==========================
    // UPDATE COURSE
    // ==========================

    public void updateCourse(Course c) {

        try {

            String teacherName = getTeacherName(c.getTeacherId());

            if (teacherName == null) {

                System.out.println("Teacher ID not found.");

                return;
            }

            String sql = "UPDATE course SET " + "courseName=?, " + "duration=?, " + "fees=?, " + "teacherId=?, " + "teacherName=?, " +
                    "totalChapters=?, " + "notes=? " + "WHERE courseId=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getDuration());
            ps.setDouble(3, c.getFees());
            ps.setInt(4, c.getTeacherId());
            ps.setString(5, teacherName);
            ps.setInt(6, c.getTotalChapters());
            ps.setString(7, c.getNotes());
            ps.setInt(8, c.getCourseId());

            int row =
                    ps.executeUpdate();

            if (row > 0) {

                System.out.println("Course Updated Successfully.");

            } else {

                System.out.println("Course Not Found.");
            }

            ps.close();

        } catch (Exception e) {

            System.out.println(
                    "Error : " + e.getMessage());

        }
    }

    // ==========================
    // DELETE COURSE
    // ==========================
    public void deleteCourse(String courseName) {

        try {

            String sql = "DELETE FROM course WHERE courseName=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);

            int row = ps.executeUpdate();

            if (row > 0) {

                System.out.println("Course Deleted Successfully.");

            } else {

                System.out.println("Course Not Found.");

            }

            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }
    public Course searchCourseById(int courseId) {

        try {

            String sql = "SELECT * FROM course WHERE courseId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("courseId"));
                c.setCourseName(rs.getString("courseName"));
                c.setDuration(rs.getString("duration"));
                c.setTotalChapters(rs.getInt("totalChapters"));
                c.setTeacherName(rs.getString("teacherName"));
                c.setFees(rs.getDouble("fees"));

                rs.close();
                ps.close();

                return c;
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }
     public Course searchCourseByName(String courseName) {

        Course course = null;

        try {

            String sql = "SELECT * FROM course " + "WHERE LOWER(TRIM(courseName)) = LOWER(TRIM(?))";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                course = new Course(rs.getInt("courseId"),rs.getString("courseName"), rs.getString("duration"),
                        rs.getDouble("fees"), rs.getInt("teacherId"), rs.getString("teacherName"),
                        rs.getInt("totalChapters"), rs.getString("notes"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return course;
    }
}