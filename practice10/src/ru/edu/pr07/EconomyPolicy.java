package ru.edu.pr07;
public class EconomyPolicy implements DeliveryPolicy {
    static final double MIN_COST = 45;
    private static final double MAX_DISTANCE_KM = 100;
    private static final double MAX_WEIGHT_KG = 15;
    private static final double PER_KM = 8;

    @Override
    public boolean supports(DeliveryRequest request) {
        return request.distanceKm() > 0 && request.distanceKm() <= MAX_DISTANCE_KM && request.weightKg() > 0 && request.weightKg() <= MAX_WEIGHT_KG && request.orderAmount() > 0;
    }

    @Override
    public double calculateCost(DeliveryRequest request) {
        return Math.max(MIN_COST, PER_KM * request.distanceKm());
    }
}
