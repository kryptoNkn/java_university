package ru.edu.pr01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class OrderCalculator {
    public static final int MAX_QUANTITY = 10_000;
    public static final double MAX_UNIT_PRICE = 5_000_000.0;
    public static final double MAX_DISCOUNT_PERCENT = 45.0;
    public static final double VAT_PERCENT = 20.0;

    public static boolean isValid(int quantity, double unitPrice, double discountPercent) {
        return isValidQuantity(quantity)
            && isValidUnitPrice(unitPrice)
            && isValidDiscount(discountPercent);
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity >= 1 && quantity <= MAX_QUANTITY;
    }

    public static boolean isValidUnitPrice(double unitPrice) {
        return unitPrice > 0 && unitPrice <= MAX_UNIT_PRICE;
    }

    public static boolean isValidDiscount(double discountPercent) {
        return discountPercent >= 0 && discountPercent <= MAX_DISCOUNT_PERCENT;
    }

    public static double calculateBase(int quantity, double unitPrice) {
        return quantity * unitPrice;
    }

    public static double applyDiscount(double base, double discountPercent) {
        return base * (1.0 - discountPercent / 100.0);
    }

    public static double calculateVat(double discounted, double vatPercent) {
        return discounted * vatPercent / 100.0;
    }

    public static double calculateTotal(int quantity, double unitPrice, double discountPercent, double vatPercent) {
        double base = calculateBase(quantity, unitPrice);
        double discounted = applyDiscount(base, discountPercent);
        return discounted + calculateVat(discounted, vatPercent);
    }

    public static String formatTotal(double total) {
        return String.format(Locale.forLanguageTag("ru-RU"), "%.2f руб.", total);
    }

    public static String evaluateLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return "отсутствует значение";
        }
        String[] parts = line.split(";", -1);
        if (parts.length != 3) {
            return "неверный формат";
        }
        for (String part : parts) {
            if (part.trim().isEmpty()) {
                return "отсутствует значение";
            }
        }
        int quantity;
        double unitPrice;
        double discountPercent;
        try {
            quantity = Integer.parseInt(parts[0].trim());
            unitPrice = Double.parseDouble(parts[1].trim());
            discountPercent = Double.parseDouble(parts[2].trim());
        } catch (NumberFormatException ex) {
            return "неверный формат";
        }
        if (!isValidQuantity(quantity)) {
            return "некорректное количество";
        }
        if (!isValidUnitPrice(unitPrice)) {
            return "некорректная цена";
        }
        if (!isValidDiscount(discountPercent)) {
            return "некорректная скидка";
        }
        double total = calculateTotal(quantity, unitPrice, discountPercent, VAT_PERCENT);
        return formatTotal(total);
    }

    public static void processFile(String path) {
        File file = new File(path);
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            boolean sawOrder = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    System.out.println(evaluateLine(line));
                    continue;
                }
                if (line.startsWith("quantity")) {
                    continue;
                }
                sawOrder = true;
                System.out.println(evaluateLine(line));
            }
            if (!sawOrder) {
                System.out.println("отсутствует значение");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("отсутствует значение");
        }
    }

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "data/sample.csv";
        processFile(path);
    }
}
