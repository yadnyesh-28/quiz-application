package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

	
        public static final String url = "jdbc:mysql://localhost:3306/quiz_app"; 
        public static final String username = "root";
        public static final String password = "root";
        public static  Connection getConnection() {
            try {
                return DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

}
