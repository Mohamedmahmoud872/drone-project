
# Drones Task 

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

## Task Description
We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

### Requirements
While implementing your solution **please take care of the following requirements**: 
#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests
- Advice: Show us how you work through your commit history.

## Assumed Scenario

1- The Drones are Registered to the system. \
2- The Registered Drones are loaded by Medications. \
3- System Periodically check if any Drone is in any state except Loading state.
- When Drone is Filled "Loaded state" : 
    System simulates Delivering process, reduces Battery capacity, changes Drone state to Delivering state and stores the changes to logs file.
- When Drone is in Delivering state : 
    System simulates unloading Medications from Drone, reduces Battery capacity, changes Drone state to Delivered state and stores the changes to logs file.
- When Drone is in Delivered state : 
    System simulates Returning process, reduces Battery capacity, changes Drone state to Returning state and stores the changes to logs file. 
- When Drone is in Returning state : 
    System simulates Landing process, reduces Battery capacity and stores the changes to logs file and checks if Drone Battery Capacity is less than "25%" then it changes its state to idle so, this drone can't load anymore Medications, but if its Battery Capacity is more than 25% then it changes its state to Loading so, this drone is ready to be loaded again.

## API Reference

### Register Drone

```http
  POST /api/v1/drone/register-drone
```
```json
{
    "serialNumber" : "111116",
    "droneWeight" : 350,
    "batteryCapacity" : 90
}
```
The API takes the serial number, limit of the drone and the battery capacity
and it classifies its state and model depending on provided weight limit and battery capacity and returns all the Drone data after classification.

```json
{
    "serialNumber": "111116",
    "droneModel": "CRUISEWEIGHT",
    "batteryCapacity": 90,
    "droneState": "LOADING",
    "loadedWeight": 0.0
}
```

### Get Battery Capacity of a Drone

```http
  GET /api/v1/drone/drone-battery/{serial}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `serial`      | `string` | **Required**. Id of Drone to fetch |

The API takes the serial number of the drone in url and returns its battery capacity

```json
{
    "serialNumber": "111114",
    "batteryCapacity": 80
}
```
  
### Get available drones to be loaded

```http
GET /api/v1/drone/available-drones
```

The API returns all Drones in Loading state

```json
[
    {
        "serialNumber": "111113",
        "droneModel": "MIDDLEWEIGHT",
        "batteryCapacity": 60,
        "droneState": "LOADING",
        "loadedWeight": 50.0
    },
    {
        "serialNumber": "111114",
        "droneModel": "HEAVYWEIGHT",
        "batteryCapacity": 80,
        "droneState": "LOADING",
        "loadedWeight": 150.0
    },
    {
        "serialNumber": "111115",
        "droneModel": "LIGHTWEIGHT",
        "batteryCapacity": 50,
        "droneState": "LOADING",
        "loadedWeight": 0.0
    }
]
```
### load Drone with Medications
```http
  POST /api/v1/meds/load-medications
```
```json
{
    "serialNumber": "111113",
    "medications" :[
        {
            "medicationName": "VitaminC",
            "medicationWeight": 50,
            "medicationCode": "VIT1234",
            "medicationImage": "image.png",
            "numberOfPackages": 2

        },
        {
            "medicationName": "Omega3",
            "medicationWeight": 100,
            "medicationCode": "OMEGA3",
            "medicationImage": "image.png"
        }
    ]
}
```
The API takes the serial number of the Drone and a List of Medications to be loaded and return them back after assigning them to the Drone.

```json
{
    "serialNumber": "111113",
    "medications": [
        {
            "medicationName": "VitaminC",
            "medicationWeight": 50.0,
            "medicationCode": "VIT1234",
            "medicationImage": "image.png",
            "numberOfPackages": 2
        },
        {
            "medicationName": "Omega3",
            "medicationWeight": 100.0,
            "medicationCode": "OMEGA3",
            "medicationImage": "image.png",
            "numberOfPackages": 1
        }
    ]
}
```
### Get Medications loaded on a specific Drone

```http
  GET /api/v1/meds/get-medications/{serial}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `serial`      | `string` | **Required**. Id of Drone to fetch |

The API takes the serial number of the drone in url and returns all the Medications are loaded by it.

```json
[
    {
        "medicationName": "VitaminC",
        "medicationWeight": 50.0,
        "medicationCode": "VIT1234",
        "medicationImage": "image.png",
        "numberOfPackages": 2
    },
    {
        "medicationName": "Omega3",
        "medicationWeight": 100.0,
        "medicationCode": "OMEGA3",
        "medicationImage": "image.png",
        "numberOfPackages": 1
    }
]
```

## How to Install

- Download the project
-  open it using Intellij Idea
-  open pom.xml file and  run maven to download all dependencies

## How to run
after installing The Dependencies required for the project, open DroneTaskApplication class and run it. 

## How to test
There are two types of testing I included in the project

### Integration Testing :
`` \dronetaskv1\src\test\java\com\example\dronetaskv1\controller ``

This Package includes all integration test cases i assumed, to test them :
- open any class in this package right click on it and click on run "class name".
- If you want to test specific test case you can select the method you want to test and right click on it and click on run "method name".

### Unit Testing :
`` \dronetaskv1\src\test\java\com\example\dronetaskv1\service ``

This Package includes all unit test cases i assumed, to test them :
- open any class in this package right click on it and click on run "class name".
- If you want to test specific test case you can select the method you want to test and right click on it and click on run "method name".
