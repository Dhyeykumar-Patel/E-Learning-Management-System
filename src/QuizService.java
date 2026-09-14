import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizService {

    private Scanner sc;
    private QuizDAO quizDAO;

    public QuizService() {
        sc = new Scanner(System.in);
        quizDAO = new QuizDAO();
    }


    // ==========================================
    // QUIZ MENU
    // ==========================================
    public void quizMenu() {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("          QUIZ MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Quiz");
            System.out.println("2. Display Quiz");
            System.out.println("3. Search Quiz");
            System.out.println("4. Update Quiz");
            System.out.println("5. Delete Quiz");
            System.out.println("6. Back");
            System.out.print("\nEnter Choice : ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");

                sc.nextLine();

                continue;
            }

            int choice =
                    sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:
                    addQuiz();
                    break;

                case 2:
                    displayQuiz();
                    break;

                case 3:
                    searchQuiz();
                    break;

                case 4:
                    updateQuiz();
                    break;

                case 5:
                    deleteQuiz();
                    break;

                case 6:
                    return;
                default: System.out.println("Invalid Choice.");
            }
        }
    }


    // ==========================================
    // ADD QUIZ
    // ==========================================

    // ==========================================
// ADD QUIZ
// ==========================================

    public void addQuiz() {

        System.out.println("\n========== ADD QUIZ ==========");

        // ==========================================
        // SHOW COURSES
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No Courses Available.");
            return;
        }

        System.out.println("\n========== AVAILABLE COURSES ==========");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        // ==========================================
        // COURSE CHOICE
        // ==========================================

        int courseChoice;

        while (true) {
            System.out.print("Enter Course Choice : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");
                sc.nextLine();
                continue;
            }
            courseChoice = sc.nextInt();
            sc.nextLine();
            if (courseChoice >= 1 && courseChoice <= courses.size()) {
                break;
            }
            System.out.println("Invalid Course Choice.");
        }
        Course selectedCourse = courses.get(courseChoice - 1);
        String courseName = selectedCourse.getCourseName();
        System.out.println("Selected Course : " + courseName);


        // ==========================================
        // QUESTION
        // ==========================================

        String question;

        while (true) {
            System.out.print("\nEnter Question : ");
            question = sc.nextLine().trim();

            if (!question.isEmpty()) {
                break;
            }
            System.out.println("Question Cannot Be Empty.");
        }


        // ==========================================
        // OPTION A
        // ==========================================

        String optionA;

        while (true) {

            System.out.print("Enter Option A : ");
            optionA = sc.nextLine().trim();

            if (!optionA.isEmpty()) {
                break;
            }
            System.out.println("Option A Cannot Be Empty.");
        }


        // ==========================================
        // OPTION B
        // ==========================================

        String optionB;
        while (true) {
            System.out.print("Enter Option B : ");

            optionB = sc.nextLine().trim();

            if (!optionB.isEmpty()) {
                break;
            }
            System.out.println("Option B Cannot Be Empty.");
        }


        // ==========================================
        // OPTION C
        // ==========================================

        String optionC;

        while (true) {
            System.out.print("Enter Option C : ");
            optionC = sc.nextLine().trim();
            if (!optionC.isEmpty()) {
                break;
            }
            System.out.println("Option C Cannot Be Empty.");
        }


        // ==========================================
        // OPTION D
        // ==========================================

        String optionD;

        while (true) {
            System.out.print("Enter Option D : ");
            optionD = sc.nextLine().trim();
            if (!optionD.isEmpty()) {
                break;
            }
            System.out.println("Option D Cannot Be Empty.");
        }


        // ==========================================
        // CORRECT ANSWER
        // ==========================================

        String correctAnswer;

        while (true) {

            System.out.print("Enter Correct Answer (A/B/C/D) : ");
            correctAnswer = sc.nextLine().trim().toUpperCase();
            if (correctAnswer.equals("A") || correctAnswer.equals("B") || correctAnswer.equals("C") || correctAnswer.equals("D")) {

                break;
            }
            System.out.println("Enter A, B, C or D only.");
        }


        // ==========================================
        // MARKS
        // ==========================================
        int marks;
        while (true) {
            System.out.print("Enter Marks : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");
                sc.nextLine();
                continue;
            }
            marks = sc.nextInt();

            sc.nextLine();

            if (marks > 0) {
                break;
            }
            System.out.println("Marks must be greater than 0.");
        }


        // ==========================================
        // CREATE QUIZ OBJECT
        // ==========================================
        Quiz q = new Quiz(0, courseName, question, optionA, optionB, optionC, optionD, correctAnswer, marks);
        // ==========================================
        // SAVE QUIZ
        // ==========================================
        quizDAO.addQuiz(q);
    }


    // ==========================================
    // DISPLAY
    // ==========================================

    public void displayQuiz() {

        ArrayList<Quiz> list = quizDAO.getAllQuizzes();

        if (list.isEmpty()) {
            System.out.println("Quiz Not Found.");

            return;
        }
        System.out.println("\n========== ALL QUIZZES ==========");

        for (Quiz q : list) {
            q.displayQuiz();
        }
    }


    // ==========================================
    // SEARCH
    // ==========================================

    public void searchQuiz() {
        System.out.print("Enter Course Name or Question : ");
        String value = sc.nextLine();
        ArrayList<Quiz> list = quizDAO.searchQuiz(value);

        if (list.isEmpty()) {

            System.out.println("Quiz Not Found.");
            return;
        }

        for (Quiz q : list) {
            q.displayQuiz();
        }
    }


    // ==========================================
    // UPDATE
    // ==========================================

    public void updateQuiz() {

        System.out.println("\n========== UPDATE QUIZ ==========");

        // ==========================================
        // COURSE NAME
        // ======================================


        String courseName;

        while (true) {
            System.out.print("Enter Course Name : ");
            courseName = sc.nextLine().trim();

            if (courseName.isEmpty()) {
                System.out.println("Course Name Cannot Be Empty.");
                continue;
            }

            // Check course in database
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.searchCourseByName(courseName);

            if (course == null) {
                System.out.println("Course Name Not Found In Database.");
                continue;
            }

            // Course found
            courseName = course.getCourseName();
            break;
        }

        // ==========================================
        // OLD QUESTION
        // ==========================================
        String oldQuestion;
        Quiz selected = null;

        while (true) {
            System.out.print("Enter Current Question : ");
            oldQuestion = sc.nextLine().trim();
            if (oldQuestion.isEmpty()) {
                System.out.println("Question Cannot Be Empty.");
                continue;
            }
            ArrayList<Quiz> list = quizDAO.getAllQuizzes();
            selected = null;
            for (Quiz q : list) {
                if (q.getCourseName().equalsIgnoreCase(courseName) && q.getQuestion().equalsIgnoreCase(oldQuestion)) {
                    selected = q;
                    break;
                }
            }
            if (selected != null) {
                break;
            } else {
                System.out.println("Quiz Not Found In Database.");
                System.out.println("Please Enter Question Again.\n");
            }
        }

        // ==========================================
        // CURRENT QUIZ
        // ==========================================
        System.out.println("\n========== CURRENT QUIZ ==========");
        selected.displayQuiz();

        // ==========================================
        // NEW QUESTION
        // ==========================================
        String newQuestion;
        while (true) {
            System.out.print("\nEnter New Question : ");
            newQuestion = sc.nextLine().trim();

            if (!newQuestion.isEmpty()) {
                break;
            }
            System.out.println("Question Cannot Be Empty.");
        }

        selected.setQuestion(newQuestion);

        // ==========================================
        // OPTION A
        // ==========================================
        System.out.print("Enter New Option A : ");
        selected.setOptionA(sc.nextLine());
        // ==========================================
        // OPTION B
        // ==========================================
        System.out.print("Enter New Option B : ");

        selected.setOptionB(sc.nextLine());

        // ==========================================
        // OPTION C
        // ==========================================
        System.out.print("Enter New Option C : ");
        selected.setOptionC(sc.nextLine());

        // ==========================================
        // OPTION D
        // ==========================================
        System.out.print("Enter New Option D : ");
        selected.setOptionD(sc.nextLine());

        // ==========================================
        // CORRECT ANSWER
        // ==========================================

        String answer;

        while (true) {
            System.out.print("Enter Correct Answer (A/B/C/D) : ");
            answer = sc.nextLine().trim().toUpperCase();
            if (answer.equals("A") || answer.equals("B") || answer.equals("C") || answer.equals("D"))
            {
                break;
            }
            System.out.println("Invalid Answer. Enter A, B, C or D.");
        }

        selected.setCorrectAnswer(answer);
        // ==========================================
        // MARKS
        // ==========================================

        int marks;
        while (true) {
            System.out.print("Enter Marks : ");

            if (sc.hasNextInt()) {
                marks = sc.nextInt();
                sc.nextLine();
                if (marks > 0) {
                    break;
                }
                System.out.println("Marks Must Be Greater Than 0.");

            } else {
                System.out.println("Please Enter Number Only.");
                sc.nextLine();
            }
        }

        selected.setMarks(marks);

        // ==========================================
        // UPDATE
        // ==========================================

        quizDAO.updateQuiz(selected);
    }


    // ==========================================
    // DELETE
    // ==========================================

    public void deleteQuiz() {

        ArrayList<Quiz> list = quizDAO.getAllQuizzes();

        if (list.isEmpty()) {

            System.out.println("Quiz Not Found.");

            return;
        }

        System.out.println("\n========== QUIZZES ==========");
        for (int i = 0; i < list.size(); i++) {
            Quiz q = list.get(i);
            System.out.println((i + 1) + ". " + q.getCourseName() + " - " + q.getQuestion());
        }
        System.out.print("\nEnter Quiz Choice to Delete : ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > list.size()) {
            System.out.println("Invalid Choice.");
            return;
        }
        Quiz selected = list.get(choice - 1);
        System.out.print("Are you sure? (Y/N) : ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            quizDAO.deleteQuiz(selected.getQuizId());

        } else {
            System.out.println("Delete Cancelled.");
        }
    }
    public void startQuiz() {

        // ==========================================
        // SHOW AVAILABLE STUDENTS
        // ==========================================

        StudentDAO studentDAO = new StudentDAO();

        ArrayList<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No Students Available.");

            return;
        }
        System.out.println("\n========== AVAILABLE STUDENTS ==========");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " | " + s.getEmail() + " | " + s.getMobile());
        }

        System.out.print("\nEnter Student Choice : ");
        if (!sc.hasNextInt()) {
            System.out.println("Please enter number only.");

            sc.nextLine();

            return;
        }
        int studentChoice = sc.nextInt();
        sc.nextLine();
        if (studentChoice < 1 || studentChoice > students.size()) {
            System.out.println("Invalid Student Choice.");

            return;
        }
        Student selectedStudent = students.get(studentChoice - 1);
        String studentName = selectedStudent.getName();
        String studentEmail = selectedStudent.getEmail();

        String studentMobile = selectedStudent.getMobile();


        // ==========================================
        // SHOW AVAILABLE COURSES
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {

            System.out.println("No Courses Available.");

            return;
        }
        System.out.println("\n========== AVAILABLE COURSES ==========");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        // ==========================================
        // COURSE CHOICE
        // ==========================================
        int courseChoice;
        while (true) {
            System.out.print("\nEnter Course Choice : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");
                sc.nextLine();
                continue;
            }
            courseChoice = sc.nextInt();
            sc.nextLine();
            if (courseChoice < 1 || courseChoice > courses.size()) {
                System.out.println("Invalid Course Choice.");
                continue;}
            break;}
        String courseName = courses.get(courseChoice - 1).getCourseName();


        // ==========================================
        // GET QUIZ
        // ==========================================

        ArrayList<Quiz> quizzes = quizDAO.getQuizByCourse(courseName);

        if (quizzes.isEmpty()) {
            System.out.println("\nNo Quiz Available for " + courseName);

            return;
        }


        // ==========================================
        // START QUIZ
        // ==========================================
        System.out.println("\n======================================");
        System.out.println("              START QUIZ");
        System.out.println("======================================");
        System.out.println("Student : " + studentName);
        System.out.println("Course  : " + courseName);
        System.out.println("======================================");

        // ==========================================
        // RESULT VARIABLES
        // ==========================================
        int totalQuestions = quizzes.size();
        int correctAnswers = 0;
        int wrongAnswers = 0;
        int score = 0;
        int totalMarks = 0;
        // ==========================================
        // ASK QUESTIONS// ==========================================
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz q = quizzes.get(i);
            System.out.println("\nQuestion " + (i + 1) + " : " + q.getQuestion());
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());


            // ==========================================
            // ANSWER
            // ==========================================

            String answer;

            while (true) {
                System.out.print("Enter Answer (A/B/C/D) : ");
                answer = sc.nextLine().trim().toUpperCase();
                if (answer.equals("A") || answer.equals("B") || answer.equals("C") || answer.equals("D")) {break;}
                System.out.println("Please enter A, B, C or D only.");
            }


            // ==========================================
            // ADD TOTAL MARKS
            // ==========================================
            totalMarks += q.getMarks();


            // ==========================================
            // CHECK ANSWER
            // ==========================================
            if (answer.equals(q.getCorrectAnswer().trim().toUpperCase())) {
                correctAnswers++;
                score += q.getMarks();
                System.out.println("Correct!");
            } else {
                wrongAnswers++;
                System.out.println("Wrong!");
            }
        }


        // ==========================================
        // CALCULATE PERCENTAGE
        // ==========================================

        double percentage = 0;
        if (totalMarks > 0) {
            percentage = (score * 100.0) / totalMarks;
        }


        // ==========================================
        // PASS / FAIL
        // ==========================================

        String resultStatus;
        if (percentage >= 40) {
            resultStatus = "PASS";
        } else {
            resultStatus = "FAIL";
        }


        // ==========================================
        // SHOW RESULT
        // ==========================================
        System.out.println("\n======================================");
        System.out.println("             QUIZ RESULT");
        System.out.println("======================================");
        System.out.println("Student         : " + studentName);
        System.out.println("Email           : " + studentEmail);
        System.out.println("Mobile          : " + studentMobile);
        System.out.println("Course          : " + courseName);
        System.out.println("Total Questions : " + totalQuestions);
        System.out.println("Correct Answers : " + correctAnswers);
        System.out.println("Wrong Answers   : " + wrongAnswers);
        System.out.println("Total Marks     : " + totalMarks);
        System.out.println("Obtained Marks  : " + score);
        System.out.printf("Percentage      : %.2f%%\n", percentage);
        System.out.println("Result          : " + resultStatus);
        System.out.println("======================================");


        // ==========================================
        // SAVE RESULT IN TXT FILE
        // ==========================================
        saveQuizResult(studentName, studentEmail, studentMobile, courseName, totalMarks, score, percentage, resultStatus);


        // ==========================================
        // SAVE RESULT IN DATABASE
        // ==========================================

        Result resultObject = new Result(studentName, studentEmail, studentMobile, courseName, totalQuestions, correctAnswers, wrongAnswers, totalMarks, score, percentage, resultStatus);
        ResultDAO resultDAO = new ResultDAO();
        resultDAO.addResult(resultObject);
    }
    private void saveQuizResult(String studentName, String studentEmail, String studentMobile, String courseName,
                                int totalMarks, int score, double percentage, String result) {

        try {
            FileWriter fw = new FileWriter("quizResult.txt", true);

            PrintWriter pw = new PrintWriter(fw);
            pw.println("======================================");
            pw.println("              QUIZ RESULT");
            pw.println("======================================");
            pw.println("Student       : " + studentName);
            pw.println("Email         : " + studentEmail);
            pw.println("Mobile        : " + studentMobile);
            pw.println("Course        : " + courseName);
            pw.println("Total Marks   : " + totalMarks);
            pw.println("Obtained Marks: " + score);
            pw.printf("Percentage    : %.2f%%\n", percentage);
            pw.println("Result        : " + result);
            pw.println("======================================");
            pw.println();
            pw.close();
            System.out.println("\nQuiz result saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving quiz result : " + e.getMessage());
        }
    }
}