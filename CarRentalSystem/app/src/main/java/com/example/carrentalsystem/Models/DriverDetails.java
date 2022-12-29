package com.example.carrentalsystem.Models;

public class DriverDetails {

    String id;
    String name;
    String nic;
    String licen;
    String contact;
    String email;

    public DriverDetails() {
    }

    public DriverDetails(String id, String name, String nic, String licen, String contact, String email) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.licen = licen;
        this.contact = contact;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getLicen() {
        return licen;
    }

    public void setLicen(String licen) {
        this.licen = licen;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
