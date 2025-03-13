package model;

public interface DeliveryCalculator {
    double calculateDeliveryCost(double totalWeight, double distance, double purchaseAmount);
}