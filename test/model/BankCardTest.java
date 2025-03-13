package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankCardTest {
    private BankCard debitCard;
    private BankCard creditCard;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        // Création d'un compte bancaire pour les tests
        account = new BankAccount("123456789", 1000.0, 500.0);
        
        // Création des cartes de test
        debitCard = new BankCard("4111111111111111", "123", "Debit", account);
        creditCard = new BankCard("5555555555554444", "456", "Credit", account);
    }

    @Test
    void testCardCreation() {
        // Test de la création d'une carte de débit
        assertNotNull(debitCard);
        assertEquals("4111111111111111", debitCard.getCardNumber());
        assertEquals("123", debitCard.getPinCode());
        assertEquals("Debit", debitCard.getCardType());
        assertEquals(account, debitCard.getLinkedAccount());

        // Test de la création d'une carte de crédit
        assertNotNull(creditCard);
        assertEquals("5555555555554444", creditCard.getCardNumber());
        assertEquals("456", creditCard.getPinCode());
        assertEquals("Credit", creditCard.getCardType());
        assertEquals(account, creditCard.getLinkedAccount());
    }

    @Test
    void testGetCardNumber() {
        assertEquals("4111111111111111", debitCard.getCardNumber());
        assertEquals("5555555555554444", creditCard.getCardNumber());
    }

    @Test
    void testGetPinCode() {
        assertEquals("123", debitCard.getPinCode());
        assertEquals("456", creditCard.getPinCode());
    }

    @Test
    void testGetCardType() {
        assertEquals("Debit", debitCard.getCardType());
        assertEquals("Credit", creditCard.getCardType());
    }

    @Test
    void testGetLinkedAccount() {
        // Vérification que les cartes sont bien liées au compte
        assertNotNull(debitCard.getLinkedAccount());
        assertNotNull(creditCard.getLinkedAccount());
        
        // Vérification que c'est le bon compte
        assertEquals(account, debitCard.getLinkedAccount());
        assertEquals(account, creditCard.getLinkedAccount());
    }

    @Test
    void testAccountLinkage() {
        // Vérification que le compte est correctement lié dans les deux sens
        BankAccount newAccount = new BankAccount("987654321", 2000.0, 1000.0);
        BankCard newCard = new BankCard("1234567890123456", "789", "Debit", newAccount);
        
        assertEquals(newAccount, newCard.getLinkedAccount());
        assertEquals(newCard, newAccount.getLinkedCard());
    }

    @AfterEach
    void tearDown() {
        debitCard = null;
        creditCard = null;
        account = null;
    }
}