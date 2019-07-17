package com.example.currency;

public class Currency {
    private int image;
    private String shortName;
    private String longName;
    private float currency;

    public Currency(int image, String shortName, String longName,float currency){
        this.image = image;
        this.shortName = shortName;
        this.longName = longName;
        this.currency = currency;
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

    public float getCurrency() {
        return currency;
    }

    public void setCurrency(float currency) {
        this.currency = currency;
    }
}
