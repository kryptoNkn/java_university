package ru.edu.pr10; 
import java.time.*; 
public record Booking(String id,String roomId,LocalDateTime start,LocalDateTime end) { 
    public Duration duration(){
        return Duration.between(start,end);
    } 
}