package com.hospital.repository;

import com.hospital.entity.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    Optional<Pharmacist> findByUser_Id(Long userId);
    List<Pharmacist> findByIsAvailableTrue();
    Optional<Pharmacist> findByLicenseNumber(String licenseNumber);
}
