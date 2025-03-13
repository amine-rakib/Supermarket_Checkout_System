package model;

public interface ICustomerPlan {
    double applyDiscount(double amount);
    String getPlanName();
}
