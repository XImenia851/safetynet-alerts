package org.ximenia.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ximenia.model.FireStation;
import org.ximenia.model.MedicalRecord;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;
import org.ximenia.service.dto.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonService personService;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, PersonService personService) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personService = personService;
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

    public FireStationDto getPersonsByStation(String stationNumber) {
        // Trouver toutes les adresses couvertes par cette station
        List<String> coverAddresses = fireStationRepository.findAllFireStations().stream()
                .filter(fs -> fs.getStation().equalsIgnoreCase(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        List<Person> personsCovered = personRepository.findAllPersons().stream()
                .filter(p -> coverAddresses.contains(p.getAddress()))
                .toList();

        //transforme direct en objet que je peux utiliser plus tard
        AtomicInteger adultsCount = new AtomicInteger();
        AtomicInteger childsCount = new AtomicInteger();

        List<FireStationPersonDto> personDtoList = personsCovered.stream()
                .map(p -> {
                    MedicalRecord record = medicalRecordRepository.findMedicalWithFirstNameAndLastName(
                            p.getFirstName(),
                            p.getLastName()
                    );

                    int age = 0;
                    if (record != null) {
                        age = personService.computeAge(record.getBirthdate());
                    }
                    //permet d'incrémenter SET GET
                    if (age > 18) {
                        adultsCount.incrementAndGet();
                    } else {
                        childsCount.incrementAndGet();
                    }

                    return new FireStationPersonDto(
                            p.getFirstName(),
                            p.getLastName(),
                            p.getPhone(),
                            p.getAddress()
                    );
                })
                .toList();

        return new FireStationDto(
                String.valueOf(childsCount.get()),
                String.valueOf(adultsCount.get()),
                personDtoList
        );
    }








    public FloodDto getFoyersByStations() {

        List<String> coveredAddresses = new ArrayList<>();
        for (FireStation fs : fireStationRepository.findAllFireStations()) {
            coveredAddresses.add(fs.getAddress());
        }

        List<Person> personsCovered = new ArrayList<>();
        for (Person p : personService.findAllPersons()) {
            if (coveredAddresses.contains(p.getAddress())) {
                personsCovered.add(p);
            }
        }

        List<FloodPersonDto> floodPersonDTO = new ArrayList<>();

        for (Person p : personsCovered) {

            MedicalRecord record = medicalRecordRepository.findMedicalWithFirstNameAndLastName(
                    p.getFirstName(),
                    p.getLastName()
            );

            int age = (record != null) ? personService.computeAge(record.getBirthdate()) : 0;

            FloodPersonDto dto = new FloodPersonDto();
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setPhone(p.getPhone());
            dto.setAge(age);

            if (record != null) {
                dto.setAllergies(record.getAllergies().toArray(new String[0]));
                dto.setMedications(record.getMedications().toArray(new String[0]));
            }

            floodPersonDTO.add(dto);
        }

        return new FloodDto(coveredAddresses.get(0), floodPersonDTO);
    }
}
