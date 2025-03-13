package model;

public class NormalDiscountStrategy implements ICustomerPlan {
    @Override
    public double applyDiscount(double amount) {
        return amount; // Pas de réduction
    }

    @Override
    public String getPlanName() {
        return "normal";
    }
}