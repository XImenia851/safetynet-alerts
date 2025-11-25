package org.ximenia.controller;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.web.bind.annotation.*;
import org.ximenia.model.FireStation;
import org.ximenia.service.FireStationService;
import org.ximenia.service.dto.FireStationDto;
import org.ximenia.service.dto.FireStationPersonDto;
import org.ximenia.service.dto.FloodDto;

import java.util.List;
import java.util.Map;

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

    @GetMapping("phoneAlert")
    public List<String> phoneAlert(@RequestParam("firestation") String stationNumber) {
        return fireStationService.getPhoneAlert(stationNumber);
    }

    @GetMapping("number")
    public FireStationDto getPersonsByStation(@RequestParam("stationNumber") String stationNumber) {
        return fireStationService.getPersonsByStation(stationNumber);
    }

    @RequestMapping("flood/stations")
    public FloodDto foyersListByFireStation(@RequestParam(name = "stations") int number){
        return this.fireStationService.getFoyersByStations();
    }
}