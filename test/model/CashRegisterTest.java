package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class CashRegisterTest {
    private CashRegister cashRegister;
    private POS pos;
    private TAS tas;
    private Customer regularCustomer;
    private Customer primeCustomer;
    private Customer platinumCustomer;
    private FruitItem pomme;
    private DairyItem yaourt;
    private MeatItem steak;
    private BankAccount regularAccount;
    private BankAccount primeAccount;
    private BankAccount platinumAccount;
    private BankCard regularCard;
    private BankCard primeCard;
    private BankCard platinumCard;
    private final InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        // Initialisation du POS et de la caisse
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

        // Création des clients
        regularCustomer = new Customer("Jean", "Dupont", "normal", 5.0, 5.0, regularCard);
        primeCustomer = new Customer("Marie", "Martin", "prime", 20.0, 20.0, primeCard);
        platinumCustomer = new Customer("Pierre", "Bernard", "platinum", 40.0, 40.0, platinumCard);

        // Création des articles avec leurs spécificités
        pomme = new FruitItem("F001", "Pomme Bio", 2.0, 2, 0.5, true);
        yaourt = new DairyItem("D001", "Yaourt", 1.5, 3, 0.4, 1);
        steak = new MeatItem("M001", "Steak", 5.0, 2, 0.3, false);
    }

    @Test
    void testRecordFruitItem() {
        cashRegister.recordItem(pomme);
        double total = cashRegister.computeTotal(regularCustomer);
        assertEquals(18.4, total, 0.001);
    }

    @Test
    void testRecordDairyItem() {
        cashRegister.recordItem(yaourt);
        double total = cashRegister.computeTotal(regularCustomer);
        assertEquals(18.15, total, 0.001);
    }

    @Test
    void testRecordMeatItem() {
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(regularCustomer);
        // Prix de base: 5.0 * 2 = 10.0, remise congelé 10% -> 9.0 + 15 livraison
        assertEquals(24.0, total, 0.001);
    }

    @Test
    void testComputeTotalRegularCustomer() {
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(regularCustomer);
        assertEquals(30.55, total, 0.001);
    }

    @Test
    void testComputeTotalPrimeCustomer() {
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(primeCustomer);
        assertEquals(19.94, total, 0.001);
    }

    @Test
    void testComputeTotalPlatinumCustomer() {
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(platinumCustomer);
        // 15.55 avec 30% de remise platinum -> 10.885
        assertEquals(10.885, total, 0.001);
    }

    @Test
    void testComputeTotalRegularCustomerWithoutDelivery() {
        simulateUserInput("non\n");
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(regularCustomer);
        
        assertEquals(30.55, total, 0.001);
    }

    @Test
    void testComputeTotalRegularCustomerWithDelivery() {
        simulateUserInput("oui\n");
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(regularCustomer);
        // 15.55 + 15.0 (frais de livraison de base, distance < 30km)
        assertEquals(30.55, total, 0.001);
    }

    @Test
    void testComputeTotalPrimeCustomerWithDelivery() {
        simulateUserInput("oui\n");
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(primeCustomer);
        
        // Calcul détaillé :
        // Sous-total articles après remises individuelles = 15.55
        // Remise prime 20% sur articles : 15.55 * 0.8 = 12.44
        // Frais livraison de base : 15.0
        // Remise prime 50% sur livraison : 15.0 * 0.5 = 7.5
        // Total final = 12.44 + 7.5 = 19.94
        assertEquals(19.94, total, 0.001);
    }

    @Test
    void testComputeTotalPlatinumCustomerWithDelivery() {
        simulateUserInput("oui\n");
        cashRegister.recordItem(pomme);
        cashRegister.recordItem(yaourt);
        cashRegister.recordItem(steak);
        double total = cashRegister.computeTotal(platinumCustomer);
        
        // Calcul détaillé :
        // Sous-total articles après remises individuelles = 15.55
        // Remise platinum 30% sur articles : 15.55 * 0.7 = 10.885
        // Frais livraison : 0 (gratuit pour platinum)
        // Total final = 10.885
        assertEquals(10.885, total, 0.001);
    }

    @Test
    void testHeavyDeliveryForRegularCustomer() {
        simulateUserInput("oui\n");
        for(int i = 0; i < 5; i++) {
            cashRegister.recordItem(steak);
        }
        double total = cashRegister.computeTotal(regularCustomer);
        
        // Calcul détaillé :
        // Prix steak unitaire après remise congelé : 5.0 * 2 * 0.9 = 9.0
        // Prix total steaks : 9.0 * 5 = 45.0
        // Poids total : 0.3 * 2 * 5 = 3.0 kg (< 10kg)
        // Distance : sqrt(5² + 5²) ≈ 7.07 km (< 30km)
        // Frais livraison standard : 15.0
        // Total final = 45.0 + 15.0 = 60.0
        assertEquals(60.0, total, 0.001);
    }

    @Test
    void testLongDistanceDeliveryForPrimeCustomer() {
        simulateUserInput("oui\n");
        cashRegister.recordItem(pomme);
        Customer farCustomer = new Customer("Test", "Far", "prime", 50.0, 50.0, primeCard);
        double total = cashRegister.computeTotal(farCustomer);
        
        // Calcul détaillé :
        // Prix pomme après remise bio : 2.0 * 2 * 0.85 = 3.4
        // Remise prime 20% : 3.4 * 0.8 = 2.72
        // Distance : sqrt(50² + 50²) ≈ 70.71 km (> 30km)
        // Frais livraison : 15.0 + (2.72 * 0.05) = 15.136
        // Remise prime 50% sur livraison : 15.136 * 0.5 = 7.568
        assertEquals(10.305, total, 0.001);
    }

    private void simulateUserInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    @AfterEach
    void tearDown() {
        cashRegister = null;
        pos = null;
        tas = null;
        regularCustomer = null;
        primeCustomer = null;
        platinumCustomer = null;
        pomme = null;
        yaourt = null;
        steak = null;
        regularAccount = null;
        primeAccount = null;
        platinumAccount = null;
        regularCard = null;
        primeCard = null;
        platinumCard = null;
        System.setIn(systemIn); // Restaurer l'entrée standard
    }
}