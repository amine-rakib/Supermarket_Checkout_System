package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTransactionTest {
    private PaymentTransaction transaction;
    private double amount;
    private String paymentType;

    @BeforeEach
    void setUp() {
        amount = 50.0;
        paymentType = "Credit";
        transaction = new PaymentTransaction(amount, paymentType);
    }

    @Test
    void testTransactionInitialization() {
        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(paymentType, transaction.getPaymentMethod());
        assertEquals("pending", transaction.getStatus());
        assertNotNull(transaction.getTransactionId());
    }

    @Test
    void testDifferentPaymentTypes() {
        PaymentTransaction creditTransaction = new PaymentTransaction(100.0, "Credit");
        PaymentTransaction debitTransaction = new PaymentTransaction(100.0, "Debit");
        
        assertNotNull(creditTransaction.getTransactionId());
        assertNotNull(debitTransaction.getTransactionId());
        assertNotEquals(creditTransaction.getTransactionId(), debitTransaction.getTransactionId());
    }

    @AfterEach
    void tearDown() {
        transaction = null;
    }
}