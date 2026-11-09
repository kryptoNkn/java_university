package ru.edu.pr07; 

public class DeliveryPolicyFactory { 
    public DeliveryPolicy get(DeliveryType type) { 
        if(type == null) {
            throw new IllegalArgumentException("способ доставки не указан");
        }
        return switch(type) {
            case STANDARD -> new StandardCourierPolicy();
            case EXPRESS -> new ExpressPolicy();
            case PICKUP -> new PickupPolicy();
            case ECONOMY -> new EconomyPolicy();
        };
    } 
}
