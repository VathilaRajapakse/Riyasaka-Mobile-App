package com.example.carrentalsystem.Models;

public class BookingDetails {

    String id;
    String image;
    String number;
    String price;
    String rentaldate;
    String rentdate;
    String handover;
    String customername;
    String customercontact;

    public BookingDetails() {
    }

    public BookingDetails(String id, String image, String number, String price, String rentaldate, String rentdate, String handover, String customername, String customercontact) {
        this.id = id;
        this.image = image;
        this.number = number;
        this.price = price;
        this.rentaldate = rentaldate;
        this.rentdate = rentdate;
        this.handover = handover;
        this.customername = customername;
        this.customercontact = customercontact;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRentaldate() {
        return rentaldate;
    }

    public void setRentaldate(String rentaldate) {
        this.rentaldate = rentaldate;
    }

    public String getRentdate() {
        return rentdate;
    }

    public void setRentdate(String rentdate) {
        this.rentdate = rentdate;
    }

    public String getHandover() {
        return handover;
    }

    public void setHandover(String handover) {
        this.handover = handover;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomercontact() {
        return customercontact;
    }

    public void setCustomercontact(String customercontact) {
        this.customercontact = customercontact;
    }
}
