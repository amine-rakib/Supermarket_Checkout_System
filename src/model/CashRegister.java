package model;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class CashRegister {
    private List<Item> items;
    private POS pos;
    private ItemVisitor priceCalculator;
    private DeliveryCalculator deliveryCalculator;
    private Scanner scanner;

    public CashRegister(POS pos) {
        this.items = new ArrayList<Item>();
        this.pos = pos;
        this.priceCalculator = new PriceCalculatorVisitor();
        this.deliveryCalculator = new StandardDeliveryCalculator();
        this.scanner = new Scanner(System.in);
    }


    public void recordItem(Item item) {
        items.add(item);
        double price = item.accept(priceCalculator);
        System.out.println("Enregistré : " + item.getLabel() + " | Prix  après remise : " + price);
    }

    public double computeTotal(Customer customer) {
        double subtotal = calculateSubtotal();
        double totalWeight = calculateTotalWeight();

        // Ajouter le prix de la livraison si le client répond oui
        System.out.print("Souhaitez-vous la livraison à domicile ? (oui/non): ");
        boolean wantsDelivery = scanner.nextLine().toLowerCase().startsWith("o");

        double deliveryCost = 0.0;
        if (wantsDelivery) {
            // Calcul du coût de livraison
            double distance = calculateDistance(customer.getX_axis(), customer.getY_axis());
            deliveryCost = deliveryCalculator.calculateDeliveryCost(totalWeight, distance, subtotal);
            
            // Application des remises sur la livraison selon le plan du client
            switch (customer.getPlan().toLowerCase()) {
                case "prime":
                    deliveryCost *= 0.5; // 50% de réduction
                    break;
                case "platinum":
                    deliveryCost = 0.0; // Gratuit
                    break;
            }
        }

        // Application de la remise client sur le sous-total
        double discountedSubtotal = customer.applyDiscount(subtotal);
        double total = discountedSubtotal + deliveryCost;

        // Affichage du détail si le client répond oui 
        System.out.print("Souhaitez-vous voir imprimer un reçu ? (oui/non): ");
        boolean wantsReceipt = scanner.nextLine().toLowerCase().startsWith("o");
        if (wantsReceipt) {
            printOrderSummary(subtotal, deliveryCost, total, customer);
        }
        
        return total;
    }

    private double calculateSubtotal() {
        double total = 0.0;
        for (Item item : items) {
            total += item.accept(priceCalculator);
        }
        return total;
    }

    private double calculateTotalWeight() {
        return items.stream()
                   .mapToDouble(item -> item.getWeight() * item.getQuantity())
                   .sum();
    }

    private double calculateDistance(double x, double y) {
        // Position du magasin (0,0)
        return Math.sqrt(x * x + y * y);
    }

    private void printOrderSummary(double subtotal, double deliveryCost, double total, Customer customer) {
        System.out.println("\n----- RÉSUMÉ DE LA COMMANDE -----");
        System.out.println("Sous-total avant remise : " + subtotal + "€");
        System.out.println("Remise client (" + customer.getPlan() + ")");
        System.out.println("Frais de livraison : " + deliveryCost + "€");
        System.out.println("Total final : " + total + "€");
        System.out.println("-------------------------------");
    }

    public void processPayment(Customer customer, double total) {
        PaymentTransaction transaction = new PaymentTransaction(total, "");
        boolean paymentSuccess = pos.processPayment(transaction, customer);

        if (paymentSuccess) {
            printReceipt(customer, total, transaction);
        } else {
            System.out.println("Paiement refusé, veuillez réessayer.");
        }
        completeSale();
    }

    public void printReceipt(Customer customer, double total, PaymentTransaction transaction) {
        System.out.println("----- REÇU -----");
        System.out.println("Client : " + customer.getFirstName() + " " + customer.getLastName() + " (ID : " + customer.getId() + ")");
        for (Item item : items) {
            System.out.println(item.getLabel() + " x " + item.getQuantity() + " = " + item.accept(priceCalculator));
        }
        System.out.println("Total à payer : " + total);
        System.out.println("Transaction ID : " + transaction.getTransactionId() +
                           " | Statut : " + transaction.getStatus());
        System.out.println("----------------");
    }
    
    public void completeSale() {
        items.clear();
    }
}

