import java.util.*;
import java.io.*;
public class ResultService {
    private Scanner sc;
    private ResultDAO resultDAO;
    public ResultService() {
        sc = new Scanner(System.in);
        resultDAO = new ResultDAO();
    }
    public void resultMenu() {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("           RESULT MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. View All Results");
            System.out.println("2. Student-wise Result");
            System.out.println("3. Course-wise Result");
            System.out.println("4. Topper List");
            System.out.println("5. Search Result");
            System.out.println("6. Delete Result");
            System.out.println("7. Back");
            System.out.print("\nEnter Choice : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter number only.");
                sc.nextLine();
                continue;
            }
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewAllResults();
                    break;
                case 2:
                    studentWiseResult();
                    break;
                case 3:
                    courseWiseResult();
                    break;
                case 4:
                    viewTopperList();
                    break;
                case 5:
                    searchResult();
                    break;
                case 6:
                    deleteResult();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
    public void viewAllResults()
    {
        ArrayList<Result> list = resultDAO.getAllResults();

        if (list.isEmpty()) {
            System.out.println("No Result Found.");
            return;
        }
        System.out.println("\n========== ALL RESULTS ==========");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"));
            bw.write("========== ALL RESULTS ==========");
            bw.newLine();
            bw.newLine();
            for (Result r : list) {
                // ==================================
                // DISPLAY ON CONSOLE
                // ==================================
                r.displayResult();
                System.out.println("--------------------------------------");
                // ==================================
                // WRITE INTO result.txt
                // ==================================
                bw.write("======================================");
                bw.newLine();
                bw.write("Result ID       : " + r.getResultId());
                bw.newLine();
                bw.write("Student Name    : " + r.getStudentName());
                bw.newLine();
                bw.write("Student Email   : " + r.getStudentEmail());
                bw.newLine();
                bw.write("Student Mobile  : " + r.getStudentMobile());
                bw.newLine();
                bw.write("Course          : " + r.getCourseName());
                bw.newLine();
                bw.write("Total Questions : " + r.getTotalQuestions());
                bw.newLine();
                bw.write("Correct Answers : " + r.getCorrectAnswers());
                bw.newLine();
                bw.write("Wrong Answers   : " + r.getWrongAnswers());
                bw.newLine();
                bw.write("Marks           :" + r.getMarks());
                bw.newLine();
                bw.write("Total Marks     : " + r.getTotalMarks());
                bw.newLine();
                bw.write(String.format("Percentage      : %.2f%%", r.getPercentage()));
                bw.newLine();
                bw.write("Result          : "+ r.getResult());

                bw.newLine();
                bw.write("Result Date     : " + r.getResultDate());

                bw.newLine();
                bw.write("======================================");

                bw.newLine();
                bw.newLine();
            }
            bw.close();

            System.out.println("\nAll Results Saved Successfully.");

            System.out.println("File Name : result.txt");

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void viewStudentResults() {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> students =studentDAO.getAllStudents();

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
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > students.size()) {

            System.out.println("Invalid Student Choice.");

            return;
        }
        Student student = students.get(choice - 1);
        ArrayList<Result> list = resultDAO.getStudentResults(student.getName(), student.getEmail(), student.getMobile());

        if (list.isEmpty()) {
            System.out.println("No Result Found.");
            return;
        }

        for (Result r : list) {
            r.displayResult();
        }
    }


        public void viewCourseResults()  {

        System.out.println("\n========== COURSE WISE RESULT ==========");


        // ==========================================
        // GET ALL COURSES
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.getAllCourses();


        if (courses.isEmpty()) {
            System.out.println("Course Not Found.");

            return;
        }


        // ==========================================
        // SHOW COURSES
        // ==========================================

        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }


        // ==========================================
        // SELECT COURSE
        // ==========================================

        int choice;

        while (true) {
            System.out.print("Enter Course Choice : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please Enter Number Only.");
                sc.nextLine();
                continue;
            }
            choice = sc.nextInt();
            sc.nextLine();

            if (choice >= 1 && choice <= courses.size()) {
                break;
            }

            System.out.println("Course Not Found.");
        }
        Course selectedCourse = courses.get(choice - 1);
        String courseName = selectedCourse.getCourseName();


        // ==========================================
        // GET COURSE RESULTS
        // ==========================================

        ArrayList<Result> list = resultDAO.getCourseResults(courseName);

        if (list.isEmpty()) {

            System.out.println("\nResult Not Found For Course : " + courseName);
            return;
        }


        // ==========================================
        // DISPLAY RESULTS
        // ==========================================

        System.out.println("\n========== COURSE WISE RESULT ==========");
        System.out.println("Course : " + courseName);
        for (Result r : list) {
            r.displayResult();
            System.out.println("--------------------------------------");
        }
    }

        // ==========================================
        // SAVE COURSE RESULT
        // ==========================================

        private void saveCourseResultToFile(
                String courseName,
                ArrayList<Result> results) {

            String fileName = courseName.replaceAll("\\s+", "_") + "_Result.txt";

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
                bw.write("========== COURSE WISE RESULT ==========");
                bw.newLine();
                bw.write("Course : " + courseName);

                bw.newLine();
                bw.write("=========================================");
                bw.newLine();

                for (Result r : results) {
                    bw.write("Student Name : " + r.getStudentName());
                    bw.newLine();
                    bw.write("Course       : " + r.getCourseName());
                    bw.newLine();
                    bw.write("Score        : " + r.getMarks()+ " / " + r.getTotalQuestions());
                    bw.newLine();
                    bw.write("-----------------------------------------");
                    bw.newLine();
                }
                bw.close();
                System.out.println("\nCourse Result Saved In : " + fileName);

            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }

        // ==========================================
        // STUDENT WISE RESULT
        // ==========================================

          // ==========================================
// STUDENT-WISE RESULT
// ==========================================

    // ==========================================
// STUDENT-WISE COMBINED RESULT
// ==========================================

    // ==========================================
// STUDENT-WISE COMBINED RESULT
// ==========================================

    public void studentWiseResult()  {

        System.out.println("\n========== STUDENT WISE RESULT ==========");

        // ==========================================
        // GET STUDENTS
        // ==========================================

        StudentDAO studentDAO = new StudentDAO();

        ArrayList<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No Students Available.");

            return;
        }

        // ==========================================
        // DISPLAY STUDENTS
        // ==========================================

        System.out.println(
                "\n========== AVAILABLE STUDENTS =========="
        );
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " | " + s.getEmail() + " | " + s.getMobile());
        }

        // ==========================================
        // STUDENT CHOICE
        // ==========================================

        int choice;
        while (true) {
            System.out.print("\nEnter Student Choice : ");
            if (!sc.hasNextInt()) {
                System.out.println("Please Enter Number Only.");

                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();sc.nextLine();
            if (choice < 1 || choice > students.size()) {
                System.out.println("Invalid Student Choice.");

                continue;
            }

            break;
        }

        // ==========================================
        // SELECT STUDENT
        // ==========================================
        Student student = students.get(choice - 1);
        String name = student.getName();
        String email = student.getEmail();

        String mobile = student.getMobile();

        // ==========================================
        // GET COMBINED RESULT
        // ==========================================

        ResultDAO resultDAO = new ResultDAO();
        Result r = resultDAO.getCombinedStudentResult(name, email, mobile);

        // ==========================================
        // NO RESULT
        // ==========================================

        if (r == null) {

            System.out.println("\nNo Result Found.");

            return;
        }

        // ==========================================
        // DISPLAY COMBINED RESULT// ==========================================
        System.out.println("\n======================================");
        System.out.println("       STUDENT WISE COMBINED RESULT");
        System.out.println("======================================");
        System.out.println("Student Name    : " + r.getStudentName());
        System.out.println("Student Email   : " + r.getStudentEmail());
        System.out.println("Student Mobile  : " + r.getStudentMobile());
        System.out.println("--------------------------------------");
        System.out.println("Total Questions : " + r.getTotalQuestions());
        System.out.println("Correct Answers : " + r.getCorrectAnswers());
        System.out.println("Wrong Answers   : " + r.getWrongAnswers());

        System.out.println("Marks           : " + r.getMarks());

        System.out.println("Total Marks     : " + r.getTotalMarks());

        System.out.printf("Percentage      : %.2f%%\n", r.getPercentage());

        System.out.println("Result          : " + r.getResult());

        System.out.println("======================================");

        // ==========================================
        // SAVE TXT FILE
        // ==========================================

        saveStudentResultToFile(student, r);
    }

        // ==========================================
        // SAVE STUDENT RESULT
        // ==========================================
     private void saveStudentResultToFile(Student student, Result r)  {
        String fileName = student.getName().replaceAll("\\s+", "_") + "_Result.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write("========== STUDENT COMBINED RESULT ==========");
            bw.newLine();
            bw.write("Student Name    : " + student.getName());bw.newLine();
            bw.write("Email           : " + student.getEmail());
            bw.newLine();
            bw.write("Mobile          : " + student.getMobile());
            bw.newLine();
            bw.write("---------------------------------------------");
            bw.newLine();
            bw.write("Total Questions : " + r.getTotalQuestions());
            bw.newLine();
            bw.write("Correct Answers : " + r.getCorrectAnswers());
            bw.newLine();
            bw.write("Wrong Answers   : " + r.getWrongAnswers());
            bw.newLine();
            bw.write("Marks           : " + r.getMarks());
            bw.newLine();
            bw.write("Total Marks     : " + r.getTotalMarks());
            bw.newLine();
            bw.write(String.format("Percentage      : %.2f%%", r.getPercentage()));
            bw.newLine();
            bw.write("Result          : " + r.getResult());
            bw.newLine();

            bw.write("=============================================");
            bw.newLine();

            bw.close();

            System.out.println("\nStudent Result Saved In : " + fileName);

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
    public void viewTopperList() {

        ArrayList<Result> list = resultDAO.getAllResults();

        if (list.isEmpty()) {

            System.out.println("No Result Found.");
            return;
        }

        list.sort((r1, r2) -> Double.compare(r2.getPercentage(), r1.getPercentage()));
        System.out.println("\n======================================");
        System.out.println("              TOPPER LIST");
        System.out.println("======================================");
        int rank = 1;
        for (Result r : list) {
            System.out.println("\nRank : " + rank);
            System.out.println("Student : " + r.getStudentName());
            System.out.println("Email : " + r.getStudentEmail());
            System.out.println("Course : " + r.getCourseName());
            System.out.println("Marks : " + r.getMarks() + "/" + r.getTotalMarks());
            System.out.printf("Percentage : %.2f%%\n", r.getPercentage());
            System.out.println("Result : " + r.getResult());
            rank++;
        }
    }
    public void searchResult() {
        System.out.println("\n========== SEARCH RESULT ==========");

        // Clear leftover input from menu
        sc.nextLine();

        String value;
        while (true) {

            System.out.print("Enter Student Name OR Email OR Mobile OR Course : ");
            value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                break;
            }
            System.out.println("Search Value Cannot Be Empty.");
        }

        ArrayList<Result> list = resultDAO.searchResult(value);
        if (list.isEmpty()) {

            System.out.println("\nResult Not Found.");
            return;
        }

        System.out.println(
                "\n========== SEARCH RESULTS ==========");

        for (Result r : list) {

            r.displayResult();

            System.out.println("--------------------------------------");
        }
    }
    public void deleteResult() {

        ArrayList<Result> list = resultDAO.getAllResults();

        if (list.isEmpty()) {
            System.out.println("No Result Found.");
            return;
        }

        System.out.println("\n========== ALL RESULTS ==========");

        for (Result r : list) {
            System.out.println("Result ID : " + r.getResultId() + " | Student : " + r.getStudentName() + " | Email : " + r.getStudentEmail() + " | Course : " + r.getCourseName()
                    + " | Marks : " + r.getMarks() + "/" + r.getTotalMarks() + " | Percentage : "+ r.getPercentage());
        }
        System.out.print("\nEnter Result ID to Delete : ");
        if (!sc.hasNextInt()) {
            System.out.println("Please enter number only.");
            sc.nextLine();
            return;
        }
        int resultId = sc.nextInt();
        sc.nextLine();
        Result r = resultDAO.getResultById(resultId);
        if (r == null) {
            System.out.println("Result Not Found.");
            return;
        }
        System.out.println("\n========== SELECTED RESULT ==========");
        r.displayResult();
        System.out.print("\nAre you sure you want to delete? (Y/N) : ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            resultDAO.deleteResult(resultId);
        } else {
            System.out.println("Delete Cancelled.");
        }
    }
    private void courseWiseResult() {

        System.out.println("\n========== COURSE WISE RESULT ==========");

        // ==========================================
        // GET COURSES
        // ==========================================

        CourseDAO courseDAO = new CourseDAO();

        ArrayList<Course> courses = courseDAO.getAllCourses();

        if (courses.isEmpty()) {

            System.out.println("No Courses Available.");

            return;
        }

        // ==========================================
        // DISPLAY COURSES
        // ==========================================

        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);

            System.out.println((i + 1) + ". " + c.getCourseName());
        }

        // ==========================================
        // COURSE CHOICE
        // ==========================================

        int choice;

        while (true) {

            System.out.print("\nEnter Course Choice : ");

            if (!sc.hasNextInt()) {

                System.out.println("Please Enter Number Only.");

                sc.nextLine();

                continue;
            }

            choice = sc.nextInt();

            sc.nextLine();
            if (choice < 1 || choice > courses.size()) {
                System.out.println("Invalid Course Choice.");

                continue;
            }

            break;
        }

        // ==========================================
        // SELECT COURSE
        // ==========================================

        Course selectedCourse = courses.get(choice - 1);
        String courseName = selectedCourse.getCourseName();

        // ==========================================
        // GET COMBINED COURSE RESULT
        // ==========================================
        ResultDAO resultDAO = new ResultDAO();
        Result r = resultDAO.getCombinedCourseResult(courseName);

        // ==========================================
        // NO RESULT
        // ==========================================

        if (r == null) {
            System.out.println("\nNo Result Found For Course : " + courseName);

            return;
        }

        // ==========================================
        // DISPLAY COMBINED COURSE RESULT
        // ==========================================
        System.out.println("\n======================================");
        System.out.println("       COURSE WISE COMBINED RESULT");
        System.out.println("======================================");
        System.out.println("Course          : " + r.getCourseName());
        System.out.println("--------------------------------------");
        System.out.println("Total Students  : " + getTotalStudentsForCourse(courseName));
        System.out.println("Total Questions : " + r.getTotalQuestions());
        System.out.println("Correct Answers : " + r.getCorrectAnswers());
        System.out.println("Wrong Answers   : " + r.getWrongAnswers());
        System.out.println("Total Marks     : " + r.getMarks());
        System.out.println("Maximum Marks   : " + r.getTotalMarks());
        System.out.printf("Percentage      : %.2f%%\n", r.getPercentage());
        System.out.println("Overall Result  : " + r.getResult());

        System.out.println("======================================");
        int totalStudents = getTotalStudentsForCourse(courseName);
        saveCourseResultToFile(r, totalStudents);
    }
    private int getTotalStudentsForCourse(String courseName) {
        ResultDAO resultDAO = new ResultDAO();
        return resultDAO.getTotalStudentsForCourse(courseName);
    }
    private void saveCourseResultToFile(Result r, int totalStudents) {

        String fileName = r.getCourseName().replaceAll("\\s+", "_") + "_Course_Result.txt";

        try {
            BufferedWriter bw =new BufferedWriter(new FileWriter(fileName));
            bw.write("========== COURSE COMBINED RESULT ==========");
            bw.newLine();
            bw.write("Course          : " + r.getCourseName());
            bw.newLine();
            bw.write("Total Students  : " + totalStudents);
            bw.newLine();
            bw.write("--------------------------------------------");bw.newLine();
            bw.write("Total Questions : " + r.getTotalQuestions());
            bw.newLine();
            bw.write("Correct Answers : " + r.getCorrectAnswers());
            bw.newLine();
            bw.write("Wrong Answers   : " + r.getWrongAnswers());
            bw.newLine();
            bw.write("Total Marks     : " + r.getMarks());
            bw.newLine();
            bw.write("Maximum Marks   : " + r.getTotalMarks());bw.newLine();
            bw.write(String.format("Percentage      : %.2f%%", r.getPercentage()));
            bw.newLine();
            bw.write("Overall Result  : " + r.getResult());
            bw.newLine();

            bw.write("============================================");
            bw.newLine();

            bw.close();

            System.out.println("\nCourse Result Saved In : " + fileName);

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}