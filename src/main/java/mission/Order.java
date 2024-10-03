package mission;

import java.util.List;

public class Order {
    private final List<Item> items;
    private final double totalPrice;
    private final Customer customer;

    private Order(List<Item> items, double totalPrice, Customer customer) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public static Order createOrder(List<Item> items, double totalPrice, Customer customer) {
        return new Order(items, totalPrice, customer);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isNotValidTotalPrice() {
        return totalPrice <= 0;
    }

    public boolean hasNoCustomerInfo() {
        return customer == null;
    }
}
