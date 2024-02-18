package com.example.dronetaskv1.model;

public enum DroneModel {
    
    LIGHTWEIGHT,
    MIDDLEWEIGHT,
    CRUISEWEIGHT,
    HEAVYWEIGHT;

    public static DroneModel classifyModel(double weight) {
        if(weight > 400) return DroneModel.HEAVYWEIGHT;
        else if(weight > 300) return DroneModel.CRUISEWEIGHT;
        else if(weight > 200) return DroneModel.MIDDLEWEIGHT;
        else{return DroneModel.LIGHTWEIGHT;}
    }

}
