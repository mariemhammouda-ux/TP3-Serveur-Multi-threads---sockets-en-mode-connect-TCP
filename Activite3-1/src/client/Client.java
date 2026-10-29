package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) {
        String adresseServeur = "localhost";
        int port = 1234;
        if (args.length > 0) {
            adresseServeur = args[0];
        }
        try {
            InetAddress adresseIP = InetAddress.getByName(adresseServeur);
            InetSocketAddress adresseSocket = new InetSocketAddress(adresseIP, port);
            Socket socket = new Socket();
            socket.connect(adresseSocket);
            try (
                    Socket clientSocket = socket;
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    Scanner scanner = new Scanner(System.in)
            ) {
                System.out.println("Connecté au serveur : " + adresseIP.getHostAddress());
                String bienvenue = in.readLine();
                System.out.println(bienvenue);
                String message;
                do {
                    System.out.print("Votre message : ");
                    message = scanner.nextLine();
                    out.println(message);
                    String reponse = in.readLine();
                    if (reponse == null) {
                        System.out.println("Serveur déconnecté.");
                        break;
                    }
                    System.out.println(reponse);
                } while (!message.equalsIgnoreCase("exit"));
            }
        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}