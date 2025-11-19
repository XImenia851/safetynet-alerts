package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.FireStation;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<FireStation> allFireStations() {
        return fireStationRepository.findAllFireStations();
    }

    public List<String> getPhoneAlert(String stationNumber) {
        List<String> address = fireStationRepository.findAllFireStations().stream()
                .filter(station -> station.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        return personRepository.findAllPersons().stream()
                .filter(person -> address.contains(person.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }
}
