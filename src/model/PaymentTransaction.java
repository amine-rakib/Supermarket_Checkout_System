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

}