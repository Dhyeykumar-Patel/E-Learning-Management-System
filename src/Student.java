public class Student {

    private int studentId;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String course;
    private double courseFees;
    private String paymentStatus;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public  Student()
    {

    }
    public Student(int studentId, String name, String email, String password, String mobile, String course, double courseFees, String paymentStatus) {

        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.course = course;
        this.courseFees = courseFees;
        this.paymentStatus = paymentStatus;
    }

    // ==========================
    // GETTERS
    // ==========================

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCourse() {
        return course;
    }

    public double getCourseFees() {
        return courseFees;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setCourseFees(double courseFees) {
        this.courseFees = courseFees;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // ==========================
    // DISPLAY
    // ==========================

    public void displayStudent() {

        System.out.println("\n========== STUDENT DETAILS ==========");
        System.out.println("Student ID    : " + studentId);
        System.out.println("Name          : " + name);
        System.out.println("Email         : " + email);
        System.out.println("Mobile        : " + mobile);
        System.out.println("Course        : " + course);
        System.out.println("Course Fees   : ₹" + courseFees);
        System.out.println("Payment       : " + paymentStatus);
    }

    // ==========================
    // TO STRING
    // ==========================

    @Override
    public String toString() {

        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", course='" + course + '\'' +
                ", courseFees=" + courseFees +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}