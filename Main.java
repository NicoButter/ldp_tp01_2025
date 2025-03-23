import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistDAO playlistDAO = new PlaylistDAO();
        int opcion = -1;

        do {
            System.out.println("\nSpotify - Gestión de Playlists");
            System.out.println("1. Agregar Playlist");
            System.out.println("2. Listar Playlists");
            System.out.println("3. Editar Playlist");
            System.out.println("4. Eliminar Playlist");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            
            // Validar que la opción sea un número
            while (!scanner.hasNextInt()) {
                System.out.println("¡Por favor ingresa un número válido!");
                scanner.next(); // Limpiar la entrada inválida
                System.out.print("Elige una opción: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Intérprete: ");
                    String interprete = scanner.nextLine();
                    System.out.print("Cantidad de temas: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la cantidad de temas!");
                        scanner.next(); // Limpiar entrada inválida
                    }
                    int cantidadTemas = scanner.nextInt();
                    System.out.print("Duración total en minutos: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("¡Por favor ingresa un número válido para la duración total!");
                        scanner.next(); // Limpiar entrada inválida
                    }
                    double duracionTotal = scanner.nextDouble();
                    
                    playlistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal);  
                    break;
                case 2:
                    playlistDAO.listarPlaylists();  
                    break;
                case 3:
                    System.out.print("ID de la playlist a editar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next(); // Limpiar entrada inválida
                    }
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();  // Limpiar buffer
                    
                    // Verificar si la playlist con esa ID existe
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
                        scanner.next(); // Limpiar entrada inválida
                    }
                    int nuevaCantidadTemas = scanner.nextInt();
                    
                    System.out.print("Nueva duración total en minutos: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("¡Por favor ingresa un número válido para la duración total!");
                        scanner.next(); // Limpiar entrada inválida
                    }
                    double nuevaDuracionTotal = scanner.nextDouble();
                
                    boolean editado = playlistDAO.editarPlaylist(idEditar, nuevoTitulo, nuevoInterprete, nuevaCantidadTemas, nuevaDuracionTotal);
                    
                    if (editado) {
                        System.out.println("Playlist actualizada con éxito.");
                    } else {
                        System.out.println("No se pudo actualizar la playlist.");
                    }
                    break;
                case 4:
                    System.out.print("ID de la playlist a eliminar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("¡Por favor ingresa un número válido para la ID!");
                        scanner.next(); // Limpiar entrada inválida
                    }
                    int idEliminar = scanner.nextInt();
                    
                    boolean eliminado = playlistDAO.eliminarPlaylist(idEliminar);
                    
                    if (eliminado) {
                        System.out.println("Playlist eliminada con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar la playlist.");
                    }
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);  

        scanner.close();
    }
}
