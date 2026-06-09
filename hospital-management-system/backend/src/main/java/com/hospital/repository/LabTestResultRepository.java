package com.hospital.repository;

import com.hospital.entity.LabTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LabTestResultRepository extends JpaRepository<LabTestResult, Long> {
    List<LabTestResult> findByPatient_Id(Long patientId);
    List<LabTestResult> findByLabTest_Id(Long labTestId);
    List<LabTestResult> findByStatus(String status);
    List<LabTestResult> findByPatient_IdOrderByTestDateDesc(Long patientId);
}
