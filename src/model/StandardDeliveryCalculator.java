package model;

public class StandardDeliveryCalculator implements DeliveryCalculator {
    private static final double BASE_COST = 15.0;
    private static final double WEIGHT_THRESHOLD = 10.0;
    private static final double DISTANCE_THRESHOLD = 30.0;
    private static final double ADDITIONAL_WEIGHT_PERCENTAGE = 0.05;

    @Override
    public double calculateDeliveryCost(double totalWeight, double distance, double purchaseAmount) {
        double deliveryCost = BASE_COST;

        if (totalWeight > WEIGHT_THRESHOLD || distance > DISTANCE_THRESHOLD) {
            deliveryCost += purchaseAmount * ADDITIONAL_WEIGHT_PERCENTAGE;
        }

        return deliveryCost;
    }
}