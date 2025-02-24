package model;

public class Item {
    private String id;
    private String label;
    private double price;
    private String category; // ex. "dairy", "fruit", "bakery", etc.

    public Item(String id, String label, double price, int quantity, String category) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.category = category;
    }

    public String getLabel() {
        return label;
    }
    
    public String getCategory() {
        return category;
    }
}
