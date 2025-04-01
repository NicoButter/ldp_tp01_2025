/**
 * Capa de servicio para la gestión de playlists musicales
 * <p>
 * Contiene la lógica de negocio para operaciones con playlists,
 * incluyendo validaciones y coordinación con el servicio de géneros.
 * </p>
 * 
 * @author Nicolás Butterfield
 * @version 1.0
 * @since Marzo 2025
 */

package playlist.service;

import playlist.dao.PlaylistDAO;

public class PlaylistService {
    private PlaylistDAO playlistDAO;
    private GeneroService generoService;

    public PlaylistService() {
        this.playlistDAO = new PlaylistDAO();
        this.generoService = new GeneroService();
    }

    public boolean agregarPlaylist(String titulo, String interprete, int cantidadTemas, 
                                  double duracionTotal, String nombreGenero) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (interprete == null || interprete.trim().isEmpty()) {
            throw new IllegalArgumentException("El intérprete no puede estar vacío");
        }
        if (cantidadTemas <= 0) {
            throw new IllegalArgumentException("La cantidad de temas debe ser mayor a cero");
        }
        if (duracionTotal <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a cero");
        }
        
        int idGenero = generoService.existeGenero(nombreGenero);
        if (idGenero == -1) {
            throw new IllegalArgumentException("El género '" + nombreGenero + "' no está registrado.");
        }
        
        playlistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal, nombreGenero);

        return true;
    }

    public void listarPlaylists() {
        playlistDAO.listarPlaylists();
    }

    public boolean editarPlaylist(int id, String nuevoTitulo, String nuevoInterprete, 
                                int nuevaCantidadTemas, double nuevaDuracionTotal, 
                                String nuevoNombreGenero) {
        if (!playlistDAO.existePlaylist(id)) {
            throw new IllegalArgumentException("No existe una playlist con ID: " + id);
        }
        
        int idGenero = generoService.existeGenero(nuevoNombreGenero.toLowerCase());
        if (idGenero == -1) {
            throw new IllegalArgumentException("El género '" + nuevoNombreGenero + "' no existe");
        }
        
        return playlistDAO.editarPlaylist(id, nuevoTitulo, nuevoInterprete, 
                                        nuevaCantidadTemas, nuevaDuracionTotal, 
                                        nuevoNombreGenero);
    }

    public boolean eliminarPlaylist(int id) {
        if (!playlistDAO.existePlaylist(id)) {
            throw new IllegalArgumentException("No existe una playlist con ID: " + id);
        }
        return playlistDAO.eliminarPlaylist(id);
    }

    public void listarPlaylistsOrdenadas(String campoOrden) {
        if (!playlistDAO.esCampoValido(campoOrden)) {
            throw new IllegalArgumentException("Campo de ordenación inválido: " + campoOrden);
        }
        playlistDAO.listarPlaylistsOrdenadas(campoOrden);
    }

    public void listarPlaylistsConGenero() {
        playlistDAO.listarPlaylistsConGenero();
    }

    public void listarPlaylistsConGeneroOrdenadas(String campoOrden) {
        if (!playlistDAO.esCampoValido(campoOrden)) {
            throw new IllegalArgumentException("Campo de ordenación inválido: " + campoOrden);
        }
        playlistDAO.listarPlaylistsConGeneroOrdenadas(campoOrden);
    }

    public void listarPlaylistsPorCriterio(String criterio, double valorMinimo) {
        if (!criterio.equals("duracion_total") && !criterio.equals("cantidad_temas")) {
            throw new IllegalArgumentException("Criterio no válido: " + criterio);
        }
        if (valorMinimo < 0) {
            throw new IllegalArgumentException("El valor mínimo no puede ser negativo");
        }
        playlistDAO.listarPlaylistsPorCriterio(criterio, valorMinimo);
    }

    public void mostrarEstadisticas() {
        playlistDAO.mostrarEstadisticas();
    }

    public boolean existePlaylist(int id) {
        return playlistDAO.existePlaylist(id);
    }

    public String obtenerNombrePlaylist(int id) {
        return playlistDAO.obtenerNombrePlaylist(id);
    }
}