import java.util.InputMismatchException;
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
            
            opcion = obtenerOpcionValida(scanner);

            switch (opcion) {
                case 1:
                    String titulo = obtenerEntradaTextoValida(scanner, "Título: ");
                    String interprete = obtenerEntradaTextoValida(scanner, "Intérprete: ");
                    int cantidadTemas = obtenerNumeroValido(scanner, "Cantidad de temas: ");
                    double duracionTotal = obtenerNumeroDecimalValido(scanner, "Duración total en minutos: ");
                    
                    playlistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal);
                    break;
                case 2:
                    playlistDAO.listarPlaylists();
                    break;
                case 3:
                    System.out.print("ID de la playlist a editar: ");
                    int idEditar = obtenerNumeroValido(scanner, "");
                    scanner.nextLine(); 

                    String nuevoTitulo = obtenerEntradaTextoValida(scanner, "Nuevo título: ");
                    String nuevoInterprete = obtenerEntradaTextoValida(scanner, "Nuevo intérprete: ");
                    int nuevaCantidadTemas = obtenerNumeroValido(scanner, "Nueva cantidad de temas: ");
                    double nuevaDuracionTotal = obtenerNumeroDecimalValido(scanner, "Nueva duración total en minutos: ");

                    boolean editado = playlistDAO.editarPlaylist(idEditar, nuevoTitulo, nuevoInterprete, nuevaCantidadTemas, nuevaDuracionTotal);

                    if (editado) {
                        System.out.println("Playlist actualizada con éxito.");
                    } else {
                        System.out.println("No se pudo actualizar la playlist.");
                    }
                    break;
                case 4:
                    // Eliminar Playlist
                    System.out.print("ID de la playlist a eliminar: ");
                    int idEliminar = obtenerNumeroValido(scanner, "");

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

    private static int obtenerOpcionValida(Scanner scanner) {
        int opcion = 0;
        while (true) {
            try {
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <= 5) {
                    break; // Opción válida
                } else {
                    System.out.println("Por favor, ingresa un número entre 1 y 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número entre 1 y 5.");
                scanner.next(); 
            }
        }
        scanner.nextLine(); 
        return opcion;
    }

    private static String obtenerEntradaTextoValida(Scanner scanner, String mensaje) {
        String entrada = "";
        while (true) {
            System.out.print(mensaje);
            entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                break; 
            } else {
                System.out.println("La entrada no puede estar vacía. Intenta de nuevo.");
            }
        }
        return entrada;
    }

    private static int obtenerNumeroValido(Scanner scanner, String mensaje) {
        int numero = 0;
        while (true) {
            try {
                if (!mensaje.isEmpty()) {
                    System.out.print(mensaje);
                }
                numero = scanner.nextInt();
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número entero.");
                scanner.next(); 
            }
        }
        scanner.nextLine(); 
        return numero;
    }

    private static double obtenerNumeroDecimalValido(Scanner scanner, String mensaje) {
        double numero = 0;
        while (true) {
            try {
                System.out.print(mensaje);
                numero = scanner.nextDouble();
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número decimal.");
                scanner.next();
            }
        }
        scanner.nextLine(); 
        return numero;
    }
}
