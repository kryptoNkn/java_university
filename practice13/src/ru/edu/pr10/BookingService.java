package ru.edu.pr10;
import java.time.*; 
import java.util.*;
public class BookingService { 
    private final BookingRepository repo;
    private static final int MIN_MINUTES = 45;
    private static final int MAX_HOURS = 8;
    private static final int BUFFER_MINUTES = 0;

    public BookingService(BookingRepository r){
        repo=r;
    }

    public Booking book(String id,String room,LocalDateTime start,LocalDateTime end,LocalDateTime now) { 
        validate(id,room,start,end,now);

        if(!isAvailable(room,start,end)) {
            throw new IllegalStateException("комната занята в этот интервал");
        }

        Booking booking = new Booking(id, room, start, end);
        repo.save(booking);
        return booking;
    }

    public boolean isAvailable(String room,LocalDateTime start,LocalDateTime end) { 
        if(room == null || room.isBlank() || start == null || end == null) {
            return false;
        }

        for(Booking existing : repo.findByRoom(room)) {
            if(overlapsWithBuffer(existing, start, end)) {
                return false;
            }
        }

        return true;
    }

    boolean overlaps(Booking b,LocalDateTime start,LocalDateTime end) { 
        return start.isBefore(b.end()) && b.start().isBefore(end); 
    }

    private void validate(String id, String room, LocalDateTime start, LocalDateTime end, LocalDateTime now) {
        if(id == null || id.isBlank()) {
            throw new IllegalArgumentException("пустой id");
        } 

        if(room == null || room.isBlank()) {
            throw new IllegalArgumentException("пустая комната");
        }

        if(start == null || end == null || now == null) {
            throw new IllegalArgumentException("время не задано");
        }

        if(!end.isAfter(start)) {
            throw new IllegalArgumentException("конец должен быть позже начала");
        }

        if(!start.isAfter(now) && !start.isEqual(now)) {
            if(start.isBefore(now)) {
                throw new IllegalArgumentException("нельзя бронировать время в прошлом");
            }
        }

        Duration d = Duration.between(start, end);
        if(d.toMinutes() < MIN_MINUTES) {
            throw new IllegalArgumentException("минимальная длительность: " + MIN_MINUTES + " минут");
        }

        if(d.compareTo(Duration.ofHours(MAX_HOURS)) > 0) {
            throw new IllegalArgumentException("максимальная длительность: " + MAX_HOURS + " часов");
        }
    }

    private boolean overlapsWithBuffer(Booking b, LocalDateTime start, LocalDateTime end) {
        LocalDateTime bufferedStart = start.minusMinutes(BUFFER_MINUTES);
        LocalDateTime bufferedEnd = end.plusMinutes(BUFFER_MINUTES);

        return bufferedStart.isBefore(b.end()) && b.start().isBefore(bufferedEnd);
    }

}