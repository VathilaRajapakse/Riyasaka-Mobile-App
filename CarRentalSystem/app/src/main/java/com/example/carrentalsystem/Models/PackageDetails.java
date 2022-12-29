package com.example.carrentalsystem.Models;

public class PackageDetails {

    String id;
    String image;
    String number;
    String model;
    String brand;
    String name;
    String destination;
    String days;
    String price;

    public PackageDetails() {
    }

    public PackageDetails(String id, String image, String number, String model, String brand, String name, String destination, String days, String price) {
        this.id = id;
        this.image = image;
        this.number = number;
        this.model = model;
        this.brand = brand;
        this.name = name;
        this.destination = destination;
        this.days = days;
        this.price = price;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
