package org.ximenia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ximenia.model.FireStation;
import org.ximenia.service.FireStationService;
import java.util.List;

@RestController
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }


    @GetMapping("firestation")
    public List<FireStation> allFireStations() {
        return this.fireStationService.allFireStations();
    }
}
