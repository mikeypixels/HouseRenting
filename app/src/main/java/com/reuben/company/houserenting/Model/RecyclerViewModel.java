package com.reuben.company.houserenting.Model;

public class RecyclerViewModel {

    String imageURL; //This is for image
    String imageLoc; //This is for price
    String imageName; //This is for description
    String imagePrice; //This is for Location
    String houseRoom; //This is for price
    String houseKitchen; //This is for description
    String houseParking; //This is for Location
    String contacts; //This is for Location

//    String bedroom; //This is for price
//    String dinningroom; //This is for description
//    String livingroom; //This is for Location

    //Create Constructor
    public RecyclerViewModel() {

    }

    public RecyclerViewModel(String imageURL, String imageLoc, String imageName,
                             String imagePrice, String houseRoom, String houseKitchen,
                             String houseParking, String contacts) {
        this.imageURL = imageURL;
        this.imageLoc = imageLoc;
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.houseRoom = houseRoom;
        this.houseKitchen = houseKitchen;
        this.houseParking = houseParking;
        this.contacts = contacts;
//        this.bedroom = bedroom;
//        this.dinningroom = dinningroom;
//        this.livingroom = livingroom;
    }

    //This is the description
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    //This is the picture
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    //This is For location
    public String getImageLoc() {
        return imageLoc;
    }

    public void setImageLoc(String imageLoc) {
        this.imageLoc = imageLoc;
    }

    //This is for Location
    public String getImagePrice() {
        return imagePrice;
    }
    public void setImagePrice(String imagePrice) {
        this.imagePrice = imagePrice;
    }

    public String getHouseRoom() {
        return houseRoom;
    }

    public void setHouseRoom(String houseRoom) {
        this.houseRoom = houseRoom;
    }

    public String getHouseKitchen() {
        return houseKitchen;
    }

    public void setHouseKitchen(String houseKitchen) {
        this.houseKitchen = houseKitchen;
    }

    public String getHouseParking() {
        return houseParking;
    }

    public void setHouseParking(String houseParking) {
        this.houseParking = houseParking;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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
