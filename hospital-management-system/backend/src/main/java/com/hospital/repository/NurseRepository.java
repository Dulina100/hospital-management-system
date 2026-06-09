package com.hospital.repository;

import com.hospital.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Optional<Nurse> findByUser_Id(Long userId);
    List<Nurse> findByShift(String shift);
    List<Nurse> findByDepartment(String department);
    List<Nurse> findByIsAvailableTrue();
    Optional<Nurse> findByLicenseNumber(String licenseNumber);
}