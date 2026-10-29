package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import thread.ClientProcess;
public class Server {
    public static void main(String[] args) {
        int port = 1234;
        int numeroClient = 0;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur multithread démarré sur le port " + port);
            System.out.println("En attente des clients...");
            while (true) {
                Socket socket = serverSocket.accept();
                numeroClient++;
                System.out.println("Client n°" + numeroClient + " connecté");
                System.out.println("Adresse : " + socket.getRemoteSocketAddress());
                ClientProcess clientProcess = new ClientProcess(socket, numeroClient);
                clientProcess.start();
            }
        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
