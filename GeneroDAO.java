import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneroDAO {

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
        String sql = "UPDATE generos SET NOMBRE = ? WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el género: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarGenero(int id) {
        String sql = "DELETE FROM generos WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el género: " + e.getMessage());
            return false;
        }
    }

    public boolean existeGenero(int id) {
        String sql = "SELECT COUNT(*) FROM generos WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del género: " + e.getMessage());
        }
        return false;
    }
}