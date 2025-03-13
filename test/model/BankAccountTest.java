package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;
    private BankCard debitCard;
    private BankCard creditCard;

    @BeforeEach
    void setUp() {
        account = new BankAccount("123456", 100.0, 50.0);
        debitCard = new BankCard("4111111111111111", "123", "Debit", account);
        creditCard = new BankCard("5555555555554444", "456", "Credit", account);
    }

    @Test
    void testAccountCreation() {
        assertEquals("123456", account.getAccountNumber());
        assertEquals(100.0, account.getBalance(debitCard));
        assertEquals(50.0, account.getCreditLimit());
    }

    @Test
    void testDebitAndCredit() {
        assertTrue(account.debit(50.0));
        assertEquals(50.0, account.getBalance(debitCard));

        assertFalse(account.debit(200.0));
        assertEquals(50.0, account.getBalance(debitCard));

        account.credit(30.0);
        assertEquals(80.0, account.getBalance(debitCard));
    }

    @Test
    void testBalanceWithDifferentCards() {
        assertEquals(100.0, account.getBalance(debitCard));
        assertEquals(150.0, account.getBalance(creditCard)); // balance + creditLimit
    }

    @Test
    void testCanAuthorizeTransaction() {
        account.setLinkedCard(debitCard);
        assertTrue(account.canAuthorizeTransaction(100.0));
        assertFalse(account.canAuthorizeTransaction(150.0));

        account.setLinkedCard(creditCard);
        assertTrue(account.canAuthorizeTransaction(150.0));
        assertFalse(account.canAuthorizeTransaction(200.0));
    }

    @Test
    void testCardLinking() {
        assertEquals(account, debitCard.getLinkedAccount());
        assertEquals(account, creditCard.getLinkedAccount());
    }

    @Test
    void testCardDetails() {
        assertEquals("4111111111111111", debitCard.getCardNumber());
        assertEquals("123", debitCard.getPinCode());
        assertEquals("Debit", debitCard.getCardType());
        
        assertEquals("5555555555554444", creditCard.getCardNumber());
        assertEquals("456", creditCard.getPinCode());
        assertEquals("Credit", creditCard.getCardType());
    }

    @Test
    void testMultipleTransactions() {
        account.setLinkedCard(debitCard);
        assertTrue(account.debit(20.0));
        assertTrue(account.debit(30.0));
        assertEquals(50.0, account.getBalance(debitCard));
        
        account.credit(100.0);
        assertEquals(150.0, account.getBalance(debitCard));
        
        assertTrue(account.debit(150.0));
        assertEquals(0.0, account.getBalance(debitCard));
    }

    @AfterEach
    void tearDown() {
        account = null;
        debitCard = null;
        creditCard = null;
    }
}
