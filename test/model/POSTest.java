package model;

import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class POSTest {
    @Test
    void testPOSProcessPayment() {
        TAS tas = new TAS();
        POS pos = new POS(tas);

        BankAccount account = new BankAccount("123456", 100.0, 50.0);
        BankCard card = new BankCard("987654", "1234", "Credit", account);
        Customer customer = new Customer("Alice", "Dupont", "prime", 48.8566, 2.3522, card);

        PaymentTransaction transaction = new PaymentTransaction(80.0, "Credit");
        boolean result = pos.processPayment(transaction, customer);

        assertTrue(result);
    }
}
