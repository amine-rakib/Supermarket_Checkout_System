package model;

public class PlatinumDiscountStrategy implements ICustomerPlan {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.70; // 30% de réduction
    }

    @Override
    public String getPlanName() {
        return "platinum";
    }
}