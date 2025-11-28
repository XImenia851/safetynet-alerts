package org.ximenia.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ximenia.model.FireStation;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.service.dto.ChildAlertDto;
import org.ximenia.service.dto.FireDto;
import org.ximenia.service.dto.FireStationDto;
import org.ximenia.service.dto.FloodDto;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireStationControllerTest {

    private static List<String> phoneNumbers = new ArrayList<>();

    @Autowired
    private FireStationController fireStationsController;

    @Autowired
    private FireStationRepository fireStationRepository;

    @BeforeAll
    void setUp() {
        phoneNumbers = new ArrayList<>();
        phoneNumbers.add("841-874-6874");
        phoneNumbers.add("841-874-9845");
        phoneNumbers.add("841-874-8888");
        phoneNumbers.add("841-874-9888");

        List<Person> households = new ArrayList<>();
        households.add(new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com"));
        households.add(new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com"));

        ChildAlertDto childAlertDto = new ChildAlertDto("Zach", "Zemicks", "3", households);
    }
//--------------------------------------------phoneAlert---------------------------------------------------------
    @Test
    void phoneNumberListTests() {
        assert (fireStationsController.phoneAlert(String.valueOf(4)).equals(phoneNumbers));
    }
//------------------------------------------------number------------------------------------------------------------
    @Test
    void personListByFireStationTests() {
        FireStationDto result = fireStationsController.getPersonsByStation(String.valueOf(2));
        assert (result.getPeople().get(0).getFirstName().equals("Jonanathan"));
    }

    @Test
    void allFireStationsTest() {
        // WHEN - Appel du controller
        List<FireStation> result = fireStationsController.allFireStations();

        // THEN - Vérifications
        assert (!result.isEmpty());  // La liste n'est pas vide
        assert (result.get(0).getAddress() != null);  // Les adresses existent
    }
//-----------------------------------------------------------------------------------------------------------
    @Test
    void createFireStationTest() {
        // fire station à créer
        FireStation newFireStation = new FireStation();
        newFireStation.setAddress("123 Test Street");
        newFireStation.setStation("99");

        // Création
        FireStation result = fireStationsController.createFireStation(newFireStation);

        // Vérifications
        assert (result.getAddress().equals("123 Test Street"));
        assert (result.getStation().equals("99"));
    }

    @Test
    void updateFireStationTest() {
        // ce qu'ont met à jour.
        String address = "1509 Culver St";
        String oldStation = "3";
        String newStation = "5";

        FireStation updatedFireStation = new FireStation();
        updatedFireStation.setAddress(address);
        updatedFireStation.setStation(newStation);

        // LA Mise à jour
        FireStation result = fireStationsController.updateFireStation(address, oldStation, updatedFireStation);

        //Vérifications
        assert (result.getAddress().equals(address));
        assert (result.getStation().equals(newStation));
    }

    @Test
    void deleteFireStationTest() {
        // GIVEN supprime l'adresse donner
        String addressToDelete = "951 LoneTree Rd";

        // WHEN - Suppression de cette adresse
        fireStationsController.deletePerson(addressToDelete);

        // THEN - vérifie que l'adresse n'existe plus
        List<FireStation> allStations = fireStationsController.allFireStations();
        boolean exists = allStations.stream()
                .anyMatch(fs -> fs.getAddress().equals(addressToDelete));

        assert (!exists);  // verifie que l'adress existe pas
    }

    @Test
    void getFireStationByAddressTest() {
        // GIVEN - Adresse à tester
        String address = "644 Gershwin Cir";

        // WHEN - Appel du controller
        List<FireDto> result = fireStationsController.getFireStation(address);

        // THEN - Vérifications
        assert (!result.isEmpty());  // La liste n'est pas vide
        assert (result.get(0).getFirstName().equals("Peter"));  // Première personne est Peter
        assert (result.get(0).getLastName().equals("Duncan"));  // Son nom est Duncan
        assert (result.get(0).getStation().equals("1"));  // Station numéro 1
    }

    @Test
    void foyersListByFireStationTest() {
        // GIVEN - Numéro de station à tester
        int stationNumber = 2;

        // WHEN - Appel du controller
        FloodDto result = fireStationsController.foyersListByFireStation(stationNumber);

        // THEN - Vérifications
        assert (result != null);  // Le résultat n'est pas null
        assert (!result.getFoyers().isEmpty());  // Il y a des foyers dans la liste
        // Vérification qu'un foyer contient bien des personnes
        assert (result.getFoyers().get(0).getAge() > 0);
    }
}