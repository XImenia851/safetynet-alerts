package org.ximenia.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ximenia.model.MedicalRecord;
import org.ximenia.repository.DataHandler;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.service.dto.FloodDto;
import org.ximenia.service.dto.PersonInfoDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalRecordControllerTest {

private static List<String>  stationNumbers = new ArrayList<>();

@Autowired
    private MedicalRecordController medicalRecordController;

@Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DataHandler dataHandler;

    @BeforeAll
void setup(){
    stationNumbers = new ArrayList<>();
    stationNumbers.add("1");
    stationNumbers.add("2");
    stationNumbers.add("3");
    stationNumbers.add("4");

    List<MedicalRecord> medicalRecords = new ArrayList<>();
    medicalRecords.add(new MedicalRecord());
    medicalRecords.add(new MedicalRecord());

    PersonInfoDto  personInfoDto = new PersonInfoDto();
}

//-----------------------------------------------------

@Test
    void allMedicalRecords(){

    List<MedicalRecord> medicalRecords = medicalRecordController.allMedicalRecords();
    assert (!medicalRecords.isEmpty());
    assert (medicalRecords != null);
}
//--------------------------------------------------------
@Test
void createMedicalRecordTest() {
    // enregistrement médical
    MedicalRecord newRecord = new MedicalRecord();
    newRecord.setFirstName("Alice");
    newRecord.setLastName("Wonderland");


    // on appelle le controller
    MedicalRecord result = medicalRecordController.createMedicalRecord(newRecord);

    //vérifications
    assert(result != null);  // le résultat n'est pas null
    assert(result.getFirstName().equals("Alice"));
    assert(result.getLastName().equals("Wonderland"));
}
//-----------------------------------------------------
@Test
void updateMedicalRecordTest() {
    // GIVEN : un record existant
    String firstName = "Alice";
    String lastName = "Wonderland";

    MedicalRecord initialRecord = new MedicalRecord();
    initialRecord.setFirstName(firstName);
    initialRecord.setLastName(lastName);
    dataHandler.getDataContainer().getMedicalrecords().add(initialRecord); // ajouter dans la data

    // Nouveau record pour update (ici seulement firstName et lastName)
    MedicalRecord updateRecord = new MedicalRecord();
    updateRecord.setFirstName(firstName);
    updateRecord.setLastName(lastName);

    // WHEN : appel du service via le controller
    MedicalRecord result = medicalRecordController.updateMedicalRecord(firstName, lastName, updateRecord);

    // THEN : vérifications
    assert (result != null);
    assert (result.getFirstName().equals(firstName));
    assert (result.getLastName().equals(lastName));
}
//------------------------------------------------------------------------------
@Test
void deleteMedicalRecordTest() {
    // GIVEN – on supprime un medical record qui existe vraiment
    String firstNameToDelete = "John";
    String lastNameToDelete = "Boyd";

    // Vérification préalable
    List<MedicalRecord> before = medicalRecordController.allMedicalRecords();
    boolean existsBefore = before.stream()
            .anyMatch(mr -> mr.getFirstName().equals(firstNameToDelete)
                    && mr.getLastName().equals(lastNameToDelete));

    assert(existsBefore);

    // WHEN – suppression du medical record
    medicalRecordController.deleteMedicalRecord(firstNameToDelete, lastNameToDelete);

    // THEN – vérifie qu’il n’existe plus
    List<MedicalRecord> after = medicalRecordController.allMedicalRecords();
    boolean existsAfter = after.stream()
            .anyMatch(mr -> mr.getFirstName().equals(firstNameToDelete)
                    && mr.getLastName().equals(lastNameToDelete));

    assert(!existsAfter);
}

}