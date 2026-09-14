import java.util.Scanner;

class SignUp {

    Scanner sc = new Scanner(System.in);

    SignUpDAO dao = new SignUpDAO();

    public void signUpMenu() {

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("          SIGN UP");
            System.out.println("=================================");
            System.out.println("1. Student Registration");
            System.out.println("2. Teacher Registration");
            System.out.println("3. Admin Registration");
            System.out.println("4. Back");
            System.out.print("Enter Choice : ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    dao.studentSignUp();

                    break;

                case 2:

                    dao.teacherSignUp();

                    break;

                case 3:

                    dao.adminSignUp();

                    break;

                case 4:

                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while (true);

    }
}
