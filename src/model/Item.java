package model;

public class Item {
    private String id;
    private String label;
    private double u_price;
    private int quantity;
    private ItemCategory category; 
    private double weight;

    public Item(String id, String label, double u_price, int quantity, ItemCategory category, double weight) {
        this.id = id;
        this.label = label;
        this.u_price = price;
        this.quantity = quantity;
        this.category = category;
        this.weight = weight;
    }


    public String getId() { return id; }

    public String getLabel() { return label; }
    
    public double getUPrice() { return u_price;}

    public int getQuantity() { return quantity;}

    public ItemCategory getCategory() { return category; }

    public double getWeight() { return weight; }

    public double getPrice() {
        double discount = category.getDiscount();
        return u_price * (1 - discount) * quantity;
    }

}
