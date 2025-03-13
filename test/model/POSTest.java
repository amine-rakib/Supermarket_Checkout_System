package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;


class POSTest {
    private POS pos;
    private TAS tas;
    private Customer customer;
    private Customer credit_customer;
    private Customer debit_customer;
    private BankAccount account;
    private BankCard creditCard;
    private BankCard debitCard;
    private PaymentTransaction transaction;
    private final InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        tas = new TAS();
        pos = new POS(tas);
        
        // Création du compte et des cartes
        account = new BankAccount("123456", 1000.0, 500.0);
        creditCard = new BankCard("4111111111111111", "1234", "Credit", account);
        debitCard = new BankCard("4222222222222222", "5678", "Debit", account);
        
        // Création du client
        credit_customer = new Customer("Jean", "Dupont", "normal", 0.0, 0.0, creditCard);
        debit_customer = new Customer("Jean", "Dupont", "normal", 1.0, 1.0, debitCard);
        
        // Création de la transaction
        transaction = new PaymentTransaction(50.0, "Credit");
    }

    @Test
    void testProcessPaymentSuccessful() {
        // Simulation des entrées utilisateur (PIN correct et choix du reçu)
        String input = "1234\noui\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        pos = new POS(tas, scanner);
        assertTrue(pos.processPayment(transaction, credit_customer));
    }

    @Test
    void testProcessPaymentWithIncorrectPin() {
        // Simulation d'un PIN incorrect
        String input = "9999\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        pos = new POS(tas, scanner);        
        assertFalse(pos.processPayment(transaction, credit_customer));
    }

    @Test
    void testProcessPaymentWithDebitCard() {
        // Simuler l'entrée utilisateur
        String simulatedInput = "5678\noui\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        
        // Créer POS avec le scanner simulé
        pos = new POS(tas, scanner);
        
        // Exécuter le test
        assertTrue(pos.processPayment(transaction, debit_customer));
    }

    @Test
    void testProcessPaymentWithInsufficientFunds() {
        PaymentTransaction largeTransaction = new PaymentTransaction(2000.0, "Credit");
        String input = "1234\noui\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        pos = new POS(tas, scanner);
        assertFalse(pos.processPayment(largeTransaction, credit_customer));
    }

    @Test
    void testProcessPaymentWithInvalidCard() {
        BankCard invalidCard = new BankCard("0000000000000000", "1234", "InvalidType", account);
        customer = new Customer("Jean", "Dupont", "normal", 0.0, 0.0, invalidCard);
        
        assertFalse(pos.processPayment(transaction, customer));
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn); // Restauration de l'entrée standard
        pos = null;
        tas = null;
        credit_customer = null;
        debit_customer = null;
        account = null;
        creditCard = null;
        debitCard = null;
        transaction = null;
    }
}