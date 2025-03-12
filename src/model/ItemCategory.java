package model;

public class ItemCategory {
    private String name;
    private double discount;

    public ItemCategory(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}


