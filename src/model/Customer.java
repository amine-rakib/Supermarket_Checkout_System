package model;
import java.util.UUID;

public class Customer {
    private String firstName;
    private String lastName;
    private UUID id;
    private ICustomerPlan discountStrategy; // normal, prime, platinium
    private double x_axis;
    private double y_axis;
    private BankCard BankCard;

    public Customer(String firstName, String lastName, String plan, double x_axis, double y_axis, BankCard BankCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = generateId();
        this.setDiscountStrategy(plan);

        // address of the customer
        this.x_axis = x_axis;
        this.y_axis = y_axis;

        this.BankCard = BankCard;
    }

    private void setDiscountStrategy(String plan) {
        switch (plan.toLowerCase()) {
            case "prime":
                this.discountStrategy = new PrimeDiscountStrategy();
                break;
            case "platinum":
                this.discountStrategy = new PlatinumDiscountStrategy();
                break;
            default:
                this.discountStrategy = new NormalDiscountStrategy();
        }
    }

    public double applyDiscount(double amount) {
        return discountStrategy.applyDiscount(amount);
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UUID getId() { return id; }
    public String getPlan() { return discountStrategy.getPlanName(); }
    public double getX_axis() { return x_axis; }
    public double getY_axis() { return y_axis; }
    public BankCard getBankCard() { return BankCard; }

}

