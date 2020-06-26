package com.example.sptransapi;

public class DtoBus {
    public String getCodLine() {
        return codLine;
    }

    public void setCodLine(String codLine) {
        this.codLine = codLine;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getStopStart() {
        return stopStart;
    }

    public void setStopStart(String stopStart) {
        this.stopStart = stopStart;
    }

    public String getStopEnd() {
        return stopEnd;
    }

    public void setStopEnd(String stopEnd) {
        this.stopEnd = stopEnd;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    private String codLine;
    private String number;
    private String base;
    private String stopStart;
    private String stopEnd;
    private String way;
}