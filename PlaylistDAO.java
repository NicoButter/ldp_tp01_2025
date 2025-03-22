import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    public void agregarPlaylist(String titulo, String interprete, int cantidadTemas, double duracionTotal) {
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

    public void listarPlaylists() {
        String sql = "SELECT * FROM playlist";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("\n Playlists en la base de datos:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min");
            }
        } catch (SQLException e) {
            System.out.println(" Error al listar playlists: " + e.getMessage());
        }
    }

    public boolean editarPlaylist(int id, String nuevoTitulo, String nuevoInterprete, int nuevaCantidadTemas, double nuevaDuracionTotal) {
        String sql = "UPDATE playlist SET titulo = ?, interprete = ?, cantidad_temas = ?, duracion_total = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nuevoTitulo);
            stmt.setString(2, nuevoInterprete);
            stmt.setInt(3, nuevaCantidadTemas);
            stmt.setDouble(4, nuevaDuracionTotal);
            stmt.setInt(5, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la playlist: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPlaylist(int id) {
        String sql = "DELETE FROM playlist WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la playlist: " + e.getMessage());
            return false;
        }
    }
}
