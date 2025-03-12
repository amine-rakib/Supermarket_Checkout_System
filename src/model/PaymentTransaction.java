package model;

import java.util.UUID;

public class PaymentTransaction {
    private String transactionId;
    private double amount;
    private String paymentMethod;
    private String status; // pending, success, failed

    public PaymentTransaction(double amount, String paymentMethod) {
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "pending";
    }

    public String getTransactionId() { return transactionId; }

    public double getAmount() { return amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }    

    public boolean authorizePayment() {
        // Simulate : 95% chance of success 
        // Il aurait fallut dans un système complet, vérifier la validité de la carte, le solde, etc.
        double random = Math.random();
        if (random < 0.95) {
            status = "success";
            return true;
        } else {
            status = "failed";
            return false;
        }
    }
}