package com.hospital.service;

import com.hospital.management.entity.LabTest;
import com.hospital.management.entity.LabTestResult;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.LabTestRepository;
import com.hospital.management.repository.LabTestResultRepository;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LabTestService {

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private LabTestResultRepository labTestResultRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Lab Test Operations
    public LabTest createLabTest(LabTest labTest) {
        return labTestRepository.save(labTest);
    }

    public LabTest getLabTestById(Long id) {
        return labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab test not found with id: " + id));
    }

    public List<LabTest> getAllLabTests() {
        return labTestRepository.findAll();
    }

    public List<LabTest> getActiveLabTests() {
        return labTestRepository.findByIsActiveTrue();
    }

    public List<LabTest> getLabTestsByCategory(String category) {
        return labTestRepository.findByCategory(category);
    }

    public LabTest updateLabTest(Long id, LabTest labTestDetails) {
        LabTest labTest = getLabTestById(id);
        labTest.setTestName(labTestDetails.getTestName());
        labTest.setCategory(labTestDetails.getCategory());
        labTest.setCost(labTestDetails.getCost());
        labTest.setDescription(labTestDetails.getDescription());
        labTest.setIsActive(labTestDetails.getIsActive());
        return labTestRepository.save(labTest);
    }

    public void deleteLabTest(Long id) {
        labTestRepository.deleteById(id);
    }

    // Lab Test Result Operations
    public LabTestResult createLabTestResult(LabTestResult result) {
        Patient patient = patientRepository.findById(result.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        LabTest labTest = getLabTestById(result.getLabTest().getId());

        result.setPatient(patient);
        result.setLabTest(labTest);
        result.setTestDate(LocalDateTime.now());
        result.setStatus("PENDING");

        return labTestResultRepository.save(result);
    }

    public LabTestResult getLabTestResultById(Long id) {
        return labTestResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab test result not found with id: " + id));
    }

    public List<LabTestResult> getLabTestResultsByPatientId(Long patientId) {
        return labTestResultRepository.findByPatient_IdOrderByTestDateDesc(patientId);
    }

    public List<LabTestResult> getLabTestResultsByStatus(String status) {
        return labTestResultRepository.findByStatus(status);
    }

    public List<LabTestResult> getAllLabTestResults() {
        return labTestResultRepository.findAll();
    }

    public LabTestResult updateLabTestResult(Long id, LabTestResult resultDetails) {
        LabTestResult result = getLabTestResultById(id);
        result.setResult(resultDetails.getResult());
        result.setNormalRange(resultDetails.getNormalRange());
        result.setStatus(resultDetails.getStatus());
        result.setNotes(resultDetails.getNotes());
        return labTestResultRepository.save(result);
    }

    public void deleteLabTestResult(Long id) {
        labTestResultRepository.deleteById(id);
    }
}
