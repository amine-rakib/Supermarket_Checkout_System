package model;

import java.util.List;
import java.util.ArrayList;

public class CashRegister {
    private List<Item> items;
    private POS pos;

    public CashRegister(POS pos) {
        this.items = new ArrayList<Item>();
        this.pos = pos;
    }


    public void recordItem(Item item) {
        items.add(item);
        System.out.println("Enregistré : " + item.getLabel() + " | Prix  après remise : " + item.getPrice());
    }

    public double computeTotal(Customer customer) {
        double total = 0.0;

        for (Item item : items) {
            double itemTotal = item.getPrice();
            total += itemTotal;
            System.out.println("Article : " + item.getLabel() + " | Quantité : " + item.getQuantity() + " | Sous-total : " + itemTotal);
        }

        // Remise liée au plan du client 
        String plan = customer.getPlan().toLowerCase();
        if (plan.equals("prime")) {
            total *= 0.80; // 20 % de remise
        } else if (plan.equals("platinum")) {
            total *= 0.70; // 30 % de remise
        }
        System.out.println("Total après remise client (" + customer.getPlan() + ") : " + total);
        return total;
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
            System.out.println(item.getLabel() + " x " + item.getQuantity() + " = " + (item.getPrice()));
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

