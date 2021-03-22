package com.sysdo.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "devices" )
public class Device {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String devicename;
    private String maker;
    private String model;
    private int price;
    private String location;
    private String phoneNumber;

    @Column(nullable = false, updatable = false) @CreationTimestamp
    private Date posted;

    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String longDescription;
    private String imagename;

    @ManyToOne
    private User user;



    public Device() {
    }

    public Device(String devicename, String maker, String model, int price, String location, String phoneNumber, Date posted, String shortDescription, User user) {
        this.devicename = devicename;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.posted = posted;
        this.shortDescription = shortDescription;
        this.user = user;
    }

    public Device(String devicename, String maker, String model, int price, String location, String phoneNumber, Date posted, String shortDescription, String longDescription, User user) {
        this.devicename = devicename;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.posted = posted;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.user = user;
    }

    public Device(Long id, String devicename, String maker, String model, int price, String location, String phoneNumber, Date posted, String shortDescription, String imagename, User user) {
        this.id = id;
        this.devicename = devicename;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.posted = posted;
        this.shortDescription = shortDescription;
        this.imagename = imagename;
        this.user = user;
    }

    public Device(String devicename, String maker, String model, int price, String location, String phoneNumber, Date posted, String shortDescription, String longDescription, String imagename, User user) {
        this.devicename = devicename;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.posted = posted;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imagename = imagename;
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String deviceType) {
        this.devicename = deviceType;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Device{" +
                "devicename='" + devicename + '\'' +
                '}';
    }
}
