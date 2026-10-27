package ru.edu.pr04;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Order {
    private final Employee employee; 
    private final List<OrderItem> items=new ArrayList<>(); 
    private OrderStatus status=OrderStatus.DRAFT;

    public Order(Employee employee) { 
        this.employee=Objects.requireNonNull(employee); 
    }

    public void addProduct(Product product,int quantity) { 
        ensureDraft();
        if(product == null) {
            throw new IllegalArgumentException("требуется продукт");
        }
        if(quantity < OrderItem.MIN_QUANTITY || quantity > OrderItem.MAX_QUANTITY) {
            throw new IllegalArgumentException("количество должно быть из " + OrderItem.MIN_QUANTITY + " до " + OrderItem.MAX_QUANTITY);
        }

        for(OrderItem item : items) {
            if(item.getProduct().getId().equals(product.getId())) {
                item.increase(quantity);
                return;
            }
        }

        items.add(new OrderItem(product, quantity));
    }
    public void removeProduct(String productId) { 
        ensureDraft();
        if(productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("требуется идентификатор товара");
        }

        boolean removed = items.removeIf(item -> item.getProduct().getId().equals(productId));

        if(!removed) {
            throw new IllegalArgumentException("товар не в порядке: " + productId);
        }
    }

    public double total() { 
        double sum = 0;
        for(OrderItem item : items) {
            sum += item.getLineTotal();
        }

        return sum;
    }

    public void confirm() { 
        if(status == OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("заказ уже подтвержден");
        }
        if(items.isEmpty()) {
            throw new IllegalArgumentException("невозможно подтвердить пустой заказ");
        }

        status = OrderStatus.CONFIRMED;
    }

    public List<OrderItem> getItems() { 
        return List.copyOf(items); 
    }

    public OrderStatus getStatus() { 
        return status; 
    }

    public Employee getEmployee() {
        return employee;
    }

    private void ensureDraft() {
        if(status == OrderStatus.CONFIRMED) {
            throw new IllegalArgumentException("подтвержденный заказ изменить нельзя");
        }
    }
}
