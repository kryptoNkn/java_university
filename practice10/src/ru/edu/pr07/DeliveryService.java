package ru.edu.pr07;
public class DeliveryService {
    private final DeliveryPolicyFactory factory;
    public DeliveryService(DeliveryPolicyFactory factory) {
        this.factory = factory;
    }

    public double calculate(DeliveryRequest request) {
        if (request == null || request.type() == null) {
            throw new IllegalArgumentException("заявка на доставку не заполнена");
        }

        DeliveryPolicy policy = factory.get(request.type());
        if (!policy.supports(request)) {
            throw new IllegalArgumentException("доставка этим способом невозможна");
        }

        return policy.calculateCost(request);
    }
}
