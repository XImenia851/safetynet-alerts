package org.ximenia.repository;

import org.springframework.stereotype.Component;
import org.ximenia.model.MedicalRecord;

import java.util.List;

@Component
public class MedicalRecordRepository {

    private final DataHandler dataHandler;
    public MedicalRecordRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHandler.getDataContainer().getMedicalrecords();
    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getDataContainer().getMedicalrecords().stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(() -> new MedicalRecord());
    }
}


