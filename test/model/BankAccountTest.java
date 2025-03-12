package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void testDebitAndCredit() {
        BankAccount account = new BankAccount("123456", 100.0, 50.0);

        assertTrue(account.debit(50.0));
        assertEquals(50.0, account.getBalance());

        assertFalse(account.debit(200.0));
        assertEquals(50.0, account.getBalance());

        account.credit(30.0);
        assertEquals(80.0, account.getBalance());
    }
}
