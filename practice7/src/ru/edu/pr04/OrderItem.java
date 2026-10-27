package ru.edu.pr04;
public class OrderItem {
    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 100;
    private final Product product; 
    private int quantity;

    public OrderItem(Product product,int quantity) { 
        if(product == null) {
            throw new IllegalArgumentException("требуется продукт");
        }
        this.product=product; 
        setQuantity(quantity); 
    }

    private void setQuantity(int quantity) {
        if(quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("колво должно быть от " + MIN_QUANTITY + " до " + MAX_QUANTITY);
        }
        this.quantity=quantity; 
    }

    public void increase(int delta) { 
        if(delta <= 0) {
            throw new IllegalArgumentException("delta должен быть положительным");
        }

        setQuantity(this.quantity + delta);
    }

    public Product getProduct() { return product; } public int getQuantity() { return quantity; }

    public double getLineTotal() { 
        return product.getPrice() * quantity; 
    }
}
