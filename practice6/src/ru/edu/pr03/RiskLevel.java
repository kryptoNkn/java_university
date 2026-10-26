package ru.edu.pr03; public enum RiskLevel { 
    LOW, 
    MEDIUM, 
    HIGH;

    public static RiskLevel parse(String raw) {
    if(raw == null || raw.isBlank()) {
        throw new IllegalArgumentException("пустой уровень риска");
    }
    try {
        return RiskLevel.valueOf(raw.trim().toUpperCase());
    } catch(IllegalArgumentException ex) {
        throw new IllegalArgumentException("неверный уровень риска: " + raw);
    }
}
}