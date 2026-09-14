import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login {

    Scanner sc = new Scanner(System.in);

    public void login()
    {
        Connection con = null;

        try {

            // ==========================================
            // DATABASE CONNECTION
            // ==========================================

            con = DatabaseConnection.getConnection();

            if (con == null) {
                System.out.println("Database Connection Failed.");

                return;
            }


            // ==========================================
            // SHOW LOGIN TABLE
            // PASSWORD IS NOT DISPLAYED
            // ==========================================
            System.out.println("\n========== LOGIN TABLE ==========");
            String displaySql = "SELECT id, role FROM login";
            Statement st = con.createStatement();
            ResultSet tableRs = st.executeQuery(displaySql);
            System.out.println("--------------------------------");

            System.out.printf("%-10s %-20s%n", "ID", "ROLE");

            System.out.println("--------------------------------");

            boolean found = false;

            while (tableRs.next()) {

                found = true;

                System.out.printf("%-10s %-20s%n", tableRs.getString("id"), tableRs.getString("role"));
            }

            System.out.println("--------------------------------");

            tableRs.close();
            st.close();


            if (!found) {

                System.out.println("No Login Details Found.");

                return;
            }


            // ==========================================
            // LOGIN
            // ==========================================

            System.out.println("\n========== LOGIN ==========");

            System.out.print("Enter ID : ");

            int id;

            while (true) {

                if (sc.hasNextInt()) {

                    id = sc.nextInt();
                    sc.nextLine();

                    break;

                } else {

                    System.out.println("Invalid ID. Please enter number only.");
                    sc.nextLine();
                    System.out.print("Enter ID : ");
                }
            }


            System.out.print("Enter Password : ");

            String password = sc.nextLine();


            // ==========================================
            // CHECK LOGIN
            // ==========================================

            String sql = "SELECT role FROM login " + "WHERE id=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");


                // ==================================
                // STUDENT
                // ==================================

                if (role.equalsIgnoreCase("student")) {
                    System.out.println("\nStudent Login Successful.");
                    StudentService student = new StudentService(id);
                    student.studentMenu();
                }


                // ==================================
                // TEACHER
                // ==================================

                else if (role.equalsIgnoreCase("teacher")) {
                    System.out.println("\nTeacher Login Successful.");

                    TeacherService teacher = new TeacherService();

                    teacher.teacherMenu();
                }


                // ==================================
                // ADMIN
                // ==================================

                else if (role.equalsIgnoreCase("admin")) {

                    System.out.println("\nAdmin Login Successful.");
                    AdminMenu admin = new AdminMenu();

                    admin.adminDashboard();
                }


                // ==================================
                // INVALID ROLE
                // ==================================

                else {

                    System.out.println("Invalid Role.");
                }

            } else {

                System.out.println("\nInvalid ID or Password.");
            }


            // ==========================================
            // CLOSE
            // ==========================================

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

}