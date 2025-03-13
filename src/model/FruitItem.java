package model;

public class FruitItem extends Item {
    private boolean organic;

    public FruitItem(String id, String label, double u_price, int quantity, double weight, boolean organic) {
        super(id, label, u_price, quantity, weight);
        this.organic = organic;
    }

    public boolean isOrganic() { 
        return organic; 
    }

    @Override
    public double accept(ItemVisitor visitor) {
        return visitor.visitFruit(this);
    }
}