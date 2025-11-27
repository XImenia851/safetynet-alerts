package org.ximenia.controller;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.web.bind.annotation.*;
import org.ximenia.model.FireStation;
import org.ximenia.service.FireStationService;
import org.ximenia.service.dto.FireDto;
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

    @PostMapping("/firestation")
    public FireStation createFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.createFireStation(fireStation);
    }

    @PutMapping("/firestations")
    public FireStation updateFireStation(@RequestParam String address, @RequestParam String station,@RequestBody FireStation fireStation) {
        return fireStationService.updateFireStation(address, station, fireStation);
    }


    @DeleteMapping("/firestation")
    public void deletePerson(@RequestParam String address) {
        fireStationService.deleteFireStation(address);
    }

    //-------------------------------------------------------------------

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

    @GetMapping("fire")
    public List<FireDto> getFireStation(@RequestParam String address) {
        return fireStationService.getFireDtoByAddress(address);
    }
}