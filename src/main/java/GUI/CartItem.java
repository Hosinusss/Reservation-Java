package GUI;

public class CartItem {
    private String name;
    private final int places;
    private final int quantity;
    private final int pricing; // Add the pricing property (integer)

    public CartItem(int places, int quantity, int pricing) {

        this.places = places;
        this.quantity = quantity;
        this.pricing = pricing;
    }

    // Getters for other properties (name, places, quantity)

    public int getPlaces() {
        return places;
    }

    public int getQuantity() {
        return quantity;
    }

    // Getter for pricing
    public int getPricing() {
        return pricing;
    }
}