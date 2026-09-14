import java.time.*;
import java.sql.*;
import java.io.*;
import java.util.*;
public class ELearningManagementSystem {
    public static void main(String[] args) throws Exception
    {
        DatabaseConnection db=new DatabaseConnection();
        Connection con=db.getConnection();
        if (con==null)
        {
            return;
        }


        while (true) {
            Scanner sc=new Scanner(System.in);
            System.out.println("\n======================================");
            System.out.println("       E-LEARNING MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice;

            while (true) {

                System.out.print("Enter Choice : ");

                if (sc.hasNextInt()) {

                    choice = sc.nextInt();
                    sc.nextLine();
                    break;

                } else {

                    System.out.println(
                            "Invalid input! Please enter 1, 2 or 3."
                    );

                    sc.nextLine();
                }
            }

            switch (choice) {

                case 1:
                    // signup
                    SignUp signUp=new SignUp();
                    signUp.signUpMenu();
                    break;

                case 2:
                    Login login = new Login();
                    login.login();
                    break;

                case 3:
                    System.out.println("Thank You.");
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }

    }
 }

