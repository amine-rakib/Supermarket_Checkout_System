package model;

public class Item {
    private String id;
    private String label;
    private double price;
    private int quantity;
    private String category; 
    private double storeDiscount;

    public Item(String id, String label, double price, int quantity, String category, double storeDiscount) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.storeDiscount = storeDiscount;
    }


    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
    
    public double getBasePrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public double getStoreDiscount() {
        return storeDiscount;
    }

    public double getPrice() {
        return price * (1 - storeDiscount);
    }
}
