/**
 * Capa de acceso a datos para la entidad Género
 * <p>
 * Gestiona todas las operaciones de persistencia con la base de datos
 * para la tabla de géneros musicales.
 * </p>
 * 
 * @author Nicolas Butterfield
 * @version 1.0
 * @since Marzo 2025
 */

package playlist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import playlist.config.DatabaseConfig;


public class GeneroDAO {

    public int buscarIdPorNombre(String nombre) {
        String sql = "SELECT id FROM generos WHERE LOWER(nombre) = LOWER(?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar género: " + e.getMessage());
        }
        return -1;
    }

    public void agregarGenero(String nombre) {
        String sql = "INSERT INTO generos (NOMBRE) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            System.out.println("Género agregado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar género: " + e.getMessage());
        }
    }

    public void listarGeneros() {
        String sql = "SELECT * FROM generos";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                System.out.println("No hay géneros cargados.");
                return;
            }
            System.out.println("\nGéneros en la base de datos:");
            do {
                System.out.println("ID: " + rs.getInt("ID") +
                        " | Nombre: " + rs.getString("NOMBRE"));
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error al listar géneros: " + e.getMessage());
        }
    }

    public boolean editarGenero(int id, String nuevoNombre) {
        String sql = "UPDATE generos SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al editar género: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarGenero(int id) {
        Connection conn = null;
    try {
        conn = DatabaseConfig.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción

        // 1. Actualizar playlists asociadas para que queden sin género
        String updateSql = "UPDATE playlist SET genero_id = NULL WHERE genero_id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setInt(1, id);
            updateStmt.executeUpdate();
        }

        // 2. Eliminar el género
        String deleteSql = "DELETE FROM generos WHERE id = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit(); // Confirmar transacción
                return true;
            }
            return false;
        }
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback(); // Revertir en caso de error
            } catch (SQLException ex) {
                System.out.println("Error al revertir transacción: " + ex.getMessage());
            }
        }
        System.out.println("Error al eliminar género: " + e.getMessage());
        return false;
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Restaurar autocommit
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    }

    public int existeGenero(String nombre) {
        String sql = "SELECT ID FROM generos WHERE LOWER(NOMBRE) = LOWER(?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar género por nombre: " + e.getMessage());
        }
        return -1;
    }

    public boolean existeGenero(int id) {
        String sql = "SELECT COUNT(*) FROM generos WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar género por ID: " + e.getMessage());
        }
        return false;
    }


}