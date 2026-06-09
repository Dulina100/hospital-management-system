package com.hospital.service;

import com.hospital.dto.MedicalRecordDTO;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        Patient patient = patientRepository.findById(record.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        record.setPatient(patient);

        if (record.getDoctor() != null) {
            Doctor doctor = doctorRepository.findById(record.getDoctor().getId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            record.setDoctor(doctor);
        }

        return medicalRecordRepository.save(record);
    }

    public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + id));
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatient_IdOrderByRecordDateDesc(patientId);
    }

    public List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findByDoctor_Id(doctorId);
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord recordDetails) {
        MedicalRecord record = getMedicalRecordById(id);
        record.setDiagnosis(recordDetails.getDiagnosis());
        record.setTreatmentPlan(recordDetails.getTreatmentPlan());
        record.setPrescription(recordDetails.getPrescription());
        record.setNotes(recordDetails.getNotes());
        record.setVitalSigns(recordDetails.getVitalSigns());
        return medicalRecordRepository.save(record);
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public MedicalRecordDTO convertToDTO(MedicalRecord record) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setId(record.getId());
        dto.setPatientId(record.getPatient().getId());
        dto.setDoctorId(record.getDoctor() != null ? record.getDoctor().getId() : null);
        dto.setRecordDate(record.getRecordDate());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setTreatmentPlan(record.getTreatmentPlan());
        dto.setPrescription(record.getPrescription());
        dto.setNotes(record.getNotes());
        dto.setVitalSigns(record.getVitalSigns());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setUpdatedAt(record.getUpdatedAt());
        return dto;
    }
}
