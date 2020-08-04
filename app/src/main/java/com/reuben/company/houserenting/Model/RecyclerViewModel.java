package com.reuben.company.houserenting.Model;

public class RecyclerViewModel {

    int id; //This is for image
    String phone; //This is for price
    String room; //This is for description
    String location; //This is for Location
    String price; //This is for price
    String parking; //This is for description
    String kitchen; //This is for Location
    String descriptions; //This is for Location

//    String bedroom; //This is for price
//    String dinningroom; //This is for description
//    String livingroom; //This is for Location

    public RecyclerViewModel(int id, String phone, String room,
                             String location, String price, String parking,
                             String kitchen, String descriptions) {
        this.id = id;
        this.phone = phone;
        this.room = room;
        this.location = location;
        this.price = price;
        this.parking = parking;
        this.kitchen = kitchen;
        this.descriptions = descriptions;
//        this.bedroom = bedroom;
//        this.dinningroom = dinningroom;
//        this.livingroom = livingroom;
    }

    //This is the description
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

    //This is the picture
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //This is For location
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //This is for Location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    //    public String getBedroom() {
//        return bedroom;
//    }
//
//    public void setBedroom(String bedroom) {
//        this.bedroom = bedroom;
//    }
//
//    public String getDinningroom() {
//        return dinningroom;
//    }
//
//    public void setDinningroom(String dinningroom) {
//        this.dinningroom = dinningroom;
//    }
//
//    public String getLivingroom() {
//        return livingroom;
//    }
//
//    public void setLivingroom(String livingroom) {
//        this.livingroom = livingroom;
//    }
}
