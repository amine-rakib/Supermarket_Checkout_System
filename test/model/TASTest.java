package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TASTest {
    private TAS tas;
    private BankAccount account;
    private PaymentTransaction validTransaction;
    private PaymentTransaction largeTransaction;

    @BeforeEach
    void setUp() {
        // Initialisation du TAS
        tas = new TAS();
        
        // Création d'un compte bancaire avec 1000€ de solde et 500€ de limite de crédit
        account = new BankAccount("123456", 1000.0, 500.0);
        
        // Création des transactions de test
        validTransaction = new PaymentTransaction(500.0, "Credit");
        largeTransaction = new PaymentTransaction(2000.0, "Credit");
    }

    @Test
    void testOpenSecureConnection() {
        assertTrue(tas.openSecureConnection(), "La connexion sécurisée devrait s'ouvrir avec succès");
    }

    @Test
    void testAuthorizeTransactionWithoutSecureConnection() {
        // Test sans ouvrir de connexion sécurisée
        assertFalse(tas.authorizeTransaction(validTransaction, account),
                "La transaction devrait échouer sans connexion sécurisée");
        assertEquals("Declined", validTransaction.getStatus(),
                "Le statut de la transaction devrait être 'Declined'");
    }

    @Test
    void testCloseSecureConnection() {
        // Ouvrir puis fermer la connexion
        tas.openSecureConnection();
        tas.closeSecureConnection();
        
        // Vérifier qu'une transaction échoue après la fermeture
        assertFalse(tas.authorizeTransaction(validTransaction, account),
                "La transaction devrait échouer après la fermeture de la connexion");
    }

    @AfterEach
    void tearDown() {
        tas = null;
        account = null;
        validTransaction = null;
        largeTransaction = null;
    }
}