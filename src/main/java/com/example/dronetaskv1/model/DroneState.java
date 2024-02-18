package com.example.dronetaskv1.model;

public enum DroneState {

    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING;


    public static DroneState classifyState(int batteryCapacity) {

        if(batteryCapacity <= 25) return DroneState.IDLE;
        else{return DroneState.LOADING;}

    }
    
}
