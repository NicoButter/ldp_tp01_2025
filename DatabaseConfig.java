import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mariadb://localhost:3306/spotify_db";
    private static final String USER = "spotify";
    private static final String PASSWORD = "spotify010203";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al intentar conectarse a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
