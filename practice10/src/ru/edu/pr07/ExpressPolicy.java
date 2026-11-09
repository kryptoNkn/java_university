package ru.edu.pr07;
public class ExpressPolicy implements DeliveryPolicy {
    private static final double MAX_DISTANCE_KM = 15;
    private static final double MAX_WEIGHT_KG = 5;
    private static final double BASE = 100;
    private static final double PER_KM = 30;
    private static final double MIN_ORDER = 1000;

    @Override
    public boolean supports(DeliveryRequest request) {
        return request.distanceKm() > 0 && request.distanceKm() <= MAX_DISTANCE_KM && request.weightKg() > 0 && request.weightKg() <= MAX_WEIGHT_KG && request.orderAmount() >= MIN_ORDER;
    }

    @Override
    public double calculateCost(DeliveryRequest request) {
        return BASE + PER_KM * request.distanceKm();
    }
}
