package ru.edu.pr07;
public class StandardCourierPolicy implements DeliveryPolicy {
    private static final double MAX_DISTANCE_KM = 30;
    private static final double MAX_WEIGHT_KG = 20;
    private static final double BASE = 50;
    private static final double PER_KM = 15;

    @Override
    public boolean supports(DeliveryRequest request) {
        return request.distanceKm() > 0 && request.distanceKm() <= MAX_DISTANCE_KM && request.weightKg() > 0 && request.weightKg() <= MAX_WEIGHT_KG && request.orderAmount() > 0;
    }

    @Override
    public double calculateCost(DeliveryRequest request) {
        return BASE + PER_KM * request.distanceKm();
    }
}
