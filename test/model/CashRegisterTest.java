package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {
    @Test
    void testComputeTotal() {
        CashRegister register = new CashRegister(null);
        ItemCategory fruitCategory = new ItemCategory("Fruit", 0.10);
        Item apple = new Item("001", "Apple", 1.5, 2, fruitCategory, 0.5);

        register.recordItem(apple);

        Customer customer = new Customer("Alice", "Dupont", "prime", 48.8566, 2.3522, null);
        double total = register.computeTotal(customer);

        assertEquals(2.16, total, 0.01);
    }
}
