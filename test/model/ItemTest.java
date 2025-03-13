package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class ItemTest {
    private ItemCategory fruitCategory;
    private Item apple;
    private Item banana;

    private ItemCategory meatCategory; 
    private Item beef;
    private Item pork;

    @BeforeEach
    void setUp() {
        fruitCategory = new ItemCategory("Fruit", 0.10);
        apple = new Item("001", "Apple", 1.5, 4, fruitCategory, 0.0);
        banana = new Item("002", "Banana", 2.0, 3, fruitCategory, 0.0);

        meatCategory = new ItemCategory("Meat", 0.20);
        beef = new Item("003", "Beef", 10.0, 2, meatCategory, 0.0); 
        pork = new Item("004", "Pork", 8.0, 3, meatCategory, 0.0);
    }

    @Test
    void testGetId() {
        assertEquals("001", apple.getId());
        assertEquals("002", banana.getId());
    }

    @Test 
    void testGetLabel() {
        assertEquals("Apple", apple.getLabel());
        assertEquals("Banana", banana.getLabel());
    }

    @Test
    void testGetPrice() {
        assertEquals(5.4, apple.getPrice(), 0.001); // 1.5 * 4 * (1-0.1)
        assertEquals(5.4, banana.getPrice(), 0.001); // 2.0 * 3 * (1-0.1)
        assertEquals(16.0, beef.getPrice(), 0.001); // 10.0 * 2 * (1-0.2)
        assertEquals(19.2, pork.getPrice(), 0.001); // 8.0 * 3 * (1-0.2)
    }

    @Test
    void testGetCategory() {
        assertEquals(fruitCategory, apple.getCategory());
        assertEquals(meatCategory, beef.getCategory());
    }

    @Test
    void testGetWeight() {
        assertEquals(0.0, apple.getWeight(), 0.001);
        assertEquals(0.0, beef.getWeight(), 0.001);
    }
}
