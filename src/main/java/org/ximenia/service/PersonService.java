package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;

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
    public List<Person> findAllPersons()
    {
        return personRepository.findAllPersons();
    }


    public List<String> findAllEmails()
    {
       List<String> emails = new ArrayList<>();
       List<Person> persons = personRepository.findAllPersons();
        for (Person person:persons) {
            emails.add(person.getEmail());
        }
        return emails;
    }
}
