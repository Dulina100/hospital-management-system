package com.hospital.controller;

import com.hospital.dto.PrescriptionDTO;
import com.hospital.entity.Prescription;
import com.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription) {
        Prescription createdPrescription = prescriptionService.createPrescription(prescription);
        return new ResponseEntity<>(prescriptionService.convertToDTO(createdPrescription), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'PHARMACIST')")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return new ResponseEntity<>(prescriptionService.convertToDTO(prescription), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        List<PrescriptionDTO> prescriptions = prescriptionService.getAllPrescriptions().stream()
                .map(prescriptionService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'PHARMACIST')")
    public ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        List<PrescriptionDTO> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId).stream()
                .map(prescriptionService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByDoctorId(@PathVariable Long doctorId) {
        List<PrescriptionDTO> prescriptions = prescriptionService.getPrescriptionsByDoctorId(doctorId).stream()
                .map(prescriptionService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT', 'PHARMACIST')")
    public ResponseEntity<List<PrescriptionDTO>> getActivePrescriptions(@PathVariable Long patientId) {
        List<PrescriptionDTO> prescriptions = prescriptionService.getActivePrescriptions(patientId).stream()
                .map(prescriptionService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<PrescriptionDTO> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescriptionDetails) {
        Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDetails);
        return new ResponseEntity<>(prescriptionService.convertToDTO(updatedPrescription), HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<PrescriptionDTO> completePrescription(@PathVariable Long id) {
        Prescription completedPrescription = prescriptionService.completePrescription(id);
        return new ResponseEntity<>(prescriptionService.convertToDTO(completedPrescription), HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<PrescriptionDTO> cancelPrescription(@PathVariable Long id) {
        Prescription cancelledPrescription = prescriptionService.cancelPrescription(id);
        return new ResponseEntity<>(prescriptionService.convertToDTO(cancelledPrescription), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>("Prescription deleted successfully", HttpStatus.OK);
    }
}
