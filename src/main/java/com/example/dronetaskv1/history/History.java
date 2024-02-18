package com.example.dronetaskv1.history;

import org.springframework.stereotype.Component;
import com.example.dronetaskv1.model.Drone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public interface History {

    public void updateHistory(Drone drone, String event);


    default String formatEventDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(LocalDateTime.now());
    }

}
