public class Result {

    private int resultId;

    private String studentName;
    private String studentEmail;
    private String studentMobile;
    private String courseName;

    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;

    private int marks;
    private int totalMarks;

    private double percentage;

    private String result;
    private String resultDate;
    public Result(String studentName, String studentEmail, String studentMobile, String courseName, int totalQuestions, int correctAnswers,
                  int wrongAnswers, int totalMarks, int score, double percentage, String result) {

        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentMobile = studentMobile;
        this.courseName = courseName;

        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;

        this.totalMarks = totalMarks;
        this.marks = score;

        this.percentage = percentage;
        this.result = result;
    }

    public Result(int resultId, String studentName, String studentEmail, String studentMobile, String courseName,
                  int totalQuestions, int correctAnswers, int wrongAnswers, int marks,int totalMarks, double percentage,
                  String result, String resultDate) {

        this.resultId = resultId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentMobile = studentMobile;
        this.courseName = courseName;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.marks = marks;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.result = result;
        this.resultDate = resultDate;
    }


    public int getResultId() {
        return resultId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getMarks() {
        return marks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getResult() {
        return result;
    }

    public String getResultDate() {
        return resultDate;
    }


    public void displayResult() {

        System.out.println("\n======================================");
        System.out.println("Result ID       : " + resultId);
        System.out.println("Student Name    : " + studentName);
        System.out.println("Student Email   : " + studentEmail);
        System.out.println("Student Mobile  : " + studentMobile);
        System.out.println("Course          : " + courseName);
        System.out.println("Total Questions : " + totalQuestions);
        System.out.println("Correct Answers : " + correctAnswers);
        System.out.println("Wrong Answers   : " + wrongAnswers);
        System.out.println("Marks           : " + marks);
        System.out.println("Total Marks     : " + totalMarks);
        System.out.printf("Percentage      : %.2f%%\n", percentage);
        System.out.println("Result          : " + result);
        System.out.println("Result Date     : " + resultDate);
    }
}