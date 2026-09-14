import java.sql.*;
import java.util.ArrayList;

public class ResultDAO {

    private Connection con;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public ResultDAO() {
        con = DatabaseConnection.getConnection();
    }


    // ==========================================
    // ADD RESULT
    // ==========================================

    public void addResult(Result r) {

        try {

            String sql =
                    "INSERT INTO result " +
                            "(studentName, studentEmail, studentMobile, courseName, " +
                            "totalQuestions, correctAnswers, wrongAnswers, marks, " +
                            "totalMarks, percentage, result, resultDate) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, r.getStudentName());
            ps.setString(2, r.getStudentEmail());
            ps.setString(3, r.getStudentMobile());
            ps.setString(4, r.getCourseName());
            ps.setInt(5, r.getTotalQuestions());
            ps.setInt(6, r.getCorrectAnswers());
            ps.setInt(7, r.getWrongAnswers());
            ps.setInt(8, r.getMarks());
            ps.setInt(9, r.getTotalMarks());
            ps.setDouble(10, r.getPercentage());
            ps.setString(11, r.getResult());
            ps.setDate(12, new java.sql.Date(System.currentTimeMillis()));

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Result Saved Successfully.");
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // ==========================================
    // VIEW ALL RESULTS
    // ==========================================

    public ArrayList<Result> getAllResults() {

        ArrayList<Result> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM result " + "ORDER BY resultId";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = createResult(rs);

                list.add(r);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
        return list;
    }


    private Result createResult(ResultSet rs)
            throws SQLException {

        return new Result(
                rs.getInt("resultId"),rs.getString("studentName"), rs.getString("studentEmail"), rs.getString("studentMobile"),
                rs.getString("courseName"),rs.getInt("totalQuestions"), rs.getInt("correctAnswers"), rs.getInt("wrongAnswers"),
                rs.getInt("marks"),rs.getInt("totalMarks"), rs.getDouble("percentage"), rs.getString("result"),
                rs.getString("resultDate"));
    }

    public ArrayList<Result> getStudentResults(String name, String email, String mobile) {

        ArrayList<Result> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM result " + "WHERE TRIM(LOWER(studentName)) = TRIM(LOWER(?)) " + "AND TRIM(LOWER(studentEmail)) = TRIM(LOWER(?)) "
                    +"AND TRIM(studentMobile) = TRIM(?) " + "ORDER BY resultId";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Result r = createResult(rs);
                list.add(r);
            }

            rs.close();ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }

    public ArrayList<Result> getCourseResults(String courseName) {
        ArrayList<Result> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM result " + "WHERE TRIM(LOWER(courseName)) = TRIM(LOWER(?)) " + "ORDER BY percentage DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, courseName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = createResult(rs);
                list.add(r);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Result> searchResult(String value) {
        ArrayList<Result> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM result " + "WHERE LOWER(TRIM(studentName)) = LOWER(TRIM(?)) " +
                    "OR LOWER(TRIM(studentEmail)) = LOWER(TRIM(?)) " + "OR TRIM(studentMobile) = TRIM(?) "
                    + "OR LOWER(TRIM(courseName)) = LOWER(TRIM(?)) " + "ORDER BY resultId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = createResult(rs);
                list.add(r);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }

    public ArrayList<Result> getTopperList() {

        ArrayList<Result> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM result " + "ORDER BY percentage DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createResult(rs));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return list;
    }


    public void deleteResult(int resultId) {

        try {

            String sql = "DELETE FROM result " + "WHERE resultId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, resultId);
            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Result Deleted Successfully.");

            } else {
                System.out.println("Result Not Found.");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public Result getResultById(int resultId) {
        Result r = null;
        try {
            String sql = "SELECT * FROM result " +"WHERE resultId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, resultId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r = createResult(rs);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return r;
    }
    public void deleteStudentResults(String name, String email, String mobile) {
        try {
            String sql = "DELETE FROM result " + "WHERE studentName=? " + "AND studentEmail=? " + "AND studentMobile=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println(row + " Result(s) Deleted Successfully.");
            } else {
                System.out.println("Result Not Found.");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
    // ==========================================
// COMBINED STUDENT + COURSE RESULT
// ==========================================

    public Result getCombinedCourseResult(String courseName) {

        Result result = null;

        String sql =
                "SELECT " +
                        "courseName, " +
                        "SUM(totalQuestions) AS totalQuestions, " +
                        "SUM(correctAnswers) AS correctAnswers, " +
                        "SUM(wrongAnswers) AS wrongAnswers, " +
                        "SUM(marks) AS marks, " +
                        "SUM(totalMarks) AS totalMarks " +
                        "FROM result " +
                        "WHERE LOWER(courseName) = LOWER(?) " +
                        "GROUP BY courseName";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int totalQuestions = rs.getInt("totalQuestions");

                int correctAnswers = rs.getInt("correctAnswers");

                int wrongAnswers = rs.getInt("wrongAnswers");

                int marks = rs.getInt("marks");

                int totalMarks = rs.getInt("totalMarks");

                double percentage = 0;

                if (totalMarks > 0) {

                    percentage = (marks * 100.0) / totalMarks;
                }

                String resultStatus;

                if (percentage >= 40) {

                    resultStatus = "PASS";

                } else {

                    resultStatus = "FAIL";
                }
                result = new Result(0, "ALL STUDENTS", "", "", rs.getString("courseName"),
                        totalQuestions, correctAnswers, wrongAnswers, marks, totalMarks, percentage, resultStatus, "");
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return result;
    }
    public int getTotalStudentsForCourse(String courseName) {

        int count = 0;

        String sql = "SELECT COUNT(DISTINCT studentEmail) " + "FROM result " + "WHERE LOWER(courseName) = LOWER(?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseName);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                count = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return count;
    }


    // ==========================================
// GET COMBINED STUDENT RESULT
// ==========================================

    public Result getCombinedStudentResult(String name, String email, String mobile) {

        Result combinedResult = null;

        try {

            String sql =
                    "SELECT " +
                            "studentName, " +
                            "studentEmail, " +
                            "studentMobile, " +
                            "SUM(totalQuestions) AS totalQuestions, " +
                            "SUM(correctAnswers) AS correctAnswers, " +
                            "SUM(wrongAnswers) AS wrongAnswers, " +
                            "SUM(marks) AS marks, " +
                            "SUM(totalMarks) AS totalMarks " +
                            "FROM result " +
                            "WHERE LOWER(TRIM(studentName)) = LOWER(TRIM(?)) " +
                            "AND LOWER(TRIM(studentEmail)) = LOWER(TRIM(?)) " +
                            "AND TRIM(studentMobile) = TRIM(?) " +
                            "GROUP BY studentName, studentEmail, studentMobile";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int totalQuestions = rs.getInt("totalQuestions");
                int correctAnswers = rs.getInt("correctAnswers");

                int wrongAnswers = rs.getInt("wrongAnswers");

                int marks = rs.getInt("marks");

                int totalMarks = rs.getInt("totalMarks");
                double percentage = 0;

                if (totalMarks > 0) {

                    percentage = (marks * 100.0) / totalMarks;
                }

                String resultStatus;

                if (percentage >= 40) {
                    resultStatus = "PASS";
                } else {
                    resultStatus = "FAIL";
                }
                combinedResult = new Result(0, rs.getString("studentName"), rs.getString("studentEmail"),
                        rs.getString("studentMobile"), "ALL COURSES", totalQuestions, correctAnswers, wrongAnswers, marks,
                        totalMarks, percentage, resultStatus, "");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return combinedResult;
    }
}