package org.ximenia.controller;

import org.springframework.web.bind.annotation.*;
import org.ximenia.model.Person;
import org.ximenia.service.FireStationService;
import org.ximenia.service.MedicalRecordService;
import org.ximenia.service.PersonService;
import org.ximenia.service.dto.ChildAlertDto;
import org.ximenia.service.dto.PersonInfoDto;

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

    @PostMapping("/person")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @GetMapping("/communityEmail")
    public List<String> findAllEmails(@RequestParam(name = "city")String email) {
        return personService.findAllEmails();
    }

    @RequestMapping(value = "personInfo", method = RequestMethod.GET)
    public List<PersonInfoDto> listOfPersonsWithMedicalRecords(@RequestParam String firstName, @RequestParam String lastName){
        return this.personService.findAllpersonsWithMedicalRecords(firstName, lastName);
    }


    @RequestMapping(value = "childAlert", method = RequestMethod.GET)
    public List<ChildAlertDto> childsUnder18ByAddress(@RequestParam (name = "address") String address){
        return this.personService.findAllchildsUnder18ByAddress(address);
    }
}