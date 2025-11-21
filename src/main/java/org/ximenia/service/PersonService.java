package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.MedicalRecord;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;
import org.ximenia.service.dto.PersonInfoDto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private FireStationRepository fireStationRepository;

    public PersonService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
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

    public List<PersonInfoDto> findAllpersonsWithMedicalRecords(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();
        PersonInfoDto dto = new PersonInfoDto();

        Person person = personRepository.findpersonByfirstNameAndLastName(firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalWithFirstNameAndLastName(firstName, lastName);

        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setAge(String.valueOf(computeAge(medicalRecord.getBirthdate())));
        dto.setEmail(person.getEmail());
        dto.setAllergies(medicalRecord.getAllergies().toArray(new String[0]));
        dto.setMedications(medicalRecord.getMedications().toArray(new String[0]));

        result.add(dto);
        return result;
    }

    private int computeAge(String birthdateOfPerson) {
        LocalDate dob = LocalDate.parse(birthdateOfPerson, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate curDate = LocalDate.now();
        return Period.between(dob, curDate).getYears();
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
