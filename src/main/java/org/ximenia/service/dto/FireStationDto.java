package org.ximenia.service.dto;

import java.util.List;

public class FireStationDto {
    private String children;
    private String adults;
    private List<FireStationPersonDto> people;

    public FireStationDto(String children, String adults, List<FireStationPersonDto> people) {
        this.children = children;
        this.adults = adults;
        this.people = people;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public List<FireStationPersonDto> getPeople() {
        return people;
    }

    public void setPeople(List<FireStationPersonDto> people) {
        this.people = people;
    }
}