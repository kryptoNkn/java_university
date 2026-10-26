package ru.edu.pr03;
public class ApprovalRouter {
    public static final long FINANCE_LIMIT = 500_000L;
    public static final int RISK_PRIORITY_PERCENT = 45;
    public static final long RISK_AMOUNT_LIMIT = FINANCE_LIMIT * RISK_PRIORITY_PERCENT / 100;

    public ApprovalRoute route(long amount, boolean urgent, RiskLevel risk, boolean containsPersonalData) {
        if(amount < 0) {
            throw new IllegalArgumentException("сумма не может быть отрицательной");
        }
        if (risk == null) {
            throw new IllegalArgumentException("уровень риска не задан");
        }
        if(requiresSecurity(containsPersonalData)) {
            return ApprovalRoute.SECURITY_REVIEW;
        }
        if(requiresDirector(amount, risk)) {
            return ApprovalRoute.DIRECTOR_REVIEW;
        }
        if(requiresFinance(amount)) {
            return ApprovalRoute.FINANCE_REVIEW;
        }
        if(requiresFastTrack(amount, urgent)) {
            return ApprovalRoute.FAST_TRACK;
        }
        
        return ApprovalRoute.STANDARD;
    }

    private boolean requiresSecurity(boolean containsPersonalData) {
        return containsPersonalData;
    }

    private boolean requiresDirector(long amount, RiskLevel risk) {
        return risk == RiskLevel.HIGH && amount >= RISK_AMOUNT_LIMIT;
    }

    private boolean requiresFinance(long amount) {
        return amount >= FINANCE_LIMIT;
    }

    private boolean requiresFastTrack(long amount, boolean urgent) {
        return urgent && amount < FINANCE_LIMIT;
    }
}
