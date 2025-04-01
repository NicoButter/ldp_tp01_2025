/**
 * Sistema de Gestión de Playlists de Spotyfi Caso de uso asignado - TP1 Parte 1
 * <p>
 * Trabajo Práctico N°1 de la materia Laboratorio de Programación (2025) 
 * de la Licenciatura en Sistemas de la Universidad Nacional de la Patagonia Austral (UNPA-UARG).
 * </p>
 * <p>
 * Implementa un menú interactivo para gestión de playlists y géneros musicales con persistencia en base de datos.
 * 
 * </p>
 * 
 * @author Nicolás Butterfield
 * @version 1.0
 * @since Marzo 2025
 */

package playlist;

import java.sql.SQLException;
import java.util.Scanner;

import playlist.config.DatabaseReset;
import playlist.service.GeneroService;
import playlist.service.PlaylistService;

public class Main {

    public static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la pantalla.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistService playlistService = new PlaylistService();
        GeneroService generoService = new GeneroService();
        int opcion = -1;

        do {
            limpiarPantalla();
            System.out.println("\n-----------------------------------");
            System.out.println("Spotify - Gestión de Playlists -");
            System.out.println("-----------------------------------");
            System.out.println("1. Agregá una Playlist");
            System.out.println("2. Listá las Playlists");
            System.out.println("3. Editá una Playlist");
            System.out.println("4. Eliminá una Playlist");
            System.out.println("5. Listá una Playlists de forma ORDENADA");
            System.out.println("6. Listá una Playlists de un Género a elección");
            System.out.println("7. Listá una Playlists con Género Ordenadas");
            System.out.println("8. CRUD para géneros");
            System.out.println("10. Listar Playlists por Criterio");
            System.out.println("11. Estadísticas de Playlists");
            System.out.println("12. Factory Reset (Reiniciar base de datos)");
            System.out.println("13. Salir");
            System.out.print("Elige una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("¡Por favor ingresá un número válido! :o");
                scanner.next();
                System.out.print("Elegí una opción: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Intérprete: ");
                    String interprete = scanner.nextLine();
                    System.out.print("Cantidad de temas: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresá un número válido para la cantidad de temas!");
                        scanner.next();
                    }
                    int cantidadTemas = scanner.nextInt();
                    System.out.print("Duración total en minutos: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("¡Por favor ingresá un número válido para la duración total!");
                        scanner.next();
                    }
                    double duracionTotal = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Nombre del género: ");
                    String nombreGenero = scanner.nextLine();

                    try {
                        playlistService.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal, nombreGenero);
                        System.out.println("Playlist agregada con éxito :)");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 2:
                    playlistService.listarPlaylists();
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.print("ID de la playlist a editar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresá un número válido para la ID!");
                        scanner.next();
                    }
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        System.out.println("Vas a editar la playlist con ID: " + idEditar);
                        System.out.print("Nuevo título: ");
                        String nuevoTitulo = scanner.nextLine();
                        System.out.print("Nuevo intérprete: ");
                        String nuevoInterprete = scanner.nextLine();
                        System.out.print("Nueva cantidad de temas: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("¡Por favor ingresá un número válido para la cantidad de temas!");
                            scanner.next();
                        }
                        int nuevaCantidadTemas = scanner.nextInt();
                        System.out.print("Nueva duración total en minutos: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("¡Por favor ingresá un número válido para la duración total!");
                            scanner.next();
                        }
                        double nuevaDuracionTotal = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Nuevo nombre del género: ");
                        String nuevoNombreGenero = scanner.nextLine();

                        boolean editado = playlistService.editarPlaylist(idEditar, nuevoTitulo, nuevoInterprete,
                                nuevaCantidadTemas, nuevaDuracionTotal, nuevoNombreGenero);

                        if (editado) {
                            System.out.println("Playlist actualizada con éxito :)");
                        } else {
                            System.out.println("No se pudo actualizar la playlist :(");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.print("ID de la playlist a eliminar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresá un número válido para la ID!");
                        scanner.next();
                    }
                    int idEliminar = scanner.nextInt();

                    try {
                        boolean eliminado = playlistService.eliminarPlaylist(idEliminar);
                        if (eliminado) {
                            System.out.println("Playlist eliminada con éxito :)");
                        } else {
                            System.out.println("No se pudo eliminar la playlist :(");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 5:
                    System.out.print(
                            "Ingresá el campo por el cual deseas ordenar (titulo, interprete, cantidad_temas, duracion_total): ");
                    String campoOrden = scanner.nextLine();

                    try {
                        playlistService.listarPlaylistsOrdenadas(campoOrden);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 6:
                    playlistService.listarPlaylistsConGenero();
                    System.out.println("\nPresioná ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 7:
                    System.out.print(
                            "Ingresá el campo por el cual deseas ordenar (titulo, interprete, cantidad_temas, duracion_total): ");
                    String campoOrdenGenero = scanner.nextLine();

                    try {
                        playlistService.listarPlaylistsConGeneroOrdenadas(campoOrdenGenero);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná ENTER para continuar...");
                    scanner.nextLine();
                    break;

                case 8:
                    menuGeneros(scanner, generoService); // Pasamos el service en lugar del DAO
                    break;

                case 10:
                    System.out.println("Criterios disponibles: ");
                    System.out.println("1. Duración total (en minutos)");
                    System.out.println("2. Cantidad de temas");
                    System.out.print("Elegí un criterio (1 o 2): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresá un número válido!");
                        scanner.next();
                    }
                    int criterio = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        if (criterio == 1) {
                            System.out.print("Ingresá la duración mínima en minutos: ");
                            while (!scanner.hasNextDouble()) {
                                System.out.println("¡Por favor ingresá un número válido para la duración!");
                                scanner.next();
                            }
                            double duracionMinima = scanner.nextDouble();
                            scanner.nextLine();
                            playlistService.listarPlaylistsPorCriterio("duracion_total", duracionMinima);
                        } else if (criterio == 2) {
                            System.out.print("Ingresá la cantidad mínima de temas: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("¡Por favor ingresá un número válido para la cantidad de temas!");
                                scanner.next();
                            }
                            int cantidadMinima = scanner.nextInt();
                            scanner.nextLine();
                            playlistService.listarPlaylistsPorCriterio("cantidad_temas", cantidadMinima);
                        } else {
                            System.out.println("Criterio no válido. Elegí 1 o 2.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 11:
                    playlistService.mostrarEstadisticas();
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 12:
                    try {
                        System.out.println("¿Estás seguro que deseas resetear la base de datos? (s/n)");
                        String confirmacion = scanner.nextLine();
                        if (confirmacion.equalsIgnoreCase("s")) {
                            DatabaseReset.factoryReset();
                            System.out.println(
                                    "Base de datos reiniciada exitosamente. Se cargaron 10 playlists de ejemplo.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al resetear la base de datos: " + e.getMessage());
                    }
                    System.out.println("\nPresioná Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 13:
                    System.out.println("¡Hasta luego! ;)");
                    break;

                default:
                    System.out.println("Opción inválida :(.");
            }
        } while (opcion != 13);

        scanner.close();
    }

    public static void menuGeneros(Scanner scanner, GeneroService generoService) {
        int opcionGenero = -1;

        do {
            limpiarPantalla();
            System.out.println("\n--- Gestión de Géneros ---");
            System.out.println("1. Agregar Género");
            System.out.println("2. Listar Géneros");
            System.out.println("3. Editar Género");
            System.out.println("4. Eliminar Género");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Elige una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("¡Por favor ingresá un número válido!");
                scanner.next();
                System.out.print("Elegí una opción: ");
            }
            opcionGenero = scanner.nextInt();
            scanner.nextLine();

            switch (opcionGenero) {
                case 1:
                    System.out.print("Nombre del género: ");
                    String nombreGenero = scanner.nextLine();
                    try {
                        generoService.agregarGenero(nombreGenero);
                        System.out.println("Género agregado con éxito.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    generoService.listarGeneros();
                    System.out.println("\nPresiona ENTER para continuar...");
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("ID del género a editar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next();
                    }
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();

                    if (!generoService.existeGenero(idEditar)) {
                        System.out.println("El género con ID " + idEditar + " no existe.");
                        break;
                    }

                    System.out.print("Nuevo nombre del género: ");
                    String nuevoNombre = scanner.nextLine();

                    boolean editado = generoService.editarGenero(idEditar, nuevoNombre);

                    if (editado) {
                        System.out.println("Género actualizado con éxito.");
                    } else {
                        System.out.println("No se pudo actualizar el género.");
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("ID del género a eliminar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next();
                    }
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();

                    boolean eliminado = generoService.eliminarGenero(idEliminar);

                    if (eliminado) {
                        System.out.println("Género eliminado con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar el género.");
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.8");
            }
        } while (opcionGenero != 5);
    }

}
