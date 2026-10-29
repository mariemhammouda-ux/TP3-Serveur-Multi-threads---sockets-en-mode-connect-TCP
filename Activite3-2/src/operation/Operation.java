package operation;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private double operande1;
    private char operateur;
    private double operande2;
    private double resultat;
    private String erreur;
    private int numeroOperation;

    public Operation(double operande1, char operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public double getOperande1() {
        return operande1;
    }

    public char getOperateur() {
        return operateur;
    }

    public double getOperande2() {
        return operande2;
    }

    public double getResultat() {
        return resultat;
    }

    public void setResultat(double resultat) {
        this.resultat = resultat;
    }

    public String getErreur() {
        return erreur;
    }

    public void setErreur(String erreur) {
        this.erreur = erreur;
    }

    public int getNumeroOperation() {
        return numeroOperation;
    }

    public void setNumeroOperation(int numeroOperation) {
        this.numeroOperation = numeroOperation;
    }
}