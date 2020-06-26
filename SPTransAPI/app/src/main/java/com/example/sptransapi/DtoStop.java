package com.example.sptransapi;

public class DtoStop {
    public String getCodStop() {
        return codStop;
    }

    public void setCodStop(String codStop) {
        this.codStop = codStop;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getCordX() {
        return cordX;
    }

    public void setCordX(String cordX) {
        this.cordX = cordX;
    }

    public String getCordY() {
        return cordY;
    }

    public void setCordY(String cordY) {
        this.cordY = cordY;
    }

    private String codStop;
    private String stopName;
    private String addressName;
    private String cordX;
    private String cordY;
}
