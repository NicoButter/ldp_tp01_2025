package playlist.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utilidad para resetear la base de datos a su estado inicial
 * <p>
 * Proporciona métodos para recrear las tablas y cargar datos de prueba.
 * </p>
 * 
 * @author Nicolas Butterfield
 * @version 1.0
 * @since Marzo 2025
 */
public class DatabaseReset {
    
   
    public static void factoryReset() throws SQLException {
        dropTables();
        createTables();
        insertDefaultGenres();
        insertSamplePlaylists();
    }
    
    private static void dropTables() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("DROP TABLE IF EXISTS playlist");
            stmt.executeUpdate("DROP TABLE IF EXISTS generos");
        }
    }
    
    private static void createTables() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE TABLE generos (" +
                              "ID INT(5) NOT NULL AUTO_INCREMENT, " +
                              "NOMBRE VARCHAR(20) NOT NULL, " +
                              "PRIMARY KEY (ID))");
            
            stmt.executeUpdate("CREATE TABLE playlist (" +
                              "ID INT(5) NOT NULL AUTO_INCREMENT, " +
                              "TITULO VARCHAR(50) NOT NULL, " +
                              "INTERPRETE VARCHAR(40) NOT NULL, " +
                              "CANTIDAD_TEMAS INT(3) NOT NULL, " +
                              "DURACION_TOTAL DECIMAL(5,2) NOT NULL, " +
                              "GENERO_ID INT(5), " +
                              "PRIMARY KEY (ID), " +
                              "FOREIGN KEY (GENERO_ID) REFERENCES generos(ID))");
        }
    }
    
    private static void insertDefaultGenres() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String[] genres = {
                "Rock", "Pop", "Electrónica", "Hip Hop", "Jazz",
                "Clásica", "Reggae", "Metal", "Indie", "Alternativo"
            };
            
            for (String genre : genres) {
                stmt.executeUpdate("INSERT INTO generos (NOMBRE) VALUES ('" + genre + "')");
            }
        }
    }
    
    private static void insertSamplePlaylists() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String[] playlists = {
                "('Greatest Rock Hits', 'Various Artists', 15, 58.30, 1)",
                "('Pop Essentials', 'Top 40', 20, 75.45, 2)",
                "('EDM Festival', 'DJ Mix', 12, 60.00, 3)",
                "('Rap Classics', 'Hip Hop Legends', 18, 72.15, 4)",
                "('Jazz Night', 'Smooth Jazz Collective', 10, 45.20, 5)",
                "('Classical Masterpieces', 'Philharmonic Orchestra', 8, 65.50, 6)",
                "('Reggae Vibes', 'Island Sounds', 14, 63.40, 7)",
                "('Heavy Metal', 'Metal Titans', 16, 70.25, 8)",
                "('Indie Discoveries', 'Emerging Artists', 12, 48.30, 9)",
                "('Alternative Mix', 'Underground Sounds', 15, 55.15, 10)"
            };
            
            for (String playlist : playlists) {
                stmt.executeUpdate("INSERT INTO playlist " +
                                  "(TITULO, INTERPRETE, CANTIDAD_TEMAS, DURACION_TOTAL, GENERO_ID) " +
                                  "VALUES " + playlist);
            }
        }
    }
}
