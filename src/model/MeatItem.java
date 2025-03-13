package model;

public class MeatItem extends Item {
    private boolean fresh;  // true si frais, false si congelé

    public MeatItem(String id, String label, double u_price, int quantity, double weight, boolean fresh) {
        super(id, label, u_price, quantity, weight);
        this.fresh = fresh;
    }

    public boolean isFresh() { 
        return fresh; 
    }

    @Override
    public double accept(ItemVisitor visitor) {
        return visitor.visitMeat(this);
    }
}