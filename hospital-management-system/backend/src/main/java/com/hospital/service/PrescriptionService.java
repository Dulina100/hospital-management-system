package com.hospital.service;

import com.hospital.dto.PrescriptionDTO;
import com.hospital.entity.Prescription;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.repository.PrescriptionRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Prescription createPrescription(Prescription prescription) {
        Patient patient = patientRepository.findById(prescription.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(prescription.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setStatus("ACTIVE");

        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId);
    }

    public List<Prescription> getPrescriptionsByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctor_Id(doctorId);
    }

    public List<Prescription> getActivePrescriptions(Long patientId) {
        return prescriptionRepository.findByPatient_IdAndStatus(patientId, "ACTIVE");
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setMedicationName(prescriptionDetails.getMedicationName());
        prescription.setDosage(prescriptionDetails.getDosage());
        prescription.setFrequency(prescriptionDetails.getFrequency());
        prescription.setDuration(prescriptionDetails.getDuration());
        prescription.setInstructions(prescriptionDetails.getInstructions());
        return prescriptionRepository.save(prescription);
    }

    public Prescription completePrescription(Long id) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setStatus("COMPLETED");
        return prescriptionRepository.save(prescription);
    }

    public Prescription cancelPrescription(Long id) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setStatus("CANCELLED");
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public PrescriptionDTO convertToDTO(Prescription prescription) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(prescription.getId());
        dto.setPatientId(prescription.getPatient().getId());
        dto.setDoctorId(prescription.getDoctor().getId());
        dto.setMedicationName(prescription.getMedicationName());
        dto.setDosage(prescription.getDosage());
        dto.setFrequency(prescription.getFrequency());
        dto.setDuration(prescription.getDuration());
        dto.setInstructions(prescription.getInstructions());
        dto.setPrescriptionDate(prescription.getPrescriptionDate());
        dto.setStatus(prescription.getStatus());
        dto.setCreatedAt(prescription.getCreatedAt());
        dto.setUpdatedAt(prescription.getUpdatedAt());
        return dto;
    }
}
