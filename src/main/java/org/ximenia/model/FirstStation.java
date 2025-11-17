package org.ximenia.model;

public class FirstStation {

    private String adress;
    private String station;

    public FirstStation(){
    }

    public FirstStation(String adress, String station) {
        this.adress = adress;
        this.station = station;
    }

    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getStation() {
        return station;
    }
    public void setStation(String station) {
        this.station = station;
    }
}
