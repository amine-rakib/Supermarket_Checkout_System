package controller;

import model.PaymentTransaction;
import model.Customer;
import java.util.Scanner;

public class POS {
    public boolean processPayment(PaymentTransaction transaction, Customer customer) {
        System.out.println("Sélectionnez votre mode de paiement : [1] Débit | [2] Crédit");
        String paymentMethod = customer.getPaymentMethod();
        transaction.setPaymentMethod(paymentMethod);
        transaction.setStatus("pending");
        return transaction.authorizePayment();
    }   
}
