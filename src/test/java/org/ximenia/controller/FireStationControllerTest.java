package org.ximenia.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ximenia.model.Person;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.service.dto.ChildAlertDto;

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

    @Test
    void phoneNumberListTests() {
        assert (fireStationsController.phoneAlert(String.valueOf(4)).equals(phoneNumbers));
    }
}