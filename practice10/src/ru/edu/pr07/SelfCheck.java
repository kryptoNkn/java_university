package ru.edu.pr07;
public class SelfCheck {
    public static void main(String[] args) {
        boolean ok = false;
        try { ok = (new PickupPolicy().calculateCost(new DeliveryRequest(DeliveryType.PICKUP,0,10,1000))==0); } catch (Exception e) { System.out.println("FAIL exception " + e.getMessage()); }
        System.out.println(ok ? "PASS" : "FAIL  complete TODO methods");
    }
}
