package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import operation.Operation;

public class Client {

    public static void main(String[] args) {

        String adresseServeur = "192.168.100.7";
        int port = 1234;

        if (args.length > 0) {
            adresseServeur = args[0];
        }

        try {
            InetAddress adresseIP = InetAddress.getByName(adresseServeur);
            InetSocketAddress adresseSocket =
                    new InetSocketAddress(adresseIP, port);

            try (Socket socket = new Socket()) {

                socket.connect(adresseSocket);

                System.out.println(
                        "Connecté au serveur : " + adresseIP.getHostAddress()
                );

                try (
                        ObjectOutputStream out =
                                new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in =
                                new ObjectInputStream(socket.getInputStream());
                        Scanner scanner = new Scanner(System.in)
                ) {

                    out.flush();

                    Object bienvenue = in.readObject();

                    if (bienvenue instanceof String) {
                        System.out.println(bienvenue);
                    }

                    while (true) {

                        System.out.print(
                                "Opérateur (+, -, *, /) ou exit : "
                        );

                        String operateur = scanner.nextLine().trim();

                        if (operateur.equalsIgnoreCase("exit")) {
                            out.writeObject("FIN");
                            out.flush();
                            break;
                        }

                        if (!operateur.equals("+")
                                && !operateur.equals("-")
                                && !operateur.equals("*")
                                && !operateur.equals("/")) {

                            System.out.println("Opérateur invalide.");
                            continue;
                        }

                        double a;
                        double b;

                        try {
                            System.out.print("Premier nombre : ");
                            a = Double.parseDouble(scanner.nextLine());

                            System.out.print("Deuxième nombre : ");
                            b = Double.parseDouble(scanner.nextLine());

                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Veuillez saisir des nombres valides."
                            );
                            continue;
                        }

                        Operation operation =
                                new Operation(a, operateur.charAt(0), b);

                        out.writeObject(operation);
                        out.flush();

                        Object reponse = in.readObject();

                        if (reponse instanceof String) {
                            System.out.println("Résultat : " + reponse);
                        } else {
                            System.out.println(
                                    "Réponse inattendue du serveur."
                            );
                        }

                        System.out.println();
                    }

                    System.out.println("Déconnexion du serveur.");

                } catch (ClassNotFoundException e) {
                    System.out.println("Erreur : classe reçue inconnue.");
                }
            }

        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
            System.out.println(
                    "Vérifiez l'adresse IP, le serveur et le port 1234."
            );
        }
    }
}