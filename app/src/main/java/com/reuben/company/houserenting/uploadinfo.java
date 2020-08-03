package com.reuben.company.houserenting;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public String imageLoc;
    public String imagePrice;
    public String room;
    public String kitchen;
    public String parking;
    public String phone;

    public uploadinfo() {
    }

//    public uploadinfo(String description, String url, String loc, String price, String hroom, String hKitchen, String hParking) {
//        this.imageName = description;
//        this.imageURL = url;
//        this.imageLoc = loc;
//        this.imagePrice = price;
//        this.room = hroom;
//        this.kitchen = hKitchen;
//        this.parking = hParking;
//    }


    public uploadinfo(String imageName, String imageURL, String imageLoc, String imagePrice,
                      String room, String kitchen, String parking, String phone) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.imageLoc = imageLoc;
        this.imagePrice = imagePrice;
        this.room = room;
        this.kitchen = kitchen;
        this.parking = parking;
        this.phone = phone;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageLoc() {
        return imageLoc;
    }

    public String getImagePrice() {
        return imagePrice;
    }

    public String getRoom() {
        return room;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getParking() {
        return parking;
    }

    public String getPhone() {
        return phone;
    }
}

