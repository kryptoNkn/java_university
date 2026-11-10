package ru.edu.pr09;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
public class SalesAnalyticsService {
    public static final int SECONDARY_MODIFIER = 45;

    public List<Sale> topSales(List<Sale> sales, double min, int limit) {
        if (sales == null || limit <= 0) {
            return List.of();
        }

        return sales.stream().filter(s -> s.amount() >= min).sorted(byAmountThenSecondary()).limit(limit).toList();
    }

    public Optional<Sale> maxSale(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            return Optional.empty();
        }

        return sales.stream().max(Comparator.comparingDouble(Sale::amount).thenComparingInt((Sale s) -> -secondaryDistance(s)).thenComparing(Comparator.comparing(Sale::id).reversed()));
    }

    public Map<String, Double> totalsByManager(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            return Map.of();
        }

        return sales.stream().collect(Collectors.groupingBy(Sale::manager,Collectors.summingDouble(Sale::amount)));
    }

    public double averageByRegion(List<Sale> sales, String region) {
        if (sales == null || region == null || region.isBlank()) {
            return 0.0;
        }

        return sales.stream().filter(s -> region.equals(s.region())).mapToDouble(Sale::amount).average().orElse(0.0);
    }

    private Comparator<Sale> byAmountThenSecondary() {
        return Comparator.comparingDouble(Sale::amount).reversed().thenComparingInt(this::secondaryDistance).thenComparing(Sale::id);
    }

    private int secondaryDistance(Sale sale) {
        int n = numericId(sale.id());
        return Math.abs(n - SECONDARY_MODIFIER);
    }

    private int numericId(String id) {
        String digits = id.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}