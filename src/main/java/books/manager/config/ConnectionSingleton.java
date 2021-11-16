package books.manager.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/book";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "To@nOpen89";
    private static Connection connection;

    public static Connection getConnection(){
        if (connection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //product: TÊN CỦA DATABASE
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                System.out.println("Ket noi Thanh cong");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Ket noi loi");
            }
        }
        return connection;
    }
}
