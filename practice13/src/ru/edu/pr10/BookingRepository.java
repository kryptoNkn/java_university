package ru.edu.pr10; 
import java.util.*; 
public class BookingRepository { 
    private final List<Booking> data = new ArrayList<>(); 
    
    public void save(Booking b) {
        data.add(b);
    } 

    public List<Booking> findByRoom(String r){ 
        return data.stream().filter(x->x.roomId().equals(r)).toList(); 
    } 
}