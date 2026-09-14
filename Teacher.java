public class Teacher {

    private int teacherId;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String subject;
    private double salary;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Teacher(){

    }
    public Teacher(int teacherId, String name, String email, String password, String mobile, String subject, double salary) {

        this.teacherId = teacherId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.subject = subject;
        this.salary = salary;
    }

    // ==========================
    // GETTERS
    // ==========================

    public int getTeacherId() {
        return teacherId;
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

    public String getSubject() {
        return subject;
    }

    public double getSalary() {
        return salary;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // ==========================
    // DISPLAY
    // ==========================

    public void displayTeacher() {

        System.out.println("\n========== TEACHER DETAILS ==========");

        System.out.println("Teacher ID : " + teacherId);
        System.out.println("Name       : " + name);
        System.out.println("Email      : " + email);
        System.out.println("Mobile     : " + mobile);
        System.out.println("Subject    : " + subject);
        System.out.println("Salary     : ₹" + salary);
    }

    @Override
    public String toString() {

        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", subject='" + subject + '\'' +
                ", salary=" + salary +
                '}';

    }
}