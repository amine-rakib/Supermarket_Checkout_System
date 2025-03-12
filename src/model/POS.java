package model;

import java.util.Scanner;

public class POS {
    private Scanner scanner;
    private TAS tas;

    public POS(TAS tas) {
        this.scanner = new Scanner(System.in);
        this.tas = tas;
    }
    public boolean processPayment(PaymentTransaction transaction, Customer customer) {
        BankCard card = customer.getBankCard();
        BankAccount account = card.getLinkedAccount();

        // Vériication que la carte est bien une carte bancaire : 
        if (isValidBankCard(card)) {
            System.out.println("Carte bancaire détectée.");
        } else {
            System.out.println("Erreur : Carte non reconnue. Paiement annulé.");
            return false;
        }

        // Demande du code PIN
        System.out.println("Entrez votre code PIN : ");
        String enteredPin = scanner.nextLine();

        // Vérification du pin 
        if (!verifyPin(card, enteredPin)) {
            System.out.println("Erreur : Code PIN incorrect. Paiement annulé.");
            return false;
        }
        System.out.println("Code PIN correct.");

        // Ouverture d'une connexion sécurisée avec TAS
        tas.openSecureConnection();

        // Demande d'autorisation bancaire via TAS
        System.out.println("Demande d'autorisation bancaire en cours...");
        boolean authorized = tas.authorizeTransaction(transaction, account);
        if (authorized) {
            account.debit(transaction.getAmount());
        } else {
            System.out.println("Paiement refusé par la banque.");
            return false;
        }
        
        // Fermeture de la connexion sécurisée avec TAS
        tas.closeSecureConnection();


        // Impression du reçu
        System.out.print("Souhaitez-vous un reçu ? (Oui/Non) : ");
        String receiptChoice = scanner.nextLine().toLowerCase();
        boolean printReceipt = receiptChoice.equals("oui");

        // Finalisation
        if (printReceipt) {
            System.out.println("Impression du reçu en cours...");
        }
        System.out.println("Transaction terminée. Veuillez récupérer votre carte.");
        return true;
    }   

    private boolean isValidBankCard(BankCard card) {
        return card.getCardType().equalsIgnoreCase("Credit") || card.getCardType().equalsIgnoreCase("Debit");
    }

    private boolean verifyPin(BankCard card, String enteredPin) {
        return enteredPin.equals(card.getPinCode()); 
    }
}
