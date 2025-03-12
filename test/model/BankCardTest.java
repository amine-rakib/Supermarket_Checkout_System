package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankCardTest {
    @Test
    void testCardAssociation() {
        BankAccount account = new BankAccount("123456", 100.0, 50.0);
        BankCard card = new BankCard("987654", "1234", "Credit", account);

        assertEquals("987654", card.getCardNumber());
        assertEquals("1234", card.getPinCode());
        assertEquals("Credit", card.getCardType());
        assertEquals(account, card.getLinkedAccount());
    }
}
