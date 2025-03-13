package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {
    private CashRegister cashRegister;
    private POS pos;
    private TAS tas;
    private Customer regularCustomer;
    private Customer primeCustomer;
    private Customer platinumCustomer;
    private ItemCategory foodCategory;
    private Item item1;
    private Item item2;
    private BankAccount regularAccount;
    private BankAccount primeAccount;
    private BankAccount platinumAccount;
    private BankCard regularCard;
    private BankCard primeCard;
    private BankCard platinumCard;

    @BeforeEach
    void setUp() {
        // Initialisation du POS
        tas = new TAS();
        pos = new POS(tas);
        cashRegister = new CashRegister(pos);

        // Création des comptes bancaires
        regularAccount = new BankAccount("123456", 1000.0, 0.0);
        primeAccount = new BankAccount("234567", 2000.0, 500.0);
        platinumAccount = new BankAccount("345678", 3000.0, 1000.0);

        // Création des cartes bancaires
        regularCard = new BankCard("4111111111111111", "123", "Debit", regularAccount);
        primeCard = new BankCard("4222222222222222", "456", "Credit", primeAccount);
        platinumCard = new BankCard("4333333333333333", "789", "Credit", platinumAccount);

        // Création des clients avec différents plans
        regularCustomer = new Customer("Jean", "Dupont", "normal", 0.0, 0.0, regularCard);
        primeCustomer = new Customer("Marie", "Martin", "prime", 1.0, 1.0, primeCard);
        platinumCustomer = new Customer("Pierre", "Bernard", "platinum", 2.0, 2.0, platinumCard);

        // Création de la catégorie et des articles
        foodCategory = new ItemCategory("Food", 0.0);
        item1 = new Item("I001", "Pain", 2.0, 2, foodCategory, 0.0);
        item2 = new Item("I002", "Lait", 1.5, 3, foodCategory, 0.0);
    }

    @Test
    void testRecordItem() {
        cashRegister.recordItem(item1);
        cashRegister.recordItem(item2);
        double total = cashRegister.computeTotal(regularCustomer);
        assertEquals(8.5, total, 0.001); // (2.0 * 2) + (1.5 * 3) = 8.5
    }

    @Test
    void testComputeTotalRegularCustomer() {
        cashRegister.recordItem(item1);
        cashRegister.recordItem(item2);
        double total = cashRegister.computeTotal(regularCustomer);
        assertEquals(8.5, total, 0.001); // Pas de remise
    }

    @Test
    void testComputeTotalPrimeCustomer() {
        cashRegister.recordItem(item1);
        cashRegister.recordItem(item2);
        double total = cashRegister.computeTotal(primeCustomer);
        assertEquals(6.8, total, 0.001); // 20% de remise sur 8.5
    }

    @Test
    void testComputeTotalPlatinumCustomer() {
        cashRegister.recordItem(item1);
        cashRegister.recordItem(item2);
        double total = cashRegister.computeTotal(platinumCustomer);
        assertEquals(5.95, total, 0.001); // 30% de remise sur 8.5
    }

    @Test
    void testProcessPaymentSuccessful() {
        cashRegister.recordItem(item1);
        double total = cashRegister.computeTotal(regularCustomer);
        cashRegister.processPayment(regularCustomer, total);
        assertEquals(0.0, cashRegister.computeTotal(regularCustomer), 0.001);
    }

    @Test
    void testCompleteSale() {
        cashRegister.recordItem(item1);
        cashRegister.recordItem(item2);
        cashRegister.completeSale();
        assertEquals(0.0, cashRegister.computeTotal(regularCustomer), 0.001);
    }

    @AfterEach
    void tearDown() {
        cashRegister = null;
        pos = null;
        regularCustomer = null;
        primeCustomer = null;
        platinumCustomer = null;
        foodCategory = null;
        item1 = null;
        item2 = null;
        regularAccount = null;
        primeAccount = null;
        platinumAccount = null;
        regularCard = null;
        primeCard = null;
        platinumCard = null;
    }
}