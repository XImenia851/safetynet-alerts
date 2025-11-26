package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.MedicalRecord;
import org.ximenia.repository.DataHandler;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final DataHandler dataHandler;

    public MedicalRecordService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordRepository medicalRecordRepository, DataHandler dataHandler) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.dataHandler = dataHandler;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecordRepository.findAllMedicalRecords();
    }
//------------------------------------CRUD -------------------------------

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = dataHandler.getDataContainer().getMedicalrecords();

        // Vérifier si le dossier médical existe déjà
        boolean exists = medicalRecords.stream()
                .anyMatch(mr -> mr.getFirstName().equals(medicalRecord.getFirstName())
                        && mr.getLastName().equals(medicalRecord.getLastName()));

        if (exists) {
            throw new IllegalArgumentException("Medical record already exists");
        }

        medicalRecords.add(medicalRecord);
        dataHandler.save();
        return medicalRecord;
    }

    //---------------------------------------------------------------
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = dataHandler.getDataContainer().getMedicalrecords();

        for (MedicalRecord mr : medicalRecords) {
            if (mr.getFirstName().equals(medicalRecord.getFirstName())
                    && mr.getLastName().equals(medicalRecord.getLastName())) {
                // Mettre à jour tous les champs sauf firstName et lastName
                mr.setBirthdate(medicalRecord.getBirthdate());
                mr.setMedications(medicalRecord.getMedications());
                mr.setAllergies(medicalRecord.getAllergies());
                dataHandler.save();
                return mr;
            }
        }

        throw new IllegalArgumentException("Medical record not found");
    }

    //------------------------------------------------------
    public void deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = dataHandler.getDataContainer().getMedicalrecords();

        boolean removed = medicalRecords.removeIf(mr ->
                mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName));

        if (!removed) {
            throw new IllegalArgumentException("Medical record not found");
        }

        dataHandler.save();
    }
}
