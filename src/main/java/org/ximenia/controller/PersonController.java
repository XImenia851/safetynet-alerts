package org.ximenia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ximenia.model.Person;
import org.ximenia.service.FireStationService;
import org.ximenia.service.MedicalRecordService;
import org.ximenia.service.PersonService;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.personService = personService;
    }

    @GetMapping("persons")
    public List<Person> allPersons(){
        return personService.findAllPersons();
    }
}
