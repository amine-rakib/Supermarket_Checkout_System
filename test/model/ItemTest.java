package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    void testItemPricingWithDiscount() {
        ItemCategory fruitCategory = new ItemCategory("Fruit", 0.10);
        Item apple = new Item("001", "Apple", 1.5, 4, fruitCategory, 0.5);

        assertEquals(1.35, apple.getPricePerUnit(), 0.01);
        assertEquals(5.4, apple.getTotalPrice(), 0.01);
    }
}
