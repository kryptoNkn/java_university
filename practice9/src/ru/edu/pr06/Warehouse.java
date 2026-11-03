package ru.edu.pr06;
import java.util.*;
public class Warehouse {
    private static final int EXTRA_LIMIT = 45;
    private final Map<String,StockItem> items=new HashMap<>(); 
    private final List<StockMovement> movements=new ArrayList<>();

    public void addItem(StockItem item) { 
        if(item == null || item.getCode() == null || item.getCode().isBlank()) {
            throw new IllegalArgumentException("пустой код");
        }

        if(items.containsKey(item.getCode())) {
            throw new IllegalArgumentException("код уже есть: " + item.getCode());
        }

        checkExtra(item.getCategory(), item.getQuantity());
        items.put(item.getCode(), item);
    }

    public void receive(String code,int q) { 
        StockItem item = require(code);
        if(item.getCategory() == Category.EXTRA && item.getQuantity() + q > EXTRA_LIMIT) {
            throw new IllegalArgumentException("для EXTRA остаток не больше " + EXTRA_LIMIT);
        }

        item.increase(q);
        movements.add(new StockMovement(code, MovementType.RECEIVE, q));
    }

    public void issue(String code,int q) { 
        StockItem item = require(code);
        item.decrease(q);
        movements.add(new StockMovement(code, MovementType.ISSUE, q));
    }

    public List<StockItem> findByCategory(Category c) { 
        List<StockItem> result = new ArrayList<>();
        if(c == null) {
            return result;
        }

        for(StockItem item : items.values()) {
            if(item.getCategory() == c) {
                result.add(item);
            }
        }

        return result;
    }

    public Map<Category,Integer> totalsByCategory() { 
        Map<Category,Integer> totals = new HashMap<>();
        for(StockItem item : items.values()) {
            totals.merge(item.getCategory(), item.getQuantity(), Integer::sum);
        }

        return totals;
    }

    public Set<String> codes() {
        return new HashSet<>(items.keySet());
    }

    public List<StockMovement> getMovements() {
        return new ArrayList<>(movements);
    }

    private void checkExtra(Category category, int quantity) {
        if(category == Category.EXTRA && quantity > EXTRA_LIMIT) {
            throw new IllegalArgumentException("для EXTRA остаток не больше " + EXTRA_LIMIT);
        }
    }

    private StockItem require(String code) {
        if(code == null || code.isBlank()) {
            throw new IllegalArgumentException("пустой код");
        }

        StockItem item = items.get(code);
        if(item == null) {
            throw new IllegalArgumentException("нет позиции: " + code);
        }

        return item;
    }
}
