import java.util.ArrayList;
import java.util.Scanner;

public class CourseService {

    private Scanner sc;
    private CourseDAO courseDAO;

    // ==========================
    // CONSTRUCTOR
    // ==========================

    public CourseService() {

        sc = new Scanner(System.in);

        courseDAO = new CourseDAO();

    }

    // ==========================
    // COURSE MENU
    // ==========================

    public void courseMenu() {

        while (true) {

            System.out.println("\n====================================");
            System.out.println("          COURSE MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Add Course");
            System.out.println("2. Display Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. Delete Course");
            System.out.println("6. Back");

            System.out.print("Enter Choice : ");

            int choice;

            if (sc.hasNextInt()) {

                choice = sc.nextInt();
                sc.nextLine();

            } else {

                System.out.println("Please Enter Number Only.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    addCourse();
                    break;

                case 2:
                    displayCourse();
                    break;

                case 3:
                    searchCourse();
                    break;

                case 4:
                    updateCourse();
                    break;

                case 5:
                    deleteCourse();
                    break;

                case 6:
                    System.out.println("Back to Previous Menu.");
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
    // ==========================
    // ADD COURSE
    // ==========================


    public void addCourse() {

        System.out.println("\n========== ADD COURSE ==========");

        // =================================================
        // COURSE NAME
        // =================================================

        String courseName;

        while (true) {

            System.out.print("Enter Course Name : ");

            courseName = sc.nextLine().trim();

            if (courseName.isEmpty()) {

                System.out.println("Course Name Cannot Be Empty.");

                continue;
            }

            Course existing = courseDAO.searchCourseByName(courseName);

            if (existing != null) {

                System.out.println("Course Already Exists In Database.");

                continue;
            }

            break;
        }


        // =================================================
        // DURATION
        // =================================================

        int months;

        while (true) {

            System.out.print("Enter Duration in Months : ");
            if (sc.hasNextInt()) {

                months = sc.nextInt();
                sc.nextLine();

                if (months > 0) {
                    break;
                }

                System.out.println("Duration must be greater than 0.");
            } else {

                System.out.println("Please enter number only.");

                sc.nextLine();
            }
        }

        String duration = months + " Months";


        // =================================================
        // FEES
        // =================================================

        double fees;

        while (true) {

            System.out.print("Enter Course Fees : ");

            if (sc.hasNextDouble()) {

                fees = sc.nextDouble();
                sc.nextLine();

                if (fees > 0) {
                    break;
                }

                System.out.println("Fees must be greater than 0.");

            } else {

                System.out.println("Please enter valid fees.");

                sc.nextLine();
            }
        }


        // =================================================
        // SHOW TEACHERS
        // =================================================

        TeacherDAO teacherDAO = new TeacherDAO();

        ArrayList<Teacher> teachers = teacherDAO.getAllTeachers();

        if (teachers.isEmpty()) {

            System.out.println("No Teachers Found In Database.");

            return;
        }

        System.out.println(
                "\n========== AVAILABLE TEACHERS =========="
        );

        for (Teacher t : teachers) {

            System.out.println("ID : " + t.getTeacherId() + " | Name : " + t.getName() + " | Subject : " + t.getSubject());
        }


        // =================================================
        // SELECT TEACHER
        // =================================================

        Teacher selectedTeacher = null;

        while (selectedTeacher == null) {

            System.out.print("Enter Teacher ID : ");

            if (!sc.hasNextInt()) {

                System.out.println("Please enter a valid Teacher ID.");

                sc.nextLine();
                continue;
            }

            int teacherId =
                    sc.nextInt();

            sc.nextLine();

            for (Teacher t : teachers) {

                if (t.getTeacherId() == teacherId) {

                    selectedTeacher = t;
                    break;
                }
            }

            if (selectedTeacher == null) {

                System.out.println("Teacher ID Not Found.");

                System.out.println("Please Enter Teacher ID Again.\n");
            }
        }


        // =================================================
        // TOTAL CHAPTERS
        // =================================================

        int totalChapters;

        while (true) {

            System.out.print("Enter Total Chapters : ");

            if (sc.hasNextInt()) {

                totalChapters = sc.nextInt();

                sc.nextLine();

                if (totalChapters > 0) {
                    break;
                }

                System.out.println("Total Chapters must be greater than 0.");

            } else {

                System.out.println("Please enter number only.");

                sc.nextLine();
            }
        }


        // =================================================
        // NOTES
        // =================================================

        System.out.print("Enter Notes : ");

        String notes = sc.nextLine();


        // =================================================
        // CREATE COURSE
        // =================================================

        Course c = new Course();

        c.setCourseName(courseName);
        c.setDuration(duration);
        c.setFees(fees);

        // Store teacher ID
        c.setTeacherId(selectedTeacher.getTeacherId());

        // Store teacher name
        c.setTeacherName(selectedTeacher.getName());

        c.setTotalChapters(totalChapters);

        c.setNotes(notes);


        // =================================================
        // SAVE COURSE
        // =================================================

        courseDAO.addCourse(c);
    }

    // ==========================
    // DISPLAY COURSES
    // ==========================

    public void displayCourse() {

        ArrayList<Course> list = courseDAO.getAllCourses();
        if (list.isEmpty()) {

            System.out.println("Course Not Found.");
            return;
        }

        System.out.println(
                "\n========== ALL COURSES ==========");

        for (Course c : list) {

            c.displayCourse();

            System.out.println("----------------------------------");
        }
    }

    // ==========================
    // SEARCH COURSE
    // ==========================

    public void searchCourse() {

        System.out.print("Enter Course Name or Teacher Name : ");

        String value = sc.nextLine();
        ArrayList<Course> list = courseDAO.searchCourse(value);

        if (list.isEmpty()) {

            System.out.println("\nCourse Not Found.");

            return;
        }

        for (Course c : list) {

            c.displayCourse();
        }
    }

    // ==========================
    // UPDATE COURSE
    // ==========================



    public void updateCourse() {

        System.out.println("\n========== UPDATE COURSE ==========");

        // =================================================
        // FIND COURSE BY CURRENT COURSE NAME
        // =================================================

        String oldCourseName;
        Course c;

        while (true) {

            System.out.print("Enter Current Course Name : ");

            oldCourseName = sc.nextLine().trim();

            if (oldCourseName.isEmpty()) {
                System.out.println("Course Name Cannot Be Empty.");

                continue;
            }

            c = courseDAO.searchCourseByName(oldCourseName);

            if (c != null) {

                break;

            } else {

                System.out.println("Course Name Not Found In Database.");

                System.out.println("Please Enter Course Name Again.\n");
            }
        }


        // =================================================
        // SHOW CURRENT COURSE
        // =================================================

        System.out.println("\n========== CURRENT COURSE ==========");

        c.displayCourse();


        // =================================================
        // NEW COURSE NAME
        // =================================================

        String newCourseName;

        while (true) {

            System.out.print("\nEnter New Course Name : ");

            newCourseName = sc.nextLine().trim();

            if (newCourseName.isEmpty()) {

                System.out.println("Course Name Cannot Be Empty.");

                continue;
            }

            break;
        }

        c.setCourseName(newCourseName);


        // =================================================
        // NEW DURATION
        // =================================================

        int months;

        while (true) {

            System.out.print("Enter New Duration in Months : ");

            if (sc.hasNextInt()) {

                months = sc.nextInt();
                sc.nextLine();

                if (months > 0) {

                    break;

                } else {

                    System.out.println("Duration must be greater than 0.");
                }

            } else {

                System.out.println("Please enter number only.");

                sc.nextLine();
            }
        }

        String duration = months + " Months";

        c.setDuration(duration);


        // =================================================
        // NEW FEES
        // =================================================

        double fees;

        while (true) {

            System.out.print("Enter New Course Fees : ");

            if (sc.hasNextDouble()) {

                fees = sc.nextDouble();
                sc.nextLine();

                if (fees > 0) {

                    break;

                } else {

                    System.out.println("Fees must be greater than 0.");
                }

            } else {

                System.out.println("Please enter valid fees.");

                sc.nextLine();
            }
        }

        c.setFees(fees);


        // =================================================
        // SHOW TEACHERS
        // =================================================

        TeacherDAO teacherDAO = new TeacherDAO();

        ArrayList<Teacher> teachers = teacherDAO.getAllTeachers();


        System.out.println("\n========== TEACHERS ==========");


        if (teachers.isEmpty()) {

            System.out.println("No Teachers Found.");

            return;
        }


        for (Teacher t : teachers) {
            System.out.println("ID : " + t.getTeacherId() + " | Name : " + t.getName() + " | Subject : " + t.getSubject());
        }


        // =================================================
        // NEW TEACHER ID
        // =================================================

        int teacherId;

        while (true) {

            System.out.print("\nEnter New Teacher ID : ");

            if (sc.hasNextInt()) {

                teacherId = sc.nextInt();

                sc.nextLine();


                boolean found = false;

                for (Teacher t : teachers) {
                    if (t.getTeacherId() == teacherId) {

                        found = true;
                        break;
                    }
                }


                if (found) {

                    break;

                } else {

                    System.out.println("Teacher ID Not Found.");
                }

            } else {

                System.out.println("Please Enter Number Only.");

                sc.nextLine();
            }
        }

        c.setTeacherId(teacherId);


        // =================================================
        // TOTAL CHAPTERS
        // =================================================

        int totalChapters;

        while (true) {

            System.out.print("Enter Total Chapters : ");

            if (sc.hasNextInt()) {

                totalChapters = sc.nextInt();

                sc.nextLine();

                if (totalChapters > 0) {

                    break;

                } else {

                    System.out.println("Total Chapters Must Be Greater Than 0.");
                }

            } else {

                System.out.println("Please Enter Number Only.");

                sc.nextLine();
            }
        }

        c.setTotalChapters(totalChapters);


        // =================================================
        // NOTES
        // =================================================

        System.out.print("Enter New Notes : ");

        String notes = sc.nextLine();

        c.setNotes(notes);


        // =================================================
        // UPDATE DATABASE
        // =================================================

        courseDAO.updateCourse(c);
    }
    // ==========================
    // DELETE COURSE
    // ==========================

    public void deleteCourse() {

        System.out.print("Enter Course Name : ");

        String courseName = sc.nextLine();

        Course c = courseDAO.findCourse(courseName);

        if (c == null) {

            System.out.println("Course Not Found.");
            return;
        }

        System.out.println("\n========== COURSE TO DELETE ==========");

        c.displayCourse();

        System.out.print("\nAre you sure you want to delete? (Y/N): ");

        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            courseDAO.deleteCourse(courseName);

        } else {

            System.out.println("Delete Cancelled.");

        }
    }
    // ==========================
    // READ COURSE NOTES
    // ==========================

    public void readCourseNotes(String courseName) {

        Course c = courseDAO.findCourse(courseName);

        if (c == null) {

            System.out.println("Course Not Found.");

            return;
        }

        System.out.println("\n========== COURSE DETAILS ==========");

        c.displayCourse();

        System.out.println("\nCourse Notes : ");

        System.out.println(c.getNotes());
    }

}