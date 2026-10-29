package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import thread.ClientProcess;

public class Server {
    public static int compteur = 0;
    private static int numeroClient = 0;

    public static synchronized int incrementerCompteur() {
        compteur++;
        return compteur;
    }

    public static synchronized int attribuerNumeroClient() {
        numeroClient++;
        return numeroClient;
    }

    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur calculatrice démarré sur le port " + port);
            System.out.println("En attente des clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                int numero = attribuerNumeroClient();

                System.out.println("Client n°" + numero + " connecté");
                System.out.println("Adresse : " + socket.getRemoteSocketAddress());

                ClientProcess clientProcess = new ClientProcess(socket, numero);
                clientProcess.start();
            }
        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}