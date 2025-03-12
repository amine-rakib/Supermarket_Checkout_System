package controller;

import model.PaymentTransaction;
import model.Customer;
import java.util.Scanner;

public class POS {
    private Scanner scanner;

    public POS() {
        this.scanner = new Scanner(System.in);
    }
    public boolean processPayment(PaymentTransaction transaction, Customer customer) {

        // Vériication que la carte est bien une carte bancaire : 
        if (!customer.getPaymentMethod().equals("debit") && !customer.getPaymentMethod().equals("credit")) {
            System.out.println("Méthode de paiement non reconnue.");
            return false;
        }
        System.out.println("Carte bancaire détectée.");

        // Demande du code PIN
        System.out.println("Entrez votre code PIN : ");
        String enteredPin = scanner.nextLine();
        System.out.println("Code PIN vérifié.");

        // Vérification du pin 
        if (!verifyPin(customer, enteredPin)) {
            System.out.println("Erreur : Code PIN incorrect. Paiement annulé.");
            return false;
        }
        System.out.println("Code PIN correct.");

        // Demande d'autorisation de paiement
        System.out.println("Demande d'autorisation de paiement en cours...");
        transaction.setPaymentMethod(customer.getPaymentMethod());
        transaction.setStatus("Pending");
        
        boolean authorized = transaction.authorizePayment();
        if (!authorized) {
            System.out.println("Paiement refusé par la banque.");
            return false;
        }
        System.out.println("Paiement autorisé par la banque.");

        System.out.print("Souhaitez-vous un reçu ? (Oui/Non) : ");
        String receiptChoice = scanner.nextLine().toLowerCase();
        boolean printReceipt = receiptChoice.equals("oui");

        // Étape 6 : Finalisation
        if (printReceipt) {
            System.out.println("Impression du reçu en cours...");
        }
        System.out.println("Transaction terminée. Veuillez récupérer votre carte.");
        return true;
    }   

    private boolean verifyPin(Customer customer, String enteredPin) {
        return enteredPin.equals(customer.getPinCode()); // Le code PIN est stocké dans Customer
    }
}
