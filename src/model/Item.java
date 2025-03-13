package model;

public abstract class Item {
    private String id;
    private String label;
    private double u_price;
    private int quantity;
    private double weight;

    public Item(String id, String label, double u_price, int quantity, double weight) {
        this.id = id;
        this.label = label;
        this.u_price = u_price;
        this.quantity = quantity;
        this.weight = weight;
    }

    // Méthode abstraite pour accepter un visitor
    public abstract double accept(ItemVisitor visitor);

    public String getId() { return id; }
    public String getLabel() { return label; }
    public double getUPrice() { return u_price;}
    public int getQuantity() { return quantity;}
    public double getWeight() { return weight; }

}
