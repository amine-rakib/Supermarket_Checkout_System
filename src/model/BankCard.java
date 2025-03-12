package model;

public class BankCard {
    private String cardNumber;
    private String pinCode;
    private String cardType; // "Debit" ou "Credit"
    private BankAccount linkedAccount;

    public BankCard(String cardNumber, String pinCode, String cardType, BankAccount linkedAccount) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.cardType = cardType;
        this.linkedAccount = linkedAccount;
        linkedAccount.setLinkedCard(this);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getCardType() {
        return cardType;
    }

    public BankAccount getLinkedAccount() {
        return linkedAccount;
    }
}
