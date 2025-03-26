import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {

    public void agregarPlaylist(String titulo, String interprete, int cantidadTemas, double duracionTotal, int idGenero) {
        if (!existeGenero(idGenero)) {
            System.out.println("El género especificado no existe.");
            return;
        }
        String sql = "INSERT INTO playlist (titulo, interprete, cantidad_temas, duracion_total, id_genero) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, interprete);
            stmt.setInt(3, cantidadTemas);
            stmt.setDouble(4, duracionTotal);
            stmt.setInt(5, idGenero);
            stmt.executeUpdate();
            System.out.println("Playlist agregada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar playlist: " + e.getMessage());
        }
    }

    public boolean existeGenero(int idGenero) {
        String sql = "SELECT COUNT(*) FROM generos WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idGenero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del género: " + e.getMessage());
        }
        return false;
    }

    public void listarPlaylists() {
        String sql = "SELECT * FROM playlist";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }
    
            System.out.println("\nPlaylists en la base de datos:");
            do {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min");
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists: " + e.getMessage());
        }
    }

    public void listarPlaylistsPorInterprete(String interprete) {
        String sql = "SELECT * FROM playlist WHERE interprete LIKE ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + interprete + "%");
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No se encontraron playlists para el intérprete: " + interprete);
                return;
            }
            
            System.out.println("\nPlaylists de " + interprete + ":");
            do {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min");
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists por intérprete: " + e.getMessage());
        }
    }

    public boolean editarPlaylist(int id, String nuevoTitulo, String nuevoInterprete, int nuevaCantidadTemas, double nuevaDuracionTotal, int nuevoIdGenero) {
        if (!existeGenero(nuevoIdGenero)) {
            System.out.println("El género especificado no existe.");
            return false;
        }
        String sql = "UPDATE playlist SET titulo = ?, interprete = ?, cantidad_temas = ?, duracion_total = ?, id_genero = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoTitulo);
            stmt.setString(2, nuevoInterprete);
            stmt.setInt(3, nuevaCantidadTemas);
            stmt.setDouble(4, nuevaDuracionTotal);
            stmt.setInt(5, nuevoIdGenero); // Nuevo campo
            stmt.setInt(6, id);
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

    public boolean existePlaylist(int id) {
        String sql = "SELECT COUNT(*) FROM playlist WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia de la playlist: " + e.getMessage());
        }
        return false; 
    }

    public String obtenerNombrePlaylist(int id) {
        String sql = "SELECT titulo FROM playlist WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("titulo"); 
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre de la playlist: " + e.getMessage());
        }
        return null;
    }

    // 1. Listar playlists ordenadas por un campo específico
    public void listarPlaylistsOrdenadas(String campoOrden) {
        String sql = "SELECT * FROM playlist ORDER BY " + campoOrden;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }

            System.out.println("\nPlaylists en la base de datos (ordenadas por " + campoOrden + "):");
            do {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min");
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists ordenadas: " + e.getMessage());
        }
    }

    // 2. Listar playlists relacionando tablas (mostrar descripción de la clave foránea)
    public void listarPlaylistsConGenero() {
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, p.duracion_total, g.nombre_genero " +
                     "FROM playlist p " +
                     "JOIN genero g ON p.id_genero = g.id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }

            System.out.println("\nPlaylists con su género correspondiente:");
            do {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min" +
                        " | Género: " + rs.getString("nombre_genero"));
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists con género: " + e.getMessage());
        }
    }

    // 3. Listar playlists relacionando tablas y ordenadas por un campo específico
    public void listarPlaylistsConGeneroOrdenadas(String campoOrden) {
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, p.duracion_total, g.nombre_genero " +
                     "FROM playlist p " +
                     "JOIN genero g ON p.id_genero = g.id " +
                     "ORDER BY " + campoOrden;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }

            System.out.println("\nPlaylists con su género correspondiente (ordenadas por " + campoOrden + "):");
            do {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Título: " + rs.getString("titulo") +
                        " | Intérprete: " + rs.getString("interprete") +
                        " | Temas: " + rs.getInt("cantidad_temas") +
                        " | Duración: " + rs.getDouble("duracion_total") + " min" +
                        " | Género: " + rs.getString("nombre_genero"));
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists con género ordenadas: " + e.getMessage());
        }
    }

    // 4. Agregar validación para evitar errores en el campo de ordenación
    public boolean esCampoValido(String campoOrden) {
        String[] camposValidos = {"titulo", "interprete", "cantidad_temas", "duracion_total"};
        for (String campo : camposValidos) {
            if (campo.equalsIgnoreCase(campoOrden)) {
                return true;
            }
        }
        return false;
    }
}