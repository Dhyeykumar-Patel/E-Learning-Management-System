public class Course {

    private int courseId;
    private String courseName;
    private String duration;
    private double fees;
    private int teacherId;
    private String teacherName;
    private int totalChapters;
    private String notes;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Course()
    {

    }
    public Course(int courseId, String courseName, String duration, double fees, int teacherId, String teacherName, int totalChapters, String notes) {

        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fees = fees;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.totalChapters = totalChapters;
        this.notes = notes;
    }

    // ==========================
    // GETTERS
    // ==========================

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDuration() {
        return duration;
    }

    public double getFees() {
        return fees;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getTotalChapters() {
        return totalChapters;
    }

    public String getNotes() {
        return notes;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTotalChapters(int totalChapters) {
        this.totalChapters = totalChapters;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // ==========================
    // DISPLAY COURSE
    // ==========================

    public void displayCourse() {

        System.out.println("\n==========================================");
        System.out.println("             COURSE DETAILS");
        System.out.println("==========================================");

        System.out.println("Course ID       : " + courseId);
        System.out.println("Course Name     : " + courseName);
        System.out.println("Teacher ID      : " + teacherId);
        System.out.println("Teacher Name    : " + teacherName);
        System.out.println("Duration        : " + duration);
        System.out.println("Total Chapters  : " + totalChapters);
        System.out.println("Course Fees     : ₹" + fees);
        System.out.println("Notes           : " + notes);

        System.out.println("==========================================");
    }

    @Override
    public String toString() {

        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", duration='" + duration + '\'' +
                ", fees=" + fees +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", totalChapters=" + totalChapters +
                ", notes='" + notes + '\'' +
                '}';
    }
}