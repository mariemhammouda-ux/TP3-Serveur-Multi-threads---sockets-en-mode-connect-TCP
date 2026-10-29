package thread;
import java.io.*;
import java.net.Socket;
public class ClientProcess extends Thread {
    private Socket socket;
    private int numeroClient;
    public ClientProcess(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }
    @Override
    public void run() {
        try (
                Socket clientSocket = socket;
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            out.println("Bienvenue client n°" + numeroClient);
            System.out.println("Thread du client n°" + numeroClient + " démarré");
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client n°" + numeroClient + " : " + message);
                out.println("Serveur : message reçu -> " + message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur client n°" + numeroClient + " : " + e.getMessage());
        }
        System.out.println("Client n°" + numeroClient + " déconnecté");
    }
}