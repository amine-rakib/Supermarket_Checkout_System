package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class ItemTest {
    private FruitItem apple;
    private FruitItem banana;
    private MeatItem beef;
    private MeatItem pork;
    private DairyItem milk;
    private ItemVisitor priceCalculator;

    @BeforeEach
    void setUp() {
        // Création du visiteur pour le calcul des prix
        priceCalculator = new PriceCalculatorVisitor();

        // Création des articles
        apple = new FruitItem("001", "Pomme Bio", 1.5, 4, 0.5, true);
        banana = new FruitItem("002", "Banane", 2.0, 3, 0.4, false);
        
        beef = new MeatItem("003", "Boeuf", 10.0, 2, 1.0, true);
        pork = new MeatItem("004", "Porc", 8.0, 3, 0.8, false);
        
        milk = new DairyItem("005", "Lait", 1.0, 2, 1.0, 1);
    }

    @Test
    void testGetId() {
        assertEquals("001", apple.getId());
        assertEquals("002", banana.getId());
        assertEquals("003", beef.getId());
    }

    @Test 
    void testGetLabel() {
        assertEquals("Pomme Bio", apple.getLabel());
        assertEquals("Banane", banana.getLabel());
        assertEquals("Boeuf", beef.getLabel());
    }

    @Test
    void testPriceCalculationForFruits() {
        // Prix pomme bio: 1.5 * 4 = 6.0, remise bio 15% -> 5.1
        assertEquals(5.1, apple.accept(priceCalculator), 0.001);
        
        // Prix banane: 2.0 * 3 = 6.0, pas de remise bio
        assertEquals(6.0, banana.accept(priceCalculator), 0.001);
    }

    @Test
    void testPriceCalculationForMeat() {
        // Prix boeuf frais: 10.0 * 2 = 20.0, pas de remise congelé
        assertEquals(20.0, beef.accept(priceCalculator), 0.001);
        
        // Prix porc congelé: 8.0 * 3 = 24.0, remise 10% -> 21.6
        assertEquals(21.6, pork.accept(priceCalculator), 0.001);
    }

    @Test
    void testPriceCalculationForDairy() {
        // Prix lait proche péremption: 1.0 * 2 = 2.0, remise 30% -> 1.4
        assertEquals(1.4, milk.accept(priceCalculator), 0.001);
    }

    @Test
    void testSpecificFruitProperties() {
        assertTrue(apple.isOrganic());
        assertFalse(banana.isOrganic());
    }

    @Test
    void testSpecificMeatProperties() {
        assertTrue(beef.isFresh());
        assertFalse(pork.isFresh());
    }

    @Test
    void testSpecificDairyProperties() {
        assertEquals(1, milk.getDaysUntilExpiration());
    }

    @Test
    void testGetWeight() {
        assertEquals(0.5, apple.getWeight(), 0.001);
        assertEquals(1.0, beef.getWeight(), 0.001);
        assertEquals(1.0, milk.getWeight(), 0.001);
    }
}