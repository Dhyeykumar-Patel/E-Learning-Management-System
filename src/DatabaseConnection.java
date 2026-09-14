import java.sql.Connection;
import java.sql.DriverManager;

class DatabaseConnection {

    private static Connection con;

    public static Connection getConnection() {

        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/patel","root","");
                System.out.println("Connected Successfully.");

            }
        }
        catch (Exception e) {
            System.out.println("Connection Failed.");
        }
        return con;
    }
}

