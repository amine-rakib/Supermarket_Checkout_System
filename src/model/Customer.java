package model;
import java.util.UUID;

public class Customer {
    private String firstName;
    private String lastName;
    private UUID id;
    private String plan; // normal, prime, platinium
    private double x_axis;
    private double y_axis;
    private BankCard BankCard;

    public Customer(String firstName, String lastName, String plan, double x_axis, double y_axis, BankCard BankCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = generateId();
        this.plan = plan;

        // address of the customer
        this.x_axis = x_axis;
        this.y_axis = y_axis;

        this.BankCard = BankCard;
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public UUID getId() { return id; }

    public String getPlan() { return plan; }

    public double getX_axis() { return x_axis; }
    public double getY_axis() { return y_axis; }

    public BankCard getBankCard() { return BankCard; }

}

