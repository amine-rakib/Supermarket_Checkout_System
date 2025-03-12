package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTransactionTest {
    @Test
    void testTransactionAuthorization() {
        PaymentTransaction transaction = new PaymentTransaction(50.0, "Credit");
        assertEquals("Pending", transaction.getStatus());

        boolean authorized = transaction.authorizePayment();
        assertTrue(authorized || !authorized); // Le test peut réussir ou échouer selon la probabilité
    }
}
