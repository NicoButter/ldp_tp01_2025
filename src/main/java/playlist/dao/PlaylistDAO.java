/**
 * Capa de acceso a datos para la entidad Playlist
 * <p>
 * Maneja todas las operaciones de base de datos relacionadas
 * con las playlists musicales y su relación con géneros.
 * </p>
 * 
 * @author Nicolás Butterfield
 * @version 1.0
 * @since Marzo 2025
 */

package playlist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import playlist.config.DatabaseConfig;

public class PlaylistDAO {

    public void agregarPlaylist(String titulo, String interprete, int cantidadTemas,
            double duracionTotal, String nombreGenero) {
        int idGenero = existeGenero(nombreGenero.toLowerCase());
        if (idGenero == -1) {
            System.out.println("El género '" + nombreGenero + "' no existe.");
            return;
        }

        String sql = "INSERT INTO playlist (titulo, interprete, cantidad_temas, duracion_total, genero_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, interprete);
            stmt.setInt(3, cantidadTemas);
            stmt.setDouble(4, duracionTotal);
            stmt.setInt(5, idGenero);

            stmt.executeUpdate();
            System.out.println("Playlist agregada con éxito :)");
        } catch (SQLException e) {
            System.out.println("Error al agregar playlist: " + e.getMessage());
        }
    }

    public int existeGenero(String nombre) {
        String sql = "SELECT ID FROM generos WHERE NOMBRE = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del género: " + e.getMessage());
        }
        return -1;
    }

    public void listarPlaylists() {
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, " +
                "p.duracion_total, g.nombre AS nombre_genero " +
                "FROM playlist p " +
                "LEFT JOIN generos g ON p.genero_id = g.id";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }

            System.out.println("\nListado de Playlists:");
            do {
                String genero = rs.getString("nombre_genero");
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | Título: " + rs.getString("titulo") +
                                " | Intérprete: " + rs.getString("interprete") +
                                " | Temas: " + rs.getInt("cantidad_temas") +
                                " | Duración: " + rs.getDouble("duracion_total") + " min" +
                                " | Género: " + (genero != null ? genero : "Sin género asignado"));
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

    public boolean editarPlaylist(int id, String nuevoTitulo, String nuevoInterprete, int nuevaCantidadTemas,
            double nuevaDuracionTotal, String nuevoNombreGenero) {
        int idGeneroNuevo = existeGenero(nuevoNombreGenero.toLowerCase());
        if (idGeneroNuevo == -1) {
            System.out.println("El género '" + nuevoNombreGenero
                    + "' no existe. Por favor, crealo en la opción 8 (CRUD de géneros).");
            return false;
        }

        String sql = "UPDATE playlist SET titulo = ?, interprete = ?, cantidad_temas = ?, duracion_total = ?, id_genero = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoTitulo);
            stmt.setString(2, nuevoInterprete);
            stmt.setInt(3, nuevaCantidadTemas);
            stmt.setDouble(4, nuevaDuracionTotal);
            stmt.setInt(5, idGeneroNuevo);
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

    public void listarPlaylistsConGenero() {
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, " +
                    "p.duracion_total, g.nombre AS nombre_genero " +
                    "FROM playlist p " +
                    "LEFT JOIN generos g ON p.genero_id = g.id";
    
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }
    
            System.out.println("\nPlaylists con género:");
            do {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Título: " + rs.getString("titulo") +
                    " | Intérprete: " + rs.getString("interprete") +
                    " | Temas: " + rs.getInt("cantidad_temas") +
                    " | Duración: " + rs.getDouble("duracion_total") + " min" +
                    " | Género: " + rs.getString("nombre_genero")
                );
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists con género: " + e.getMessage());
        }
    }

    public void listarPlaylistsConGeneroOrdenadas(String campoOrden) {
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, " +
                    "p.duracion_total, g.nombre AS nombre_genero " +
                    "FROM playlist p " +
                    "LEFT JOIN generos g ON p.genero_id = g.id " +
                    "ORDER BY " + campoOrden;
    
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (!rs.next()) {
                System.out.println("No hay playlists cargadas.");
                return;
            }
    
            System.out.println("\nPlaylists ordenadas por " + campoOrden + ":");
            do {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Título: " + rs.getString("titulo") +
                    " | Intérprete: " + rs.getString("interprete") +
                    " | Temas: " + rs.getInt("cantidad_temas") +
                    " | Duración: " + rs.getDouble("duracion_total") + " min" +
                    " | Género: " + rs.getString("nombre_genero")
                );
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists ordenadas: " + e.getMessage());
        }
    }

    public boolean esCampoValido(String campoOrden) {
        String[] camposValidos = { "titulo", "interprete", "cantidad_temas", "duracion_total" };
        for (String campo : camposValidos) {
            if (campo.equalsIgnoreCase(campoOrden)) {
                return true;
            }
        }
        return false;
    }

    public void listarPlaylistsPorCriterio(String criterio, double valorMinimo) {
        // Consulta corregida usando genero_id en lugar de id_genero
        String sql = "SELECT p.id, p.titulo, p.interprete, p.cantidad_temas, " +
                    "p.duracion_total, g.nombre AS nombre_genero " +
                    "FROM playlist p " +
                    "LEFT JOIN generos g ON p.genero_id = g.id " +
                    "WHERE p." + criterio + " >= ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (criterio.equals("duracion_total")) {
                stmt.setDouble(1, valorMinimo);
            } else if (criterio.equals("cantidad_temas")) {
                stmt.setInt(1, (int) valorMinimo);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("\nNo hay playlists que cumplan con el criterio " + criterio + " >= " + valorMinimo);
                return;
            }
            
            System.out.println("\nPlaylists que cumplen con " + criterio + " >= " + valorMinimo + ":");
            do {
                System.out.println(
                    "ID: " + rs.getInt("id") +
                    " | Título: " + rs.getString("titulo") +
                    " | Intérprete: " + rs.getString("interprete") +
                    " | Temas: " + rs.getInt("cantidad_temas") +
                    " | Duración: " + rs.getDouble("duracion_total") + " min" +
                    " | Género: " + (rs.getString("nombre_genero") != null ? rs.getString("nombre_genero") : "Sin género")
                );
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar playlists por criterio: " + e.getMessage());
        }
    }

    public void mostrarEstadisticas() {
        String sql = "SELECT AVG(duracion_total) AS promedio_duracion, SUM(cantidad_temas) AS suma_temas FROM playlist";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                double promedioDuracion = rs.getDouble("promedio_duracion");
                int sumaTemas = rs.getInt("suma_temas");

                if (rs.wasNull()) { // Si no hay playlists, AVG y SUM devuelven NULL
                    System.out.println("No hay playlists cargadas para calcular estadísticas.");
                } else {
                    System.out.println("\n--- Estadísticas de Playlists ---");
                    System.out.printf("Promedio de duración total: %.2f minutos%n", promedioDuracion);
                    System.out.println("Suma total de temas: " + sumaTemas);
                }
            } else {
                System.out.println("No hay playlists cargadas.");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular estadísticas: " + e.getMessage());
        }
    }

}
