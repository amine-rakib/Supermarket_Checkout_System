package model;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private double creditLimit; // Découvert autorisé pour les cartes de crédit
    private BankCard linkedCard;

    public BankAccount(String accountNumber, double balance, double creditLimit) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creditLimit = creditLimit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance(BankCard card) {
        if (card.getCardType().equals("Debit")) {
            return balance;
        } else {
            return balance + creditLimit;
        }
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setLinkedCard(BankCard linkedCard) {
        this.linkedCard = linkedCard;
    }

    public BankCard getLinkedCard() {
        return linkedCard;
    }
    
    public boolean canAuthorizeTransaction(double amount) {
        return amount <= getBalance(linkedCard);
    }

    
    public boolean debit(double amount) {
        if (canAuthorizeTransaction(amount)) {
            balance -= amount;
            System.out.println("Compte débité de " + amount + "€. Nouveau solde : " + balance + "€.");
            return true;
        } else {
            System.out.println("Fonds insuffisants. Transaction refusée.");
            return false;
        }
    }

    
    public void credit(double amount) {
        balance += amount;
        System.out.println("Compte crédité de " + amount + "€. Nouveau solde : " + balance + "€.");
    }
}
