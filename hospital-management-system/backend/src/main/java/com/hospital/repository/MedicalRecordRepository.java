package com.hospital.repository;

import com.hospital.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatient_Id(Long patientId);
    List<MedicalRecord> findByDoctor_Id(Long doctorId);
    List<MedicalRecord> findByPatient_IdOrderByRecordDateDesc(Long patientId);
}
