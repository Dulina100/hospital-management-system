package com.hospital.repository;

import com.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser_Id(Long userId);
    List<Patient> findByIsAdmittedTrue();
    List<Patient> findByIsAdmittedFalse();
    List<Patient> findByUser_FirstNameContainingIgnoreCase(String firstName);
}
