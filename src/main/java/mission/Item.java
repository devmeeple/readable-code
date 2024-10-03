package mission;

public class Item {
    private final String name;
    private final double price;

    private Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public static Item of(String name, double price) {
        return new Item(name, price);
    }
}
