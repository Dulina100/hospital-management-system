package com.hospital.service;

import com.hospital.management.entity.Admission;
import com.hospital.management.entity.Bed;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.AdmissionRepository;
import com.hospital.management.repository.BedRepository;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BedRepository bedRepository;

    public Admission createAdmission(Admission admission) {
        Patient patient = patientRepository.findById(admission.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Bed bed = bedRepository.findById(admission.getBed().getId())
                .orElseThrow(() -> new RuntimeException("Bed not found"));

        if (!bed.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Bed is not available");
        }

        admission.setPatient(patient);
        admission.setBed(bed);
        admission.setStatus("ADMITTED");
        admission.setAdmissionDate(LocalDateTime.now());

        // Update bed status
        bed.setStatus("OCCUPIED");
        bed.setPatient(patient);
        bedRepository.save(bed);

        // Update patient admission status
        patient.setIsAdmitted(true);
        patientRepository.save(patient);

        return admissionRepository.save(admission);
    }

    public Admission getAdmissionById(Long id) {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + id));
    }

    public List<Admission> getAdmissionsByPatientId(Long patientId) {
        return admissionRepository.findByPatient_Id(patientId);
    }

    public List<Admission> getAdmissionsByStatus(String status) {
        return admissionRepository.findByStatus(status);
    }

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    public Admission dischargePatient(Long id) {
        Admission admission = getAdmissionById(id);
        admission.setStatus("DISCHARGED");
        admission.setDischargeDate(LocalDateTime.now());

        // Update bed status
        Bed bed = admission.getBed();
        bed.setStatus("AVAILABLE");
        bed.setPatient(null);
        bedRepository.save(bed);

        // Update patient admission status
        Patient patient = admission.getPatient();
        patient.setIsAdmitted(false);
        patientRepository.save(patient);

        return admissionRepository.save(admission);
    }

    public void deleteAdmission(Long id) {
        admissionRepository.deleteById(id);
    }
}
