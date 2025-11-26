package org.ximenia.service.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class FloodDto {
    private String address;
    private List<FloodPersonDto> foyers;

    public FloodDto(String address, List<FloodPersonDto> foyers) {
        this.address = address;
        this.foyers = foyers;
    }
}