package com.hospital.controller;

import com.hospital.management.dto.MedicalRecordDTO;
import com.hospital.management.entity.MedicalRecord;
import com.hospital.management.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecord record) {
        MedicalRecord createdRecord = medicalRecordService.createMedicalRecord(record);
        return new ResponseEntity<>(medicalRecordService.convertToDTO(createdRecord), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'NURSE')")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecord record = medicalRecordService.getMedicalRecordById(id);
        return new ResponseEntity<>(medicalRecordService.convertToDTO(record), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        List<MedicalRecordDTO> records = medicalRecordService.getAllMedicalRecords().stream()
                .map(medicalRecordService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'NURSE')")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByPatientId(patientId).stream()
                .map(medicalRecordService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByDoctorId(doctorId).stream()
                .map(medicalRecordService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord recordDetails) {
        MedicalRecord updatedRecord = medicalRecordService.updateMedicalRecord(id, recordDetails);
        return new ResponseEntity<>(medicalRecordService.convertToDTO(updatedRecord), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return new ResponseEntity<>("Medical record deleted successfully", HttpStatus.OK);
    }
}
