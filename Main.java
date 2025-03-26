import java.util.Scanner;

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
        PlaylistDAO playlistDAO = new PlaylistDAO();
        int opcion = -1;

        do {
            limpiarPantalla();
            System.out.println("\nSpotify - Gestión de Playlists");
            System.out.println("1. Agregar Playlist");
            System.out.println("2. Listar Playlists");
            System.out.println("3. Editar Playlist");
            System.out.println("4. Eliminar Playlist");
            System.out.println("5. Listar Playlists Ordenadas");
            System.out.println("6. Listar Playlists con Género");
            System.out.println("7. Listar Playlists con Género Ordenadas");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("¡Por favor ingresa un número válido!");
                scanner.next();
                System.out.print("Elige una opción: ");
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
                        System.out.println("¡Por favor ingresa un número válido para la cantidad de temas!");
                        scanner.next();
                    }
                    int cantidadTemas = scanner.nextInt();
                    System.out.print("Duración total en minutos: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("¡Por favor ingresa un número válido para la duración total!");
                        scanner.next();
                    }
                    double duracionTotal = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("ID del género: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para el ID del género!");
                        scanner.next();
                    }
                    int idGenero = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    playlistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal, idGenero);
                    break;
                case 2:
                    playlistDAO.listarPlaylists();
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("ID de la playlist a editar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next();
                    }
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();

                    if (!playlistDAO.existePlaylist(idEditar)) {
                        System.out.println("La playlist con ID " + idEditar + " no existe.");
                        break;
                    }

                    System.out.println("Vas a editar la playlist con ID: " + idEditar);
                    System.out.print("Nuevo título: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Nuevo intérprete: ");
                    String nuevoInterprete = scanner.nextLine();
                    System.out.print("Nueva cantidad de temas: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la cantidad de temas!");
                        scanner.next();
                    }
                    int nuevaCantidadTemas = scanner.nextInt();
                    System.out.print("Nueva duración total en minutos: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("¡Por favor ingresa un número válido para la duración total!");
                        scanner.next();
                    }
                    double nuevaDuracionTotal = scanner.nextDouble();

                    boolean editado = playlistDAO.editarPlaylist(idEditar, nuevoTitulo, nuevoInterprete, nuevaCantidadTemas, nuevaDuracionTotal);

                    if (editado) {
                        System.out.println("Playlist actualizada con éxito.");
                    } else {
                        System.out.println("No se pudo actualizar la playlist.");
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("ID de la playlist a eliminar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next();
                    }
                    int idEliminar = scanner.nextInt();

                    boolean eliminado = playlistDAO.eliminarPlaylist(idEliminar);

                    if (eliminado) {
                        System.out.println("Playlist eliminada con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar la playlist.");
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Campo por el cual deseas ordenar (titulo, interprete, cantidad_temas, duracion_total): ");
                    String campoOrden = scanner.nextLine();

                    if (!playlistDAO.esCampoValido(campoOrden)) {
                        System.out.println("Campo de ordenación no válido.");
                    } else {
                        playlistDAO.listarPlaylistsOrdenadas(campoOrden);
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 6:
                    playlistDAO.listarPlaylistsConGenero();
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 7:
                    System.out.print("Campo por el cual deseas ordenar (titulo, interprete, cantidad_temas, duracion_total): ");
                    String campoOrdenGenero = scanner.nextLine();

                    if (!playlistDAO.esCampoValido(campoOrdenGenero)) {
                        System.out.println("Campo de ordenación no válido.");
                    } else {
                        playlistDAO.listarPlaylistsConGeneroOrdenadas(campoOrdenGenero);
                    }
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    break;
                case 8:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 8);

        scanner.close();
    }
}