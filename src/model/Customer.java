package model;

public class Customer {
    private String firstName;
    private String lastName;
    private int id;
    private String plan; // "normal", "prime", "platinium"

    public Customer(String firstName, String lastName, int id, String plan) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.plan = plan;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getPlan() {
        return plan;
    }

}

