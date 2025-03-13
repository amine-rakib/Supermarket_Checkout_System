package model;

public class TAS {
    private boolean secureConnectionOpen;

    public TAS() {
        this.secureConnectionOpen = false;
    }
    
    public boolean openSecureConnection() {
        this.secureConnectionOpen = true;
        System.out.println("Connexion sécurisée ouverte avec la banque.");
        return true;
    }

    
    public boolean authorizeTransaction(PaymentTransaction transaction, BankAccount account) {
        if (!secureConnectionOpen) {
            System.out.println("Erreur : Pas de connexion sécurisée avec la banque.");
            transaction.setStatus("Declined");
            return false;
        }

        System.out.println("Vérification des fonds disponibles pour " + transaction.getAmount() + "€...");

        if (account.canAuthorizeTransaction(transaction.getAmount())) {
            // Débit du compte client
            boolean debited = account.debit(transaction.getAmount());
            if (debited) {
                // Crédit du compte du magasin (simulé)
                System.out.println("Paiement validé. Le montant de " + transaction.getAmount() + "€ a été transféré au magasin.");
                transaction.setStatus("Authorized");
                return true;
            } else {
                transaction.setStatus("Declined");
                return false;
            }
        } else {
            System.out.println("Fonds insuffisants. Paiement refusé.");
            transaction.setStatus("Declined");
            return false;
        }
    }

    public void closeSecureConnection() {
        this.secureConnectionOpen = false;
        System.out.println("Connexion sécurisée fermée avec la banque.");
    }
}
