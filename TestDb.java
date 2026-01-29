import java.sql.*;

public class TestDb {
    public static void main(String[] args) {
        // Use environment variables for security
        String url = "jdbc:mysql://localhost:3306/students";
        String username = System.getenv("DB_USERNAME") != null ? 
                         System.getenv("DB_USERNAME") : "root";
        String password = System.getenv("DB_PASSWORD");
        
        // Use try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {
            
            // Process results
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
