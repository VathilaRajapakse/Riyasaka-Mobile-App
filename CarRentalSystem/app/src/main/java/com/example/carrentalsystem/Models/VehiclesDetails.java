package com.example.carrentalsystem.Models;

public class VehiclesDetails {

    String id;
    String image;
    String VehicleNo;
    String Model;
    String Price;
    String Brand;
    String Passenger;
    String Category;
    String Driver;

    public VehiclesDetails() {
    }

    public VehiclesDetails(String id, String image, String vehicleNo, String model, String price, String brand, String passenger, String category, String driver) {
        this.id = id;
        this.image = image;
        VehicleNo = vehicleNo;
        Model = model;
        Price = price;
        Brand = brand;
        Passenger = passenger;
        Category = category;
        Driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getPassenger() {
        return Passenger;
    }

    public void setPassenger(String passenger) {
        Passenger = passenger;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }
}
