package mission;

public class Customer {
    private final String name;
    private final String email;

    private Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static Customer of(String name, String email) {
        return new Customer(name, email);
    }
}
