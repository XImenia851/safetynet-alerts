package org.ximenia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/communityEmail")
    public List<String> findAllEmails(@RequestParam(name = "city")String email) {
        return personService.findAllEmails();
    }

//    Pour trouver le numero de téléphone, il faut l'adresse, qui est en lien avec l'une des firestations
    // faire une boucle for ? comparer les numéro de firestations avec l'adresse ?

}