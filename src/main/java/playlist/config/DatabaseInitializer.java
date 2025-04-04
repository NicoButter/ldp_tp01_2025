/**
 * Sistema de Gestión de Playlists - TP Ejercicio Anterior
 * <p>
 * Clase responsable de inicializar completamente la base de datos,
 * incluyendo creación de BD, usuario, permisos y datos de prueba.
 * Incluye un submenú interactivo para regenerar la base de datos.
 * </p>
 * 
 * @author Nicolas Butterfield
 * @version 2.1
 * @since Marzo 2025
 */

 package playlist.config;

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.Scanner;
 
 public class DatabaseInitializer {
     private Connection connection;
     private static final String DB_NAME = "spotify_db";
     private static final String DB_USER = "spotify";
     private static final String DB_PASSWORD = "spotify010203";
     private final Scanner scanner;
 
     // Constructor que recibe el Scanner desde Main
     public DatabaseInitializer(Scanner scanner) {
         this.scanner = scanner;
     }
 
     // Submenú interactivo para regenerar la base de datos
     public void ejecutarSubmenuRegeneracion() {
         boolean salir = false;
 
         System.out.println("=== Regeneración de la Base de Datos de Spotify ===");
         while (!salir) {
             System.out.println("\nOpciones:");
             System.out.println("1. Regenerar base de datos y cargar datos de prueba");
             System.out.println("2. Volver al menú principal");
             System.out.print("Selecciona una opción (1-2): ");
 
             String opcion = scanner.nextLine();
 
             switch (opcion) {
                 case "1":
                     System.out.print("Ingresa el usuario administrador (ej. root): ");
                     String adminUser = scanner.nextLine();
                     System.out.print("Ingresa la contraseña del administrador: ");
                     String adminPassword = scanner.nextLine();
 
                     try {
                         connection = DriverManager.getConnection(
                             "jdbc:mariadb://localhost:3306/", // Sin BD específica aún
                             adminUser,
                             adminPassword
                         );
                         inicializarBDCompleta();
                     } catch (SQLException e) {
                         System.err.println("Error al regenerar la base de datos: " + e.getMessage());
                     }
                     break;
 
                 case "2":
                     salir = true;
                     System.out.println("Volviendo al menú principal...");
                     break;
 
                 default:
                     System.out.println("Opción no válida. Intenta de nuevo.");
             }
         }
     }
 
     private void inicializarBDCompleta() {
         try {
             connection.setAutoCommit(false);
             crearBaseDeDatosSiNoExiste();
             crearUsuarioSiNoExiste();
             otorgarPrivilegios();
             resetCompleto();
             connection.commit();
             System.out.println("Spotify DB inicializada correctamente");
         } catch (SQLException e) {
             try {
                 connection.rollback();
                 System.err.println("Error en configuración BD - Rollback: " + e.getMessage());
             } catch (SQLException ex) {
                 System.err.println("Error durante rollback: " + ex.getMessage());
             }
         } finally {
             try {
                 connection.setAutoCommit(true);
                 connection.close();
             } catch (SQLException e) {
                 System.err.println("Error restableciendo autocommit o cerrando: " + e.getMessage());
             }
         }
     }
 
     private void crearBaseDeDatosSiNoExiste() throws SQLException {
         try (Statement stmt = connection.createStatement()) {
             stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
             System.out.println("Base de datos verificada/creada: " + DB_NAME);
         }
     }
 
     private void crearUsuarioSiNoExiste() throws SQLException {
         try (Statement stmt = connection.createStatement()) {
             stmt.executeUpdate(
                 "CREATE USER IF NOT EXISTS '" + DB_USER + "'@'localhost' " +
                 "IDENTIFIED BY '" + DB_PASSWORD + "'");
             System.out.println("Usuario verificado/creado: " + DB_USER);
         }
     }
 
     private void otorgarPrivilegios() throws SQLException {
         try (Statement stmt = connection.createStatement()) {
             stmt.executeUpdate(
                 "GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON " + DB_NAME + ".* " +
                 "TO '" + DB_USER + "'@'localhost'");
             stmt.executeUpdate(
                 "GRANT REFERENCES ON " + DB_NAME + ".* TO '" + DB_USER + "'@'localhost'");
             System.out.println("Privilegios otorgados al usuario");
         }
     }
 
     private void resetCompleto() throws SQLException {
         try (Statement stmt = connection.createStatement()) {
             stmt.executeUpdate("USE " + DB_NAME);
             stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
             stmt.executeUpdate("DROP TABLE IF EXISTS playlist");
             stmt.executeUpdate("DROP TABLE IF EXISTS generos");
             
             stmt.executeUpdate("CREATE TABLE generos (" +
                 "ID INT(5) NOT NULL AUTO_INCREMENT, " +
                 "NOMBRE VARCHAR(20) NOT NULL, " +
                 "PRIMARY KEY (ID)) ENGINE=InnoDB");
             
             stmt.executeUpdate("CREATE TABLE playlist (" +
                 "ID INT(5) NOT NULL AUTO_INCREMENT, " +
                 "TITULO VARCHAR(50) NOT NULL, " +
                 "INTERPRETE VARCHAR(40) NOT NULL, " +
                 "CANTIDAD_TEMAS INT(3) NOT NULL, " +
                 "DURACION_TOTAL DECIMAL(5,2) NOT NULL, " +
                 "GENERO_ID INT(5), " +
                 "PRIMARY KEY (ID), " +
                 "CONSTRAINT fk_playlist_genero " +
                 "FOREIGN KEY (GENERO_ID) REFERENCES generos(ID)) ENGINE=InnoDB");
             
             stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
             insertarGeneros(stmt);
             insertarPlaylists(stmt);
         }
     }
 
     private void insertarGeneros(Statement stmt) throws SQLException {
         String[] genres = {
             "Rock", "Pop", "Electrónica", "Hip Hop", "Jazz",
             "Clásica", "Reggae", "Metal", "Indie", "Alternativo"
         };
         for (String genre : genres) {
             stmt.executeUpdate("INSERT INTO generos (NOMBRE) VALUES ('" + genre + "')");
         }
         System.out.println("Géneros musicales insertados: " + genres.length);
     }
 
     private void insertarPlaylists(Statement stmt) throws SQLException {
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
         System.out.println("Playlists de ejemplo insertadas: " + playlists.length);
     }
 
     public static String getDbUser() {
         return DB_USER;
     }
 
     public static String getDbPassword() {
         return DB_PASSWORD;
     }
 
     public static String getDbName() {
         return DB_NAME;
     }
 }