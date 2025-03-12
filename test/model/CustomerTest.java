package test.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testCustomerCreation() {
        BankAccount account = new BankAccount("123456", 100.0, 50.0);
        BankCard card = new BankCard("987654", "1234", "Credit", account);
        Customer customer = new Customer("Alice", "Dupont", "prime", 48.8566, 2.3522, card);

        assertEquals("Alice Dupont", customer.getFullName());
        assertEquals("prime", customer.getPlan());
        assertEquals(card, customer.getBankCard());
    }
}
