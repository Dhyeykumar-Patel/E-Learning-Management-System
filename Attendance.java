import java.time.LocalDate;

public class Attendance {

    private int attendanceId;
    private LocalDate attendanceDate;
    private String status;

    private String studentName;
    private String studentEmail;
    private String studentMobile;

    private String courseName;
    private String teacherName;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Attendance(int attendanceId, LocalDate attendanceDate, String status, String studentName, String studentEmail,
                      String studentMobile, String courseName, String teacherName) {

        this.attendanceId = attendanceId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentMobile = studentMobile;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public int getAttendanceId() {
        return attendanceId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public String getStatus() {
        return status;
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

    public String getTeacherName() {
        return teacherName;
    }

    // ==========================================
    // SETTERS
    // ==========================================

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    // ==========================================
    // DISPLAY
    // ==========================================

    public void displayAttendance() {
        System.out.println("\n========== ATTENDANCE DETAILS ==========");
        System.out.println("Attendance ID   : " + attendanceId);
        System.out.println("Student Name    : " + studentName);
        System.out.println("Student Email   : " + studentEmail);
        System.out.println("Student Mobile  : " + studentMobile);
        System.out.println("Course Name     : " + courseName);
        System.out.println("Teacher Name    : " + teacherName);
        System.out.println("Attendance Date : " + attendanceDate);
        System.out.println("Status          : " + status);
    }
}