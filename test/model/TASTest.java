package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TASTest {
    @Test
    void testTransactionAuthorization() {
        TAS tas = new TAS();
        tas.openSecureConnection();
        
        BankAccount account = new BankAccount("123456", 100.0, 50.0);
        PaymentTransaction transaction = new PaymentTransaction(120.0, "Credit");

        boolean authorized = tas.authorizeTransaction(transaction, account.getBalance());
        assertTrue(authorized);

        tas.closeSecureConnection();
    }
}
