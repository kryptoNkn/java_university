package ru.edu.pr07;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
public class Main {
    public static void main(String[] args) throws IOException {
        Path file = Path.of(args.length > 0 ? args[0] : "data/sample.csv");
        DeliveryService service = new DeliveryService(new DeliveryPolicyFactory());
        List<String> lines = Files.readAllLines(file);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            try {
                DeliveryRequest request = parse(line);
                double cost = service.calculate(request);
                System.out.printf("%s -> %.2f%n", request.type(), cost);
            } catch (IllegalArgumentException ex) {
                System.out.println("Строка " + (i + 1) + ": " + ex.getMessage());
            }
        }
    }

    static DeliveryRequest parse(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Ожидалось 4 поля");
        }
        for (String part : parts) {
            if (part.isBlank()) {
                throw new IllegalArgumentException("Пустое поле");
            }
        }
        DeliveryType type;
        try {
            type = DeliveryType.valueOf(parts[0].trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Неизвестный способ доставки");
        }
        double distance = readNumber(parts[1], "расстояние");
        double weight = readNumber(parts[2], "вес");
        double amount = readNumber(parts[3], "сумма заказа");
        if (type == DeliveryType.PICKUP && distance != 0) {
            throw new IllegalArgumentException("Самовывоз возможен только при расстоянии 0");
        }
        return new DeliveryRequest(type, distance, weight, amount);
    }

    private static double readNumber(String raw, String name) {
        try {
            return Double.parseDouble(raw.trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Неверный формат поля: " + name);
        }
    }
}