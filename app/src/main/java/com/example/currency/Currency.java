package com.example.currency;

public class Currency {
    private int image;
    private String shortName;
    private String longName;

    public Currency(int image, String shortName, String longName){
        this.image = image;
        this.shortName = shortName;
        this.longName = longName;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
