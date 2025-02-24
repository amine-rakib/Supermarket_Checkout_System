package model;

public class Delivery {

    private int DeliveryId;
    private String address;
    private double weight;
    private double distance;
    private double cost;
    
    public Delivery(int DeliveryId, String address, double weight, double distance, double cost) {
        this.DeliveryId = DeliveryId;
        this.address = address;
        this.weight = weight;
        this.distance = distance;
        this.cost = cost;
    }

    private double calculateDeliveryCost() {
        if (weight < 10 && distance <= 30) {
            cost = 15.0;
        } else if (weight < 50) {
            cost = 15.0 + (0.05 * weight);
        } else {
            cost = 15.0 + (0.05 * weight) + (0.02 * distance);
        }
        System.out.println("Coût de livraison : " + cost);
        return cost;
    }

    public double calculateFinalCost(Customer customer) {
        double baseCost = calculateDeliveryCost(); // méthode existante qui calcule le coût de base
        String plan = customer.getPlan().toLowerCase();
        if (plan.equals("prime")) {
            return baseCost * 0.5; // 50% du coût
        } else if (plan.equals("platinum")) {
            return 0.0; // gratuit pour les platinum
        } else {
            return baseCost; // pas de remise pour les clients normaux
        }
    }
    
    public int getDeliveryId() {
        return DeliveryId;
    }

    public String getAddress() {
        return address;
    }
}
