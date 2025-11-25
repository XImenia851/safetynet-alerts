package org.ximenia.service.dto;
import java.util.List;

public class FloodDto {
    private String address;
    private List<FloodPersonDto> foyers;

    public FloodDto(List<FloodPersonDto> foyers) {
        this.foyers = foyers;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FloodPersonDto> getFoyers() {
        return foyers;
    }

    public void setFoyers(List<FloodPersonDto> foyers) {
        this.foyers = foyers;
    }
}