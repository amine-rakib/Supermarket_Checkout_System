package model;

public class PrimeDiscountStrategy implements ICustomerPlan {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.80; // 20% de réduction
    }

    @Override
    public String getPlanName() {
        return "prime";
    }
}
