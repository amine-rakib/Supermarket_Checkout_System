package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer customer;
    private Customer customer1;
    private Customer customer2;
    private BankAccount account1;
    private BankAccount account2;
    private BankCard creditCard;
    private BankCard debitCard;

    @BeforeEach
    void setUp() {
        
        account1 = new BankAccount("123456", 1000.0, 500.0);
        account2 = new BankAccount("123456", 1000.0, 500.0);

        // Création des cartes bancaires associées au compte
        creditCard = new BankCard("987654321", "1234", "Credit", account1);
        debitCard = new BankCard("1111222233334444", "5678", "Debit", account2);


        // Création d'un client avec ses coordonnées et sa carte bancaire
        customer1 = new Customer("Alice", "Dupont", "prime", 10.0, 20.0, creditCard);
        customer2 = new Customer("Bob", "Martin", "prime", 10.0, 20.0, debitCard);
    }

    @Test
    void testCustomerCreation() {
        assertNotNull(customer1);
        assertEquals("Alice", customer1.getFirstName());
        assertEquals("Dupont", customer1.getLastName());
        assertEquals("prime", customer1.getPlan());
    }

    @Test
    void testCustomerLocation() {
        assertEquals(10.0, customer1.getX_axis(), 0.01);
        assertEquals(20.0, customer1.getY_axis(), 0.01);
    }

    @Test
    void testBankAccountAssociation() {
        assertEquals(account1, customer1.getBankCard().getLinkedAccount());
        assertEquals(1500.0, customer1.getBankCard().getLinkedAccount().getBalance(creditCard), 0.01);
        assertEquals(1000.0, customer2.getBankCard().getLinkedAccount().getBalance(debitCard), 0.01); 
    }

    @Test
    void testCreditCardTransactionAuthorization() {
        boolean authorized = account1.canAuthorizeTransaction(1200.0); // Devrait être autorisé car 1000 + 500 (crédit)
        assertTrue(authorized);
        
        boolean notAuthorized = account1.canAuthorizeTransaction(2000.0); // Devrait échouer
        assertFalse(notAuthorized);
    }

    @Test
    void testDebitCardTransactionAuthorization() {
        BankAccount debitAccount = new BankAccount("654321", 500.0, 0.0); // Pas de crédit
        BankCard debitCardTest = new BankCard("9999888877776666", "9876", "Debit", debitAccount);

        boolean authorized = debitAccount.canAuthorizeTransaction(400.0); // Devrait passer
        assertTrue(authorized);

        boolean notAuthorized = debitAccount.canAuthorizeTransaction(600.0); // Devrait échouer
        assertFalse(notAuthorized);
    }


    @Test
    void testCustomerPlanChanges() {
        // Prime : 50% de réduction sur la livraison
        customer = new Customer("John", "Doe", "prime", 10.0, 20.0, creditCard);
        assertEquals("prime", customer.getPlan());

        // Passer à Platinum : 100% de réduction
        customer = new Customer("John", "Doe", "platinum", 10.0, 20.0, creditCard);
        assertEquals("platinum", customer.getPlan());

        // Passer à Normal : 0% de réduction
        customer = new Customer("John", "Doe", "normal", 10.0, 20.0, creditCard);
        assertEquals("normal", customer.getPlan());
    }
}
