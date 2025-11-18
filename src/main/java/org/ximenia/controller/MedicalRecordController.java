package org.ximenia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ximenia.model.MedicalRecord;
import org.ximenia.service.MedicalRecordService;

import java.util.List;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("medicalrecord")
    public List<MedicalRecord> allMedicalRecords(){
        return medicalRecordService.findAllMedicalRecords();
    }
}
