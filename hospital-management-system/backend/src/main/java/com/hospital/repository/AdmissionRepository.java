package com.hospital.repository;

import com.hospital.management.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    List<Admission> findByPatient_Id(Long patientId);
    List<Admission> findByDoctor_Id(Long doctorId);
    List<Admission> findByStatus(String status);
    List<Admission> findByBed_Id(Long bedId);
}
