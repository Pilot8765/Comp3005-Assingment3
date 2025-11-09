import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Loads Username, Password and URL from a props.properties file
        Properties props = new Properties();
        try {
            FileInputStream propsIn = new FileInputStream("props.properties");
            props.load(propsIn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        };

        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        // Create the Database
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                // Remake the DB
                // Get name from user
                System.out.println("Name of Database:");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                DatabaseFunctions.createDB(connection, name);

                // Run Main Loop
                runLoop(connection);

                // Close Connection
                connection.close();
            } else {
                System.out.println("Failed to connect to the database");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Allows User to select Functionality and run it.
    public static void runLoop(Connection conn) {
        String choices = "\n1: Get all Students\n" +
                            "2: Add a Student\n" +
                            "3: Update a Student's email\n" +
                            "4: Remove a Student\n" +
                            "5: Quit\n";
        Scanner scan = new Scanner(System.in);
        String first_name, last_name, email, date;
        int student_id;
        while (true){
            System.out.println(choices);
            try {
                int choice = scan.nextInt();
                scan.nextLine();
                switch (choice) {
                    case 1:
                        // Get Students
                        DatabaseFunctions.getAllStudents(conn);
                        break;
                    case 2:
                        // Collect Information
                        System.out.println("First Name:");
                        first_name = scan.nextLine();
                        System.out.println("Last Name");
                        last_name = scan.nextLine();
                        System.out.println("Email:");
                        email = scan.nextLine();
                        System.out.println("Enrollment Date (Y-M-D):");
                        date = scan.nextLine();

                        DatabaseFunctions.addStudent(conn, first_name, last_name, email, date);
                        break;
                    case 3:
                        // Collect info from user
                        System.out.println("Which student (Student_id):");
                        student_id = scan.nextInt();
                        System.out.println("New Email");
                        scan.nextLine();
                        email = scan.nextLine();

                        DatabaseFunctions.updateStudentEmail(conn, student_id, email);
                        break;
                    case 4:
                        // Collect info from user
                        System.out.println("Which student (Student_id):");
                        student_id = scan.nextInt();

                        DatabaseFunctions.deleteStudent(conn, student_id);
                        break;
                    case 5:
                        scan.close();
                        return;
                    default:
                        // Invalid Int
                        System.out.println("Select option 1-5");
                }
            }catch(Exception e){
                // Not an int
                System.out.println("Select option 1-5");
                scan.nextLine();
            }
        }
    }
}