package main;

import model.*;

public class Main {
    public static void main(String[] args) {
        // Initialisation du système bancaire (TAS)
        TAS tas = new TAS();
        
        // Création du terminal de paiement connecté au TAS
        POS pos = new POS(tas);

        // Création de la caisse enregistreuse
        CashRegister register = new CashRegister(pos);

        // Création d'un compte bancaire (100€ de solde, 50€ de crédit autorisé)
        BankAccount account = new BankAccount("123456789", 100.0, 50.0); 

        // Création d'une carte bancaire liée au compte (Carte de crédit)
        BankCard card = new BankCard("987654321", "1234", "Credit", account);

        // Création d'un client avec sa carte bancaire et sa localisation
        Customer customer = new Customer("Alice", "Dupont", "prime", 48.8566, 2.3522, card);

        // Ajout d'articles dans le panier
        register.recordItem(new Item("001", "Banane", 1.5, 6, new ItemCategory("Fruit", 0.10), 1.2)); // 6 bananes
        register.recordItem(new Item("002", "Lait", 2.0, 2, new ItemCategory("Dairy", 0.05), 2.0)); // 2 bouteilles de lait

        // Calcul du total et passage en caisse
        double total = register.computeTotal(customer);
        register.processPayment(customer, total);

        // Vérification finale du solde du compte bancaire
        System.out.println("Solde final du compte : " + account.getBalance(card) + "€");
    }
}
