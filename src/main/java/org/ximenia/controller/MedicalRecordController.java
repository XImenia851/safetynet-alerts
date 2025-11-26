package org.ximenia.controller;

import org.springframework.web.bind.annotation.*;
import org.ximenia.model.MedicalRecord;
import org.ximenia.service.MedicalRecordService;

import java.util.List;

@RestController
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("medicalrecords")
    public List<MedicalRecord> allMedicalRecords(){
        return medicalRecordService.findAllMedicalRecords();
    }

    @PostMapping("/medicalrecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
