import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistDAO playlistDAO = new PlaylistDAO();
        int opcion;

        do {
            System.out.println("\nSpotify - Gestión de Playlists");
            System.out.println("1. Agregar Playlist");
            System.out.println("2. Listar Playlists");
            System.out.println("3. Editar Playlist");
            System.out.println("4. Eliminar Playlist");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Intérprete: ");
                    String interprete = scanner.nextLine();
                    System.out.print("Cantidad de temas: ");
                    int cantidadTemas = scanner.nextInt();
                    System.out.print("Duración total en minutos: ");
                    double duracionTotal = scanner.nextDouble();
                    
                    playlistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal);  // Uso de la instancia
                    break;
                case 2:
                    playlistDAO.listarPlaylists();  
                    break;
                    case 3:
                    System.out.print("ID de la playlist a editar: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea pendiente
                
                    System.out.print("Nuevo título: ");
                    String nuevoTitulo = scanner.nextLine();
                    
                    System.out.print("Nuevo intérprete: ");
                    String nuevoInterprete = scanner.nextLine();
                    
                    System.out.print("Nueva cantidad de temas: ");
                    int nuevaCantidadTemas = scanner.nextInt();
                    
                    System.out.print("Nueva duración total en minutos: ");
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
        } while (opcion != 5);  // Cambié el fin del ciclo a opción 5, no a 3.

        scanner.close();
    }
}