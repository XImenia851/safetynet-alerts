package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.FireStation;
import org.ximenia.model.MedicalRecord;
import org.ximenia.model.Person;
import org.ximenia.repository.DataHandler;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;
import org.ximenia.service.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonService personService;
    private final DataHandler dataHandler;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, PersonService personService, DataHandler dataHandler) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personService = personService;
        this.dataHandler = dataHandler;
    }

    public List<FireStation> allFireStations() {
        return fireStationRepository.findAllFireStations();
    }

    //--------------------------------------------------------------------------------------------------

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

    //____________________________________________________________________________________________________________

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

    //-------------------------------------------------------------------
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

    //----------------------------CRUD-----------------------------------------------

    public FireStation createFireStation(FireStation fireStation) {
        List<FireStation> fireStations = dataHandler.getDataContainer().getFirestations();

        // Vérifier si le mapping existe déjà
        boolean exists = fireStations.stream()
                .anyMatch(fs -> fs.getAddress().equals(fireStation.getAddress()));

        if (exists) {
            throw new IllegalArgumentException("FireStation mapping already exists for this address");
        }

        fireStations.add(fireStation);
        dataHandler.save();
        return fireStation;
    }

    //-----------------------------------------------------------------
    public FireStation updateFireStation(String address, String station, FireStation fireStation) {
        List<FireStation> fireStations = dataHandler.getDataContainer().getFirestations();

        for (FireStation fs : fireStations) {
            if (fs.getAddress().equals(address)
                    && fs.getStation().equals(station)) {
                fs.setStation(fireStation.getStation());
                fs.setAddress(fireStation.getAddress());
                return fs;
            }
        }

        throw new IllegalArgumentException("FireStation not found");
    }

    //----------------------------------------------------------------------

    public void deleteFireStation(String address) {
        List<FireStation> fireStations = dataHandler.getDataContainer().getFirestations();

        boolean removed = fireStations.removeIf(fs -> fs.getAddress().equals(address));

        if (!removed) {
            throw new IllegalArgumentException("station not found");
        }

        dataHandler.save();
    }

    //-------------------------------------------------------------------------------------------------------------

    public List<FireDto> getFireDtoByAddress(String address) {
//Les trois liste, on à besoin de toutes les donnes du JSON
        List<FireStation> fireStations = dataHandler.getDataContainer().getFirestations();
        List<MedicalRecord> medicalRecords = dataHandler.getDataContainer().getMedicalrecords();
        List<Person> persons = dataHandler.getDataContainer().getPersons();

        // Liste qui contiendra un FireDto par personne habitant à l'adresse
        List<FireDto> fireDtos = new ArrayList<>();

        // Parcours de toutes les fire stations pour trouver celle de l'adresse
        for (FireStation fs : fireStations) {

            // Si la fire station correspond à l'adresse recherchée
            if (fs.getAddress().equals(address)) {
                String stationNumber = fs.getStation();
                //sauvegarde ce numéro dans une variable temporaire
                //fs.getStation() = appel de méthode, sauvegarde l'info avant de sortir de la boucle,
                // évite des appels multiples à la même méthode

                // Parcours de toutes les personnes pour trouver celles à cette adresse
                for (Person p : persons) {
                    //cette personne habite bien à l'adresse demandée ?
                    if (p.getAddress().equals(address)) {

                        // Remplissage du DTO avec les informations de cette personne
                        FireDto dto = new FireDto();

                        dto.setStation(stationNumber);
                        dto.setPhoneNumber(p.getPhone());
                        dto.setFirstName(p.getFirstName());
                        dto.setLastName(p.getLastName());

                        //Le principe : 1 personne trouvée = 1 dto créé et rempli

                        // Parcours des dossiers médicaux pour trouver celui de cette personne
                        for (MedicalRecord mr : medicalRecords) {

                            // Si le dossier médical correspond à la personne
                            //On compare le prénom ET le nom pour être sûr que c'est la bonne personne
                            if (p.getFirstName().equals(mr.getFirstName()) &&
                                    p.getLastName().equals(mr.getLastName())) {
                                dto.setAge(personService.computeAge(mr.getBirthdate()));
                                dto.setAllergies(mr.getAllergies());
                                dto.setMedications(mr.getMedications());
                            }
                        }

                        fireDtos.add(dto);
                    }
                }
            }
        }
        // Retour de la liste contenant tous les DTOs
        return fireDtos;
    }
}
