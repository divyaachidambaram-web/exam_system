import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection 
{

    static final String URL = "jdbc:mysql://localhost:3306/exam_db";
    static final String USER = "root";
    static final String PASS = "Divya@2026Mysql";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded ✅");

            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to DB ✅");

            return conn;

        } 
        catch (Exception e) {
            System.out.println("Connection Failed ❌");
            e.printStackTrace();
            return null;
        }
    }
}
