public class Quiz {

    private int quizId;

    private String courseName;

    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctAnswer;

    private int marks;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public Quiz(int quizId, String courseName, String question, String optionA, String optionB, String optionC,
                String optionD, String correctAnswer, int marks) {

        this.quizId = quizId;
        this.courseName = courseName;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.marks = marks;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getQuizId() {
        return quizId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getMarks() {
        return marks;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }


    // ==========================================
    // DISPLAY
    // ==========================================

    public void displayQuiz() {
        System.out.println("\n========== QUIZ ==========");
        System.out.println("Course    : " + courseName);
        System.out.println("Question  : " + question);
        System.out.println("A. " + optionA);
        System.out.println("B. " + optionB);
        System.out.println("C. " + optionC);
        System.out.println("D. " + optionD);
        System.out.println("Marks     : " + marks);
    }
}