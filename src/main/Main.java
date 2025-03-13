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

        // Création et ajout d'articles dans le panier
        FruitItem bananes = new FruitItem("001", "Bananes Bio", 1.5, 6, 1.2, true);
        DairyItem lait = new DairyItem("002", "Lait Frais", 2.0, 2, 2.0, 3);
        MeatItem steak = new MeatItem("003", "Steak Haché", 5.0, 2, 0.5, false);

        // Enregistrement des articles
        register.recordItem(bananes);  // 6 bananes bio avec 15% de remise
        register.recordItem(lait);     // 2 bouteilles de lait, pas de remise car > 2 jours
        register.recordItem(steak);    // 2 steaks congelés avec 10% de remise

        // Calcul du total (avec remises articles + remise client prime 20%)
        double total = register.computeTotal(customer);
        
        // Affichage du total avant paiement
        System.out.println("\nTotal à payer : " + total + "€");

        // Processus de paiement
        register.processPayment(customer, total);

        // Vérification finale du solde
        System.out.println("\nSolde final du compte : " + account.getBalance(card) + "€");
        
        // Finalisation de la vente
        register.completeSale();
    }
}