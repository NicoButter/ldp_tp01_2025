import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n🎧 Spotify - Gestión de Playlists 🎶");
            System.out.println("1. Agregar Playlist");
            System.out.println("2. Listar Playlists");
            System.out.println("3. Salir");
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
                    
                    PlaylistDAO.agregarPlaylist(titulo, interprete, cantidadTemas, duracionTotal);
                    break;
                case 2:
                    PlaylistDAO.listarPlaylists();
                    break;
                case 3:
                    System.out.println("👋 ¡Hasta luego!");
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 3);

        scanner.close();
    }
}
