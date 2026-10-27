package ru.edu.pr05;
import java.time.LocalDate;

public class BusinessTrip {
    public static final int MIN_REJECTION_REASON_LENGTH = 10;
    private final String id;
    private final LocalDate start;
    private final LocalDate end;
    private TripStatus status = TripStatus.DRAFT;
    private String rejectionReason;

    public BusinessTrip(String id, LocalDate start, LocalDate end) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id командировки обязателен");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("даты начала и окончания обязательны");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("дата окончания раньше даты начала");
        }
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public void submit() {
        requireStatus(TripStatus.DRAFT, "submit");
        status = TripStatus.SUBMITTED;
    }

    public void approve() {
        requireStatus(TripStatus.SUBMITTED, "approve");
        status = TripStatus.APPROVED;
    }

    public void reject(String reason) {
        requireStatus(TripStatus.SUBMITTED, "reject");
        if (reason == null || reason.trim().length() < MIN_REJECTION_REASON_LENGTH) {
            throw new IllegalArgumentException("причина отклонения обязательна и не короче " + MIN_REJECTION_REASON_LENGTH + " символов");
        }
        rejectionReason = reason.trim();
        status = TripStatus.REJECTED;
    }

    public void cancel() {
        if (status != TripStatus.DRAFT && status != TripStatus.SUBMITTED && status != TripStatus.APPROVED) {
            throw new IllegalStateException("cancel недоступен из статуса " + status);
        }
        status = TripStatus.CANCELLED;
    }

    public void complete(LocalDate today) {
        if (today == null) {
            throw new IllegalArgumentException("текущая дата обязательна");
        }
        requireStatus(TripStatus.APPROVED, "complete");
        if (!today.isAfter(end)) {
            throw new IllegalStateException("завершить можно только после даты окончания");
        }
        status = TripStatus.COMPLETED;
    }

    public TripStatus getStatus() { 
        return status; 
    }

    public String getRejectionReason() { 
        return rejectionReason; 
    }

    private void requireStatus(TripStatus expected, String action) {
        if (status != expected) {
            throw new IllegalStateException(action + " недоступен из статуса " + status);
        }
    }
}
