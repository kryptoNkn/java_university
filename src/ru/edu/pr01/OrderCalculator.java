package ru.edu.pr01;
import java.util.Locale;

public class OrderCalculator {
    private static final double MAX_DISCOUNT_PERCENT = 45.0;

    public static boolean isValid(int quantity, double unitPrice, double discountPercent) {
        return quantity > 0
            && unitPrice > 0
            && discountPercent >= 0
            && discountPercent <= MAX_DISCOUNT_PERCENT;
    }

    public static double calculateBase(int quantity, double unitPrice) {
        return quantity * unitPrice;
    }

    public static double applyDiscount(double base, double discountPercent) {
        return base * (1.0 - discountPercent / 100.0);
    }

    public static double calculateVat(double discounted, double vatPercent) {
        return discounted * vatPercent / 100.0; // НДС
    }

    public static double calculateTotal(int quantity, double unitPrice, double discountPercent, double vatPercent) {
        double base = calculateBase(quantity, unitPrice);
        double discounted = applyDiscount(base, discountPercent);
        return discounted + calculateVat(discounted, vatPercent);
    }

    public static void main(String[] args) {
        int quantity = 2;
        double unitPrice = 100.0;
        double discountPercent = 10.0;
        double vatPercent = 20.0;

        if (!isValid(quantity, unitPrice, discountPercent)) {
            System.out.println("некорректные данные");
            return;
        }

        double total = calculateTotal(quantity, unitPrice, discountPercent, vatPercent);
        System.out.printf(Locale.forLanguageTag("ru-RU"), "%.2f руб.%n", total);
    }
}
