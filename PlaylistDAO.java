import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    public static void agregarPlaylist(String titulo, String interprete, int cantidadTemas, double duracionTotal) {
        String sql = "INSERT INTO playlist (titulo, interprete, cantidad_temas, duracion_total) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, titulo);
            stmt.setString(2, interprete);
            stmt.setInt(3, cantidadTemas);
            stmt.setDouble(4, duracionTotal);
            stmt.executeUpdate();
            
            System.out.println("Playlist agregada con éxito.");
        } catch (SQLException e) {
            System.out.println(" Error al agregar playlist: " + e.getMessage());
        }
    }

    public static void listarPlaylists() {
        String sql = "SELECT * FROM playlist";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("\n🎵 Playlists en la base de datos:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar playlists: " + e.getMessage());
        }
    }
}
