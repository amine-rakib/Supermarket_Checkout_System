package main;

import controller.POS;
import model.*;

public class Main {
    public static void main(String[] args) {
        POS pos = new POS();
        CashRegister register = new CashRegister(pos);

        // Création des catégories d'articles
        ItemCategory fruitCategory = new ItemCategory("Fruit", 0.10);
        ItemCategory dairyCategory = new ItemCategory("Dairy", 0.05);

        // Création du client avec sa position géographique (x_axis, y_axis) et son mode de paiement
        Customer customer = new Customer("Alice", "Dupont", "prime", 48.8566, 2.3522, "credit","1234");

        // Ajout d'articles (modification pour inclure `weight`)
        register.recordItem(new Item("001", "Banane", 1.5, 6, fruitCategory, 1.2)); // Poids total 1.2 kg
        register.recordItem(new Item("002", "Lait", 2.0, 2, dairyCategory, 2.0)); // Poids total 2 kg

        // Calcul du total et passage en caisse
        double total = register.computeTotal(customer);
        register.processPayment(customer, total);
    }
}
