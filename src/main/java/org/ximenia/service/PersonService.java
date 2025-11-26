package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.MedicalRecord;
import org.ximenia.model.Person;
import org.ximenia.repository.DataHandler;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;
import org.ximenia.service.dto.ChildAlertDto;
import org.ximenia.service.dto.PersonInfoDto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final DataHandler dataHandler;
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private FireStationRepository fireStationRepository;

    public PersonService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, DataHandler dataHandler) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {
        return personRepository.findAllPersons();
    }


    public List<String> findAllEmails() {
        List<String> emails = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();
        for (Person person : persons) {
            emails.add(person.getEmail());
        }
        return emails;
    }
//---------------------------------------------------------------------------------

    public int computeAge(String birthdateOfPerson) {
        LocalDate dob = LocalDate.parse(birthdateOfPerson, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate curDate = LocalDate.now();
        return Period.between(dob, curDate).getYears();
    }


    public int calculateAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(birthdate, formatter);

        return Period.between(date, LocalDate.now()).getYears();
    }


//-----------------------------------------------------------------------------------------
    public List<ChildAlertDto> findAllchildsUnder18ByAddress(String address) {
        List<ChildAlertDto> result = new ArrayList<>();
        //recup la liste des personnes habitants à cette adresse
        List<Person> persons = personRepository.findAllPersonsByAddress(address);
//recup la liste des medic record de -18
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllMedicalRecords();

        //3e liste et rentrer les noms correspondant
        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);
            if (medicalRecord != null) {
                ChildAlertDto dto = new ChildAlertDto();
                dto.setFirstName(medicalRecord.getFirstName());
                dto.setLastName(medicalRecord.getLastName());
                dto.setAge(String.valueOf(computeAge(medicalRecord.getBirthdate())));
                dto.setHouseholds(persons.stream().filter(p -> !p.getFirstName().equals(person.getFirstName())).collect(Collectors.toList()));
                result.add(dto);
            }
        }
        return result;
    }

    //------------------------------------------------------------------------------

    private MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                return  medicalRecord;
            }
        }
        return null;
    }

    public List <MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAllMedicalRecords();
    }

    public List<PersonInfoDto> findAllpersonsWithMedicalRecords(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();

        // Trouver la personne
        Person person = personRepository.findpersonByfirstNameAndLastName(firstName, lastName);

        // Trouver le dossier médical
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalWithFirstNameAndLastName(firstName, lastName);

        // Si la personne ou le dossier médical n'existe pas, retourner liste vide
        if (person == null || medicalRecord == null) {
            return result;
        }

        // Créer le DTO
        PersonInfoDto dto = new PersonInfoDto();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setEmail(person.getEmail());
        dto.setPhone(person.getPhone());
        dto.setAge(computeAge(medicalRecord.getBirthdate()));
        dto.setMedications(medicalRecord.getMedications().toArray(new String[0]));
        dto.setAllergies(medicalRecord.getAllergies().toArray(new String[0]));

        result.add(dto);
        return result;
    }



    //----------------------------CRUD -----------------

    public Person createPerson(Person person) {
        List<Person> persons = dataHandler.getDataContainer().getPersons();

        boolean exists = persons.stream()
                .anyMatch(p -> p.getFirstName().equals(person.getFirstName())
                        && p.getLastName().equals(person.getLastName()));

        persons.add(person);
        dataHandler.save();
        return person;
    }
//-------------------------------------------------------------------------
    public Person updatePerson(Person person) {
        List<Person> persons = dataHandler.getDataContainer().getPersons();

        for (Person p : persons) {
            if (p.getFirstName().equals(person.getFirstName())
                    && p.getLastName().equals(person.getLastName())) {
                // Mettre à jour tous les champs sauf firstName et lastName
                p.setAdress(person.getAddress());
                p.setCity(person.getCity());
                p.setZip(person.getZip());
                p.setPhone(person.getPhone());
                p.setEmail(person.getEmail());
                dataHandler.save();
                return p;
            }
        }

        throw new IllegalArgumentException("Person not found");
    }

    //-----------------------------------------------------------------------

    public void deletePerson(String firstName, String lastName) {
        List<Person> persons = dataHandler.getDataContainer().getPersons();

        boolean removed = persons.removeIf(p ->
                p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));

        if (!removed) {
            throw new IllegalArgumentException("Person not found");
        }

        dataHandler.save();
    }
}














// correction JC V1 :
//Public List<String> findAllEmailByCity(String city) {
//List<String> emails = new ArrayList<>();
// "" <Person> persons = personRepo.findAllPersons();
//for (Person person : persons){
//if(person.getCity(). equals(city)){
//emails.add(person.getEmail());
// }} return emails;
//Version avec un stream :
//public List<String> findAllEmailByCity(String city) {
//return this.personRepo.findAllPersons().stream().filter(p -> p.getCity().equaols(city)).map(p -> p.getMail())
