package ru.edu.pr05;
import java.time.LocalDate;

public class SelfCheck {
    public static void main(String[] args) {
        int passed = 0;
        int total = 8;
        passed += check("типовой submit и approve", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-10");
            trip.submit();
            trip.approve();
            if (trip.getStatus() != TripStatus.APPROVED) {
                throw new AssertionError(trip.getStatus());
            }
        });
        passed += check("отмена из DRAFT", () -> {
            BusinessTrip trip = trip("2026-03-17", "2026-03-20");
            trip.cancel();
            if (trip.getStatus() != TripStatus.CANCELLED) {
                throw new AssertionError(trip.getStatus());
            }
        });
        passed += check("отмена после COMPLETED запрещена", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-05");
            trip.submit();
            trip.approve();
            trip.complete(LocalDate.of(2026, 6, 6));
            expectState(trip::cancel);
        });
        passed += check("пустая причина отклонения", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-05");
            trip.submit();
            expectArgument(() -> trip.reject("   "));
        });
        passed += check("дата окончания раньше даты начала", () -> {
            expectArgument(() -> trip("2026-06-05", "2026-06-01"));
        });
        passed += check("повторный approve", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-05");
            trip.submit();
            trip.approve();
            expectState(trip::approve);
        });
        passed += check("complete в день окончания запрещён", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-05");
            trip.submit();
            trip.approve();
            expectState(() -> trip.complete(LocalDate.of(2026, 6, 5)));
        });
        passed += check("complete на следующий день после окончания", () -> {
            BusinessTrip trip = trip("2026-06-01", "2026-06-05");
            trip.submit();
            trip.approve();
            trip.complete(LocalDate.of(2026, 6, 6));
            if (trip.getStatus() != TripStatus.COMPLETED) {
                throw new AssertionError(trip.getStatus());
            }
        });
        System.out.println(passed == total ? "PASS" : "FAIL " + passed + "/" + total);
    }

    private static BusinessTrip trip(String start, String end) {
        return new BusinessTrip("T-1", LocalDate.parse(start), LocalDate.parse(end));
    }

    private static void expectState(Runnable action) {
        try {
            action.run();
            throw new AssertionError("ожидался IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    private static void expectArgument(Runnable action) {
        try {
            action.run();
            throw new AssertionError("ожидался IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static int check(String name, Runnable test) {
        try {
            test.run();
            System.out.println("OK  " + name);
            return 1;
        } catch (Throwable ex) {
            System.out.println("FAIL " + name + " — " + ex);
            return 0;
        }
    }
}
