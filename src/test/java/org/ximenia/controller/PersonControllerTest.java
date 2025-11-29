package org.ximenia.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ximenia.model.Person;
import org.ximenia.repository.PersonRepository;
import org.ximenia.service.dto.ChildAlertDto;
import org.ximenia.service.dto.PersonInfoDto;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @BeforeAll
    void setUp() {
    }

    //-------------------------------------- GET /persons --------------------------------------
    @Test
    void allPersonsTest() {
        // WHEN - Appel du controller
        List<Person> result = personController.allPersons();

        // THEN - Vérifications
        assert (!result.isEmpty());
        assert (result.get(0).getFirstName() != null);
    }

    //-------------------------------------- POST /person --------------------------------------
    @Test
    void createPersonTest() {
        // Nouvelle personne à créer
        Person newPerson = new Person();
        newPerson.setFirstName("Test");
        newPerson.setLastName("User");
        newPerson.setAdress("123 Test St");
        newPerson.setCity("Culver");
        newPerson.setZip("97451");
        newPerson.setPhone("123-456-7890");
        newPerson.setEmail("test@email.com");

        // Création
        Person result = personController.createPerson(newPerson);

        // Vérifications
        assert (result.getFirstName().equals("Test"));
        assert (result.getLastName().equals("User"));
        assert (result.getEmail().equals("test@email.com"));
    }

    //-------------------------------------- PUT /person --------------------------------------
    @Test
    void updatePersonTest() {
        // Personne à mettre à jour
        String firstName = "John";
        String lastName = "Boyd";

        Person updatedPerson = new Person();
        updatedPerson.setFirstName(firstName);
        updatedPerson.setLastName(lastName);

        // Mise à jour
        Person result = personController.updatePerson(firstName, lastName, updatedPerson);

        // Vérifications
        assert (result.getFirstName().equals(firstName));
        assert (result.getLastName().equals(lastName));
    }

    //-------------------------------------- DELETE /perso --------------------------------------
    @Test
    void deletePersonTest() {
        // Personne à supprimer
        String firstNameToDelete = "Foster";
        String lastNameToDelete = "Shepard";

        // Suppression
        personController.deletePerson(firstNameToDelete, lastNameToDelete);

        // Vérification la personne n'existe plus
        List<Person> allPersons = personController.allPersons();
        boolean exists = allPersons.stream()
                .anyMatch(p -> p.getFirstName().equals(firstNameToDelete)
                        && p.getLastName().equals(lastNameToDelete));

        assert (!exists);
    }

    //-------------------------------------- GET /communityEmail --------------------------------------
    @Test
    void findAllEmailsTest() {
        // Ville à tester
        String city = "Culver";

        // Appel du controller
        List<String> result = personController.findAllEmails(city);

        // Vérifications
        assert (!result.isEmpty());
        assert (result.contains("jaboyd@email.com"));
        assert (result.size() > 0);
    }

    //-------------------------------------- GET /personInfo --------------------------------------
    @Test
    void listOfPersonsWithMedicalRecordsTest() {
        // Nom et prénom d'une personne avec dossier médical
        String firstName = "Tenley";
        String lastName = "Boyd";

        // Appel du controller
        List<PersonInfoDto> result = personController.listOfPersonsWithMedicalRecords(firstName, lastName);

        // THEN - Vérifications
        assert (!result.isEmpty());
        assert (result.get(0).getFirstName().equals(firstName));
        assert (result.get(0).getLastName().equals(lastName));
    }

    //-------------------------------------- GET /childAlert --------------------------------------
    @Test
    void childsUnder18ByAddressTest() {
        //Adresse avec des enfants
        String address = "947 E. Rose Dr";

        // Appel du controller
        List<ChildAlertDto> result = personController.childsUnder18ByAddress(address);

        // Vérifications
        assert (result != null);
        assert (!result.isEmpty());
        assert (result.get(0).getFirstName() != null);
    }
}