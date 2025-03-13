package model;

public class DairyItem extends Item {
    private int daysUntilExpiration;

    public DairyItem(String id, String label, double u_price, int quantity, double weight, int daysUntilExpiration) {
        super(id, label, u_price, quantity, weight);
        this.daysUntilExpiration = daysUntilExpiration;
    }

    public int getDaysUntilExpiration() { 
        return daysUntilExpiration; 
    }

    @Override
    public double accept(ItemVisitor visitor) {
        return visitor.visitDairy(this);
    }
}