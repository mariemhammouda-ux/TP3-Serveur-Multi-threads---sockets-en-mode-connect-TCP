package thread;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import operation.Operation;
import server.Server;

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
                ObjectOutputStream out =
                        new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            out.flush();

            try (
                    ObjectInputStream in =
                            new ObjectInputStream(clientSocket.getInputStream())
            ) {

                System.out.println(
                        "Thread du client n°" + numeroClient + " démarré"
                );

                while (true) {

                    Object objet = in.readObject();

                    if (!(objet instanceof Operation)) {
                        System.out.println(
                                "Objet invalide reçu du client n°"
                                        + numeroClient
                        );
                        continue;
                    }

                    Operation operation = (Operation) objet;

                    double a = operation.getOperande1();
                    double b = operation.getOperande2();
                    char operateur = operation.getOperateur();

                    try {
                        double resultat;

                        switch (operateur) {
                            case '+':
                                resultat = a + b;
                                break;

                            case '-':
                                resultat = a - b;
                                break;

                            case '*':
                                resultat = a * b;
                                break;

                            case '/':
                                if (b == 0) {
                                    throw new ArithmeticException("Division par zéro impossible");
                                }
                                resultat = a / b;
                                break;

                            default:
                                throw new IllegalArgumentException("Opérateur invalide");
                        }

                        operation.setResultat(resultat);
                        operation.setErreur(null);

                        int numeroOperation =
                                Server.incrementerCompteur();

                        operation.setNumeroOperation(numeroOperation);

                        System.out.println(
                                "Client n°" + numeroClient + " : "
                                        + a + " " + operateur + " " + b
                                        + " = " + resultat
                        );

                        System.out.println(
                                "Nombre total d'opérations : "
                                        + numeroOperation
                        );

                    } catch (
                            ArithmeticException | IllegalArgumentException e
                    ) {
                        operation.setErreur(e.getMessage());
                        operation.setNumeroOperation(0);

                        System.out.println(
                                "Erreur pour le client n°" + numeroClient
                                        + " : " + e.getMessage()
                        );
                    }

                    out.writeObject(operation);
                    out.flush();
                }

            } catch (EOFException e) {
                System.out.println(
                        "Client n°" + numeroClient + " déconnecté"
                );
            } catch (ClassNotFoundException e) {
                System.out.println(
                        "Classe d'objet inconnue pour le client n°"
                                + numeroClient
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Erreur client n°" + numeroClient + " : "
                            + e.getMessage()
            );
        }
    }
}