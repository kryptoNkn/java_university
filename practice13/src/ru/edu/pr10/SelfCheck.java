package ru.edu.pr10;
import java.time.LocalDateTime;
public class SelfCheck {
    public static void main(String[] args) {
        boolean ok = true;
        BookingRepository repo = new BookingRepository();
        BookingService service = new BookingService(repo);
        LocalDateTime now = LocalDateTime.of(2026, 10, 1, 8, 0);

        try {
            service.book("B1", "R1", LocalDateTime.of(2026, 10, 1, 10, 0), LocalDateTime.of(2026, 10, 1, 11, 0), now);
            service.book("B2", "R1", LocalDateTime.of(2026, 10, 1, 11, 0), LocalDateTime.of(2026, 10, 1, 12, 0), now);
            service.book("B3", "R2", LocalDateTime.of(2026, 10, 1, 10, 0), LocalDateTime.of(2026, 10, 1, 10, 45), now);
            service.book("B4", "R3", LocalDateTime.of(2026, 10, 1, 23, 30), LocalDateTime.of(2026, 10, 2, 0, 30), now);

            boolean conflictCaught = false;
            try {
                service.book("B5", "R1", LocalDateTime.of(2026, 10, 1, 10, 30), LocalDateTime.of(2026, 10, 1, 11, 30), now);
            } catch (IllegalStateException e) {
                conflictCaught = true;
            }
            if (!conflictCaught) {
                System.out.println("FAIL, конфликт не обнаружен");
                ok = false;
            }

            boolean minCaught = false;
            try {
                service.book("B6", "R4", LocalDateTime.of(2026, 10, 1, 10, 0), LocalDateTime.of(2026, 10, 1, 10, 44), now);
            } catch (IllegalArgumentException e) {
                minCaught = true;
            }
            if (!minCaught) {
                System.out.println("FAIL, минимальная продолжительность");
                ok = false;
            }

            boolean pastCaught = false;
            try {
                service.book("B7", "R4", LocalDateTime.of(2026, 10, 1, 7, 0), LocalDateTime.of(2026, 10, 1, 8, 0), now);
            } catch (IllegalArgumentException e) {
                pastCaught = true;
            }
            if (!pastCaught) {
                System.out.println("FAIL, после бронирования");
                ok = false;
            }

            boolean maxCaught = false;
            try {
                service.book("B8", "R4", LocalDateTime.of(2026, 10, 1, 10, 0), LocalDateTime.of(2026, 10, 1, 18, 1), now);
            } catch (IllegalArgumentException e) {
                maxCaught = true;
            }
            if (!maxCaught) {
                System.out.println("Максимальная продолжительность FAIL");
                ok = false;
            }

            boolean emptyCaught = false;
            try {
                service.book("", "R4", LocalDateTime.of(2026, 10, 1, 13, 0), LocalDateTime.of(2026, 10, 1, 14, 0), now);
            } catch (IllegalArgumentException e) {
                emptyCaught = true;
            }
            if (!emptyCaught) {
                System.out.println("FAIL, пустой идентификатор");
                ok = false;
            }

            boolean stillFree = service.isAvailable("R1", LocalDateTime.of(2026, 10, 1, 12, 0), LocalDateTime.of(2026, 10, 1, 13, 0));
            if (!stillFree) {
                System.out.println("Соседний объект FAIL должен быть свободен");
                ok = false;
            }
        } catch (Exception e) {
            System.out.println("FAIL исключение " + e.getMessage());
            ok = false;
        }

        System.out.println(ok ? "PASS" : "FAIL завершить методы TODO");
    }
}