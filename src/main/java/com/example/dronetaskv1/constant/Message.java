package com.example.dronetaskv1.constant;

public class Message {

    public static final String DRONE_EXISTS = "Drone you are trying to register already exists";
    public static final String DRONE_NOT_EXISTS = "Didn't find drone with this serial number";
    public static final String DRONE_EMPTY = "Drone not containing any medications yet";
    public static final String DRONE_WEIGHT_EXCEEDED = "Medications you tried to load had weight more than available in this drone";
    public static final String DRONE_DELIVERING = "delivering Medications";
    public static final String DRONE_DELIVERED = "reached its destination and unloading Medications";
    public static final String DRONE_RETURNING = "returning to its Base";
    public static final String DRONE_RETURNED = "returned to its Base and landing";
    public static final String MEDICATION_EXISTS = "Medication code already exists";
    public static final String EMPTY_DATA = "The Data Provided is null or empty";

}
