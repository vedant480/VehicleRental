package com.vehicle.rental;

public class Vehicle {

    private int vehicleId;
    private String vehicleNumber;
    private String brand;
    private String model;
    private String vehicleType;
    private double pricePerDay;
    private String status;

    public Vehicle() {
    }

    public Vehicle(int vehicleId, String vehicleNumber, String brand,
                   String model, String vehicleType,
                   double pricePerDay, String status) {

        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}