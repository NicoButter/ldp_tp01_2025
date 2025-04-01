/**
 * Capa de servicio para la gestión de géneros musicales
 * <p>
 * Proporciona métodos para operaciones CRUD sobre géneros musicales,
 * validando las reglas de negocio antes de acceder a la capa de persistencia.
 * </p>
 * 
 * @author Nicolas Butterfield
 * @version 1.0
 * @since Marzo 2025
 */

package playlist.service;

import playlist.dao.GeneroDAO;

public class GeneroService {
    private final GeneroDAO generoDAO;

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    public GeneroService() {
        this.generoDAO = new GeneroDAO();
    }

    public void agregarGenero(String nombre) {
        validarNombre(nombre);
        generoDAO.agregarGenero(nombre);
    }

    public void listarGeneros() {
        generoDAO.listarGeneros();
    }

    public boolean editarGenero(String nombreActual, String nuevoNombre) {
        validarNombre(nombreActual);
        validarNombre(nuevoNombre);
        
        int id = generoDAO.buscarIdPorNombre(nombreActual);
        if (id == -1) {
            throw new IllegalArgumentException("El género '" + nombreActual + "' no existe");
        }
        
        return generoDAO.editarGenero(id, nuevoNombre);
    }

    public boolean editarGenero(int id, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
        }
        return generoDAO.editarGenero(id, nuevoNombre);
    }

    public boolean eliminarGenero(String nombre) {
        validarNombre(nombre);
        int id = generoDAO.buscarIdPorNombre(nombre);
        if (id == -1) {
            throw new IllegalArgumentException("El género '" + nombre + "' no existe");
        }
        return generoDAO.eliminarGenero(id);
    }

    public boolean eliminarGenero(int id) {
        return generoDAO.eliminarGenero(id);
    }

    public int existeGenero(String nombre) {
        validarNombre(nombre);
        return generoDAO.buscarIdPorNombre(nombre);
    }

    public boolean existeGenero(int id) {
        return generoDAO.existeGenero(id); // Asumiendo que existe este método en GeneroDAO
    }


}