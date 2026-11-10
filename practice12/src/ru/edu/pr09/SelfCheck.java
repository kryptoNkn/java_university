package ru.edu.pr09;
import java.util.ArrayList;
import java.util.List;
public class SelfCheck {
    public static void main(String[] args) {
        SalesAnalyticsService svc = new SalesAnalyticsService();
        List<Sale> sales = List.of(new Sale("S1", "Ivanov", "Moscow", 150000), new Sale("S2", "Petrova", "SPb", 92000), new Sale("S45", "Lee", "Kazan", 150000), new Sale("S3", "Ivanov", "Moscow", 240000));
        List<Sale> copy = new ArrayList<>(sales);

        boolean top = svc.topSales(sales, 100000, 2).get(0).id().equals("S3");
        boolean tie = svc.topSales(sales, 150000, 2).get(1).id().equals("S45");
        boolean emptyMax = svc.maxSale(List.of()).isEmpty();
        boolean max = svc.maxSale(sales).orElseThrow().id().equals("S3");
        boolean sum = svc.totalsByManager(sales).get("Ivanov") == 390000.0;
        boolean avg = svc.averageByRegion(sales, "Moscow") == 195000.0;
        boolean low = svc.topSales(sales, 240000, 5).size() == 1;
        boolean bad = false;
        try {
            Sale.parse("S9;Ivanov;Moscow;abc");
        } catch (IllegalArgumentException e) {
            bad = true;
        }
        boolean same = sales.equals(copy);
        boolean ok = top && tie && emptyMax && max && sum && avg && low && bad && same;
        System.out.println(ok ? "PASS" : "FAIL");
    }
}