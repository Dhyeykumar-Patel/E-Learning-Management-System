import java.sql.*;
import java.util.ArrayList;

public class QuizDAO {

    private Connection con;

    public QuizDAO() {
        con = DatabaseConnection.getConnection();
    }


    // ==========================================
    // ADD QUIZ
    // ==========================================


    // ==========================================
// ADD QUIZ
// ==========================================

    public void addQuiz(Quiz q) {

        try {

            String sql = "INSERT INTO quiz " + "(courseName, question, optionA, optionB, " +
                    "optionC, optionD, correctAnswer, marks) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, q.getCourseName());
            ps.setString(2, q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, q.getCorrectAnswer());
            ps.setInt(8, q.getMarks());
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Quiz Added Successfully.");
            } else {
                System.out.println("Quiz Not Added.");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // ==========================================
    // GET ALL QUIZZES
    // ==========================================

    public ArrayList<Quiz> getAllQuizzes() {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quiz";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Quiz q = createQuiz(rs);
                list.add(q);
            }

            rs.close();
            st.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // ==========================================
    // SEARCH QUIZ
    // ==========================================

    public ArrayList<Quiz> searchQuiz(String value) {
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quiz " + "WHERE courseName LIKE ? " + "OR question LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            String search = "%" + value + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createQuiz(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return list;
    }


    // ==========================================
    // GET QUIZ BY COURSE
    // ==========================================
    public ArrayList<Quiz> getQuizByCourse(String courseName) {
        ArrayList<Quiz> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM quiz " + "WHERE courseName=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(createQuiz(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return list;
    }


    // ==========================================
    // UPDATE QUIZ
    // ==========================================

    public void updateQuiz(Quiz q) {

        try {

            String sql = "UPDATE quiz SET " + "courseName=?, " + "question=?, " + "optionA=?, " + "optionB=?, " + "optionC=?, "
                    + "optionD=?, " + "correctAnswer=?, " + "marks=? " + "WHERE quizId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, q.getCourseName());
            ps.setString(2,q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, q.getCorrectAnswer());
            ps.setInt(8, q.getMarks());
            ps.setInt(9, q.getQuizId());
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Quiz Updated Successfully.");
            } else {
                System.out.println("Quiz Not Found.");
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // ==========================================
    // DELETE QUIZ
    // ==========================================

    public void deleteQuiz(int quizId) {
        try {
            String sql = "DELETE FROM quiz " + "WHERE quizId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quizId);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Quiz Deleted Successfully.");

            } else {
                System.out.println("Quiz Not Found.");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    // ==========================================
    // CREATE OBJECT
    // ==========================================

    private Quiz createQuiz(ResultSet rs) throws SQLException {

        return new Quiz(rs.getInt("quizId"), rs.getString("courseName"), rs.getString("question"),
                rs.getString("optionA"), rs.getString("optionB"), rs.getString("optionC"),
                rs.getString("optionD"), rs.getString("correctAnswer"), rs.getInt("marks"));
    }

}