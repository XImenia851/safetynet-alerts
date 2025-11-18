package org.ximenia.service;

import org.springframework.stereotype.Service;
import org.ximenia.model.MedicalRecord;
import org.ximenia.repository.FireStationRepository;
import org.ximenia.repository.MedicalRecordRepository;
import org.ximenia.repository.PersonRepository;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;

    public MedicalRecordService(FireStationRepository fireStationRepository, PersonRepository personRepository,  MedicalRecordRepository medicalRecordRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecordRepository.findAllMedicalRecords();
    }

}
